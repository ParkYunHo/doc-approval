package com.croquiscom.docApproval.config.security;

import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.croquiscom.docApproval.domain.Account;
import com.croquiscom.docApproval.service.AccountService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final AccountService accountService;
	
	private static final String secretKey = Base64.getEncoder().encodeToString("croquiscom".getBytes());
	private static final String role = "ROLE_USER";
	private static final int expireMin = 60;

	public String createToken(String userId) {
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("roles", Collections.singletonList(role));
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, expireMin);
		
		return Jwts.builder()
				.setClaims(claims) 
				.setIssuedAt(now) 
				.setExpiration(new Date(cal.getTimeInMillis())) 
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		Account account = accountService.findAccountByUserId(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(account, "", List.of(new SimpleGrantedAuthority(role)));
	}

	public String getUserId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getToken(HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
	}
	
	public boolean checkToken(String token) {
		try {
			return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
