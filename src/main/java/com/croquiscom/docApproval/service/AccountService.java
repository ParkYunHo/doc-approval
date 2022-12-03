package com.croquiscom.docApproval.service;

import org.springframework.stereotype.Service;

import com.croquiscom.docApproval.domain.Account;
import com.croquiscom.docApproval.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountRepository accountRepo;
	
	public Account findAccountByUserId(String userId) {
		return accountRepo.findByUserId(userId);
	}
}
