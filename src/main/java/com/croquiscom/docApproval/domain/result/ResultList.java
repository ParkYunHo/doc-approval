package com.croquiscom.docApproval.domain.result;

import java.util.List;

import lombok.Data;

@Data
public class ResultList<T> extends ResultCommon{
	private List<T> data;
}
