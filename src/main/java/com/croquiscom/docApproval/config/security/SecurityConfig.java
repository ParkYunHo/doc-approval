package com.croquiscom.docApproval.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf().disable()			// RestAPI방식이므로 CSRF 비활성화
			.httpBasic().disable()		// HTTP 기본인증 비활성화
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT기반 인증이므로 Session 미사용
			.and()
				.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/").permitAll()		// swagger명세화면 permitAll
					.antMatchers(HttpMethod.GET, "/auth").permitAll()	// 로그인 API permitAll
					.anyRequest().authenticated()
			.and()
				// UsernamePasswordAuthenticationFilter필터 동작전에 JWT 토큰 유효성 체크 
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
	
    @Override
    public void configure(WebSecurity web) {
    	// Swagger 예외처리
        web.ignoring().antMatchers(
        		"/v2/api-docs", 
        		"/swagger*/**",
                "/webjars/**"
        		);
    }

}
