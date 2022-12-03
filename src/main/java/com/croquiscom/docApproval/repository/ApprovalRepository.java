package com.croquiscom.docApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.croquiscom.docApproval.domain.approval.Approval;


public interface ApprovalRepository extends JpaRepository<Approval, String>{
	@Query(value = "SELECT MAX(a.seq) FROM Approval a WHERE a.docId = :docId")
	public String findMaxSeqByDocId(@Param("docId") String docId);
	
	public Approval findByDocIdAndSeq(String docId, String seq);
}
