package com.croquiscom.docApproval.domain.approval;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class ApprovalDto {
	@Data
	public static class ApprovalResDto implements Serializable {	// Redis Cache를 위해 Serializable 인터페이스 구현
		private String docId;
		private String seq;
		private String status;
		private String note;
		private String approvalUserId;
	}
	
	@Data
	@ApiModel(value = "결재라인 정보")
	public static class ApprovalDocsReqDto{
		@ApiModelProperty(value="결재순서")
		private String seq;
		
		@ApiModelProperty(value="승인자 ID")
		private String approvalUserId;
	}
	
	@Data
	@ApiModel(value = "결재라인 정보")
	public static class ApprovalReqDto{
		@ApiModelProperty(value="문서ID")
		private String docId;
		
		@ApiModelProperty(value="결재 상태", example = "0: 대기, 1:승인, 2:거절")
		private String status;
		
		@ApiModelProperty(value="결재 코멘트")
		private String note;
	}
}
