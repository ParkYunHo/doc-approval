package com.croquiscom.docApproval.domain.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.croquiscom.docApproval.domain.approval.Approval;

import lombok.Data;

@Entity
@Data
@Embeddable
@Table(name="AD_DOC_INFO")
public class Document {
	@Id
	@Column(name="DOC_ID")
	private String docId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="DOC_TITLE")
	private String title;
	
	@Column(name="DOC_DIV")
	private String div;
	
	@Column(name="DOC_CONTENT")
	private String content;
	
	@Column(name="INPUT_USER_ID")
	private String inputUserId;
	
	@Column(name="INPUT_DATE")
	private Date inputDate;
	
	@OneToMany(mappedBy = "document")
    private List<Approval> apprList = new ArrayList<Approval>();
}
