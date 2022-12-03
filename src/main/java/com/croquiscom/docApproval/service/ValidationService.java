package com.croquiscom.docApproval.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.croquiscom.docApproval.domain.approval.ApprovalDto;
import com.croquiscom.docApproval.domain.approval.ApprovalType;
import com.croquiscom.docApproval.domain.document.DocumentDto;
import com.croquiscom.docApproval.domain.document.DocumentType;
import com.croquiscom.docApproval.repository.AccountRepository;
import com.croquiscom.docApproval.repository.DocumentRepository;
import com.croquiscom.docApproval.repository.DocumentRepositoryImp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {
	
	private final AccountRepository accountRepo;
	private final DocumentRepository documentRepo;
	private final DocumentRepositoryImp documentRepoImp;
	
	
	public void checkFindAccount(String userId, String userPw) throws Exception{
		if(accountRepo.findByAccount(userId, userPw) == null) 
			throw new Exception();
	}
	
	public void checkFindDocsDetail(String docId) throws Exception{
		if(docId == null || docId.equals(""))
			throw new Exception();
		if(documentRepo.existsById(docId) == false)
			throw new Exception();
	}
	
	public void checkFindDocs(String listType) throws Exception{
		if(listType.equals("") || listType == null)
			throw new Exception();
		if(Arrays.asList(new String[]{DocumentType.OUTBOX, DocumentType.INBOX, DocumentType.ARCHIVE}).contains(listType) == false)
			throw new Exception();
	}
	
	public void checkInsertDocs(DocumentDto.DocumentReqDto document) throws Exception{
		if(document.getApprList().size() < 1)
			throw new Exception();
		if(document.getApprList().stream().map(n -> n.getSeq()).distinct().count() != document.getApprList().size())
			throw new Exception();
		if(document.getApprList().stream().map(n -> n.getApprovalUserId()).distinct().count() != document.getApprList().size())
			throw new Exception();
		
	}
	
	public String checkUpdateDocs(ApprovalDto.ApprovalReqDto approval, String userId) throws Exception{
		if(approval.getDocId() == null || approval.getDocId().equals(""))
			throw new Exception();
		if(approval.getStatus() == null || approval.getStatus().equals(""))
			throw new Exception();
		
		Object status = documentRepoImp.findStatusByDocId(approval.getDocId());
		if(status == null)
			throw new Exception();
		if(status.toString().equals(ApprovalType.HOLDING) == false)
			throw new Exception();
		
		Object[] obj = (Object[])documentRepoImp.checkNextAppr(approval.getDocId()).get(0);
		String nextSeq = obj[0].toString().trim();
		String nextUserId = obj[1].toString().trim();
		
		if(userId.equals(nextUserId) == false)
			throw new Exception();
		
		return nextSeq;
	}
	
}
