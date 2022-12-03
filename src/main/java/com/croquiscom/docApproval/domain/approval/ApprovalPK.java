package com.croquiscom.docApproval.domain.approval;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApprovalPK implements Serializable{
	private String docId;
	private String seq;
}
