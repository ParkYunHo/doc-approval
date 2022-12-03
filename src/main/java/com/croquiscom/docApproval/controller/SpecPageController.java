package com.croquiscom.docApproval.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpecPageController {
	
	@RequestMapping(value = "/")
	public String redirectDocs() {
		return "redirect:/swagger-ui.html";
	}
}
