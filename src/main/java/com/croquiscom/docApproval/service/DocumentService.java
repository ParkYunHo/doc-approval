package com.croquiscom.docApproval.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.croquiscom.docApproval.domain.Account;
import com.croquiscom.docApproval.domain.approval.Approval;
import com.croquiscom.docApproval.domain.approval.ApprovalDto;
import com.croquiscom.docApproval.domain.approval.ApprovalType;
import com.croquiscom.docApproval.domain.document.Document;
import com.croquiscom.docApproval.domain.document.DocumentDto;
import com.croquiscom.docApproval.domain.document.DocumentType;
import com.croquiscom.docApproval.repository.ApprovalRepository;
import com.croquiscom.docApproval.repository.DocumentRepository;
import com.croquiscom.docApproval.repository.DocumentRepositoryImp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {
	
	private final ValidationService validationService;
	private final ApprovalRepository apprRepo;
	private final DocumentRepository documentRepo;
	private final DocumentRepositoryImp documentRepoImp;

	
	@Cacheable(value="docs-detail", key="#docId", unless = "#result == null")
	public DocumentDto.DocumentDocsDetailDto getDocsDetail(String docId) throws Exception{
		validationService.checkFindDocsDetail(docId);
		return getMapper().map(documentRepo.findByDocId(docId), DocumentDto.DocumentDocsDetailDto.class);
	}
	
	
	@Cacheable(value="docs", key="{ #userId.concat(':').concat(#listType) }", unless = "#result == null")
	public List<DocumentDto.DocumentDocsDto> getDocs(String userId, String listType) throws Exception{
		validationService.checkFindDocs(listType);
		switch(listType) {
			case DocumentType.OUTBOX:
				return documentRepoImp.findOutBox(userId).stream().map(n -> getMapper().map(n, DocumentDto.DocumentDocsDto.class)).collect(Collectors.toList());
			case DocumentType.INBOX:
				return documentRepoImp.findInBox(userId).stream().map(n -> getMapper().map(n, DocumentDto.DocumentDocsDto.class)).collect(Collectors.toList());
			case DocumentType.ARCHIVE:
				return documentRepoImp.findArchive(userId).stream().map(n -> getMapper().map(n, DocumentDto.DocumentDocsDto.class)).collect(Collectors.toList());
			default:
				throw new Exception();
		}
	}
	
	
	@Transactional
	@Caching(evict = {
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.OUTBOX+"')"),
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.INBOX+"')"),
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.ARCHIVE+"')")
	})
	public void insertDocs(String userId, DocumentDto.DocumentReqDto documentDto) throws Exception {
		validationService.checkInsertDocs(documentDto);
		
		String docId = userId.concat(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		Document document = getMapper().map(documentDto, Document.class);
		
		List<Approval> apprList = document.getApprList();
		document.setDocId(docId);
		document.setStatus(ApprovalType.HOLDING);
		document.setInputUserId(userId);
		document.setApprList(null);
		document.setInputDate(new Date());
		documentRepo.save(document);
		
		apprList.stream().forEach(n -> {
			n.setDocId(docId);
			n.setStatus(ApprovalType.HOLDING);
			n.setInputUserId(userId);
			n.setInputDate(new Date());
		});
		apprRepo.saveAll(apprList);
	}
	
	
	@Transactional
	@Caching(evict = {
			@CacheEvict(value="docs-detail", key="#approvalDto.docId"),
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.OUTBOX+"')"),
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.INBOX+"')"),
			@CacheEvict(value="docs", key="#userId.concat(':').concat('"+DocumentType.ARCHIVE+"')")
	})
	public void updateDocs(String userId, ApprovalDto.ApprovalReqDto approvalDto) throws Exception{
		String nextSeq = validationService.checkUpdateDocs(approvalDto, userId);
		String docId = approvalDto.getDocId();
		String status = approvalDto.getStatus();
		String note = approvalDto.getNote();
		
		Approval approval = apprRepo.findByDocIdAndSeq(docId, nextSeq);
		approval.setStatus(status);
		approval.setNote(note);
		apprRepo.save(approval);
		
		if(status.equals(ApprovalType.REJECTED) || nextSeq.equals(apprRepo.findMaxSeqByDocId(docId))){
			Document document = documentRepo.findByDocId(docId);
			document.setStatus(status);
			documentRepo.save(document);
		}
	}
	
	
	public String getAuthUserId() {
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return account.getUserId();
	}
	
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
