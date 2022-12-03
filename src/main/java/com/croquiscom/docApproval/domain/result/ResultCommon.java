package com.croquiscom.docApproval.domain.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel(value = "API response 객체")
public class ResultCommon {
	@ApiModelProperty(value="응답 성공여부 구분", example = "SUCCESS: 성공, FAIL: 실패")
	private ResultType status;
	
	@ApiModelProperty(value="응답 메세지")
	private String message;
}
