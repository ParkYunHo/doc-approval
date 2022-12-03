package com.croquiscom.docApproval.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.croquiscom.docApproval.domain.Account;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
	
	private final EntityManager em;
	
	public Object findByAccount(String userId, String userPw) {
		return em.createNativeQuery("SELECT USER_ID, '' USER_PASSWD FROM UM_USER_MASTER WHERE USER_ID = :userId AND 1 = COMPARE_ENCRYPT(USER_PASSWD, :userPw)")
					.setParameter("userId", userId)
					.setParameter("userPw", userPw)
				.getSingleResult();
	}
	
	public Account findByUserId(String userId) {
		return em.find(Account.class, userId);
	}
}
