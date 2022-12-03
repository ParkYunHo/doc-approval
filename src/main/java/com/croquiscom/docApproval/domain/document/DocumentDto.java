package com.croquiscom.docApproval.domain.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.croquiscom.docApproval.domain.approval.ApprovalDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class DocumentDto {
	@Data
	public static class DocumentDocsDto implements Serializable{		// Redis Cache를 위해 Serializable 인터페이스 구현
		private String docId;
		private String status;
		private String title;
		private String div;
		private String content;
		private String inputUserId;
		private Date inputDate;
	}
	
	@Data
	public static class DocumentDocsDetailDto implements Serializable{	// Redis Cache를 위해 Serializable 인터페이스 구현
		private String docId;
		private String status;
		private String title;
		private String div;
		private String content;
		private String inputUserId;
		private Date inputDate;
		private List<ApprovalDto.ApprovalResDto> apprList;
	}
	
	@Data
	@ApiModel(value = "결재문서 정보")
	public static class DocumentReqDto{
		@ApiModelProperty(value="문서 제목")
		private String title;
		
		@ApiModelProperty(value="문서 분류")
		private String div;
		
		@ApiModelProperty(value="문서 내용")
		private String content;
		
		@ApiModelProperty(value="결재권자 사용자ID 리스트")
		private List<ApprovalDto.ApprovalDocsReqDto> apprList;
	}
}
