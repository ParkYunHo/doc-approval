package com.croquiscom.docApproval.controller.api;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.croquiscom.docApproval.config.security.JwtTokenProvider;
import com.croquiscom.docApproval.domain.result.ResultCommon;
import com.croquiscom.docApproval.service.ValidationService;
import com.croquiscom.docApproval.service.result.ResultService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
	
	private final ResultService resService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ValidationService validationService;
	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	@GetMapping("/auth")
	@ApiOperation(value = "getTokenByAuth", notes = "사용자 아이디, 패스워드를 인증하여 토큰 발급")
	@ApiImplicitParams({
		@ApiImplicitParam(name= "userId", value = "인증할 사용자 아이디", required = true, dataType = "string"),
		@ApiImplicitParam(name= "userPw", value = "인증할 사용자 비밀번호", required = true, dataType = "string"),
	})
	public @ResponseBody ResultCommon getTokenByAuth(String userId, String userPw) throws Exception {
		validationService.checkFindAccount(userId, userPw);
		return resService.getResultSingle(jwtTokenProvider.createToken(userId));
	}
	
}
