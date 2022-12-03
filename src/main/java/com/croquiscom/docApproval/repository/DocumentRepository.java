package com.croquiscom.docApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.croquiscom.docApproval.domain.document.Document;

public interface DocumentRepository extends JpaRepository<Document, String>{
	public Document findByDocId(String docId);
}
