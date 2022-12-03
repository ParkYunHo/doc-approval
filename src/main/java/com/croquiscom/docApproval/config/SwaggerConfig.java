package com.croquiscom.docApproval.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket authApi() {
		String version = "auth";
		String title = "로그인기능 API 명세";
		String desc = "크로키닷컴 전자결재 API시스템 로그인기능";
		
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.croquiscom.docApproval.controller.api"))
				.paths(PathSelectors.ant("/auth"))
				.build()
				.apiInfo(apiInfo(title, version, desc))
				;
	}
	
	@Bean
	public Docket docsApi() {
		String version = "docs";
		String title = "결재문서 관리기능 API 명세";
		String desc = "크로키닷컴 전자결재 API시스템 결재문서 관리기능";
		
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(
						Collections.singletonList(
							new ParameterBuilder()
							.name("X-AUTH-TOKEN")
							.description("JWT TOKEN")
							.modelRef(new ModelRef("string"))
							.parameterType("header")
							.required(true)
							.build()
						)
				)
				.useDefaultResponseMessages(false)
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.croquiscom.docApproval.controller.api"))
				.paths(PathSelectors.ant("/docs*"))
				.build()
				.apiInfo(apiInfo(title, version, desc))
				;
	}
	
	private ApiInfo apiInfo(String title, String version, String desc) {
		return new ApiInfoBuilder()
				.title(title)
				.description(desc)
				.version(version)
				.build();
	}
}
