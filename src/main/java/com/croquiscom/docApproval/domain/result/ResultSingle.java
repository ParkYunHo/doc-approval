package com.croquiscom.docApproval.domain.result;

import lombok.Data;

@Data
public class ResultSingle<T> extends ResultCommon{
	private T data;
}
