package com.croquiscom.docApproval.controller.api;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.croquiscom.docApproval.domain.approval.ApprovalDto;
import com.croquiscom.docApproval.domain.document.DocumentDto;
import com.croquiscom.docApproval.domain.result.ResultCommon;
import com.croquiscom.docApproval.service.DocumentService;
import com.croquiscom.docApproval.service.result.ResultService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentController {
	
	private final DocumentService documentService;
	private final ResultService resService;
	
	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	@GetMapping("/docs-detail")
	@ApiOperation(value = "getDocsDetail", notes = "문서ID에 해당하는 문서정보 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name= "docId", value = "조회할 문서ID", required = true, dataType = "string")
	})
	public @ResponseBody ResultCommon getDocsDetail(String docId) throws Exception {
		return resService.getResultDtoSingle(documentService.getDocsDetail(docId));
	}
	
	@GetMapping("/docs")
	@ApiOperation(value = "getDocs", notes = "문서목록(INBOX, OUTBOX, ARCHIVE)에 해당하는 문서정보 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name= "listType", value = "조회할 문서목록 구분", required = true, dataType = "string")
	})
	public @ResponseBody ResultCommon getDocs(String listType) throws Exception {
		return resService.getResultDtoList(documentService.getDocs(documentService.getAuthUserId(), listType));
	}

	
	@PostMapping("/docs")
	@ApiOperation(value = "insertDocs", notes = "신규 작성 문서등록")
	public @ResponseBody ResultCommon insertDocs(@RequestBody @ApiParam(value = "신규 작성 문서정보 및 결재라인 정보", required = true) DocumentDto.DocumentReqDto document) throws Exception{
		documentService.insertDocs(documentService.getAuthUserId(), document);
		return resService.getSuccessResultStatus();
	}
	
	
	@PutMapping("/docs")
	@ApiOperation(value = "updateDocs", notes = "문서 결재처리")
	@ApiImplicitParams({
		@ApiImplicitParam(name= "docId", value = "결재할 문서ID", required = true, dataType = "string"),
		@ApiImplicitParam(name= "status", value = "승인/거절 여부", required = true, dataType = "string", example = "1:승인, 2:거절"),
		@ApiImplicitParam(name= "note", value = "결재 코멘트", dataType = "string")
	})
	public @ResponseBody ResultCommon updateDocs(ApprovalDto.ApprovalReqDto approval) throws Exception {
		documentService.updateDocs(documentService.getAuthUserId(), approval);
		return resService.getSuccessResultStatus();
	}
	
}
