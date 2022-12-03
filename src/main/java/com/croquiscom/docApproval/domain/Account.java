package com.croquiscom.docApproval.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="UM_USER_MASTER")
public class Account {
	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_PASSWD")
	private String userPw;
}