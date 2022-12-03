package com.croquiscom.docApproval.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.croquiscom.docApproval.domain.result.ResultCommon;
import com.croquiscom.docApproval.service.result.ResultService;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice(basePackages = "com.croquiscom.docApproval.controller")
@RequiredArgsConstructor
public class ExceptionAdvice {
	private final ResultService resService;
	
	@ExceptionHandler(Exception.class)
	public ResultCommon exceptionAdvice() {
		return resService.getFailResultStatus();
	}
}
