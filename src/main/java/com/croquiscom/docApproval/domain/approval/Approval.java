package com.croquiscom.docApproval.domain.approval;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.croquiscom.docApproval.domain.document.Document;

import lombok.Data;

@Entity
@Data
@Embeddable
@IdClass(ApprovalPK.class)
@Table(name="AD_DOC_APPROVAL_INFO")
public class Approval {
	@Id
	@Column(name="DOC_ID")
	private String docId;
	
	@Id
	@Column(name="SEQ")
	private String seq;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="NOTE")
	private String note;
	
	@Column(name="APPROVAL_USER_ID")
	private String approvalUserId;
	
	@Column(name="INPUT_USER_ID")
	private String inputUserId;
	
	@Column(name="INPUT_DATE")
	private Date inputDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_ID", insertable = false, updatable = false)
	private Document document;
}
