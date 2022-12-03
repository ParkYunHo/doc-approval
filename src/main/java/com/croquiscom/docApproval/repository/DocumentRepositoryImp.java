package com.croquiscom.docApproval.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import com.croquiscom.docApproval.domain.document.Document;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImp {
	private final EntityManager em;
	
	public List<Document> findOutBox(String userId){
		return em.createNativeQuery("	SELECT AI.DOC_ID, AI.STATUS, AI.DOC_TITLE, AI.DOC_DIV, AI.DOC_CONTENT, AI.INPUT_USER_ID, AI.INPUT_DATE \r\n" + 
									"	FROM AD_DOC_INFO AI \r\n" +
									"	WHERE AI.INPUT_USER_ID = :userId AND AI.STATUS = '0'", Document.class)
					.setParameter("userId", userId)
				.getResultList();
	}
	
	public List<Document> findInBox(String userId){
		return em.createNativeQuery("	SELECT AI.DOC_ID, AI.STATUS, AI.DOC_TITLE, AI.DOC_DIV, AI.DOC_CONTENT, AI.INPUT_USER_ID, AI.INPUT_DATE \r\n" + 
									"	FROM AD_DOC_INFO AI\r\n" + 
									"	JOIN (\r\n" + 
									"		SELECT AI.DOC_ID, MIN(APPROVAL_USER_ID) KEEP(DENSE_RANK FIRST ORDER BY SEQ) NEXT_USER_ID\r\n" + 
									"		FROM AD_DOC_APPROVAL_INFO AI\r\n" + 
									"		JOIN AD_DOC_INFO DI ON AI.DOC_ID = DI.DOC_ID AND DI.STATUS = '0'\r\n" + 
									"		WHERE AI.STATUS = 0\r\n" + 
									"		GROUP BY AI.DOC_ID\r\n" + 
									"	) Z ON AI.DOC_ID = Z.DOC_ID\r\n" + 
									"	WHERE Z.NEXT_USER_ID = :userId", Document.class)
					.setParameter("userId", userId)
				.getResultList();
	}
	
	public List<Document> findArchive(String userId){
		return em.createNativeQuery("	SELECT DOC_ID, STATUS, DOC_TITLE, DOC_DIV, DOC_CONTENT, INPUT_USER_ID, INPUT_DATE \r\n" + 
									"	FROM AD_DOC_INFO\r\n" + 
									"	WHERE INPUT_USER_ID = :userId\r\n" + 
									"	AND STATUS <> '0'\r\n" + 
									"	UNION\r\n" + 
									"	SELECT DOC_ID, STATUS, DOC_TITLE, DOC_DIV, DOC_CONTENT, INPUT_USER_ID, INPUT_DATE \r\n" +
									"	FROM AD_DOC_INFO DI\r\n" + 
									"	WHERE STATUS <> '0'\r\n" + 
									"	AND EXISTS (\r\n" + 
									"		SELECT 1\r\n" + 
									"		FROM AD_DOC_APPROVAL_INFO FI\r\n" + 
									"		WHERE DI.DOC_ID = FI.DOC_ID\r\n" + 
									"		AND FI.APPROVAL_USER_ID = :userId\r\n" + 
									"	)", Document.class)
					.setParameter("userId", userId)
				.getResultList();
	}
	
	public Object findStatusByDocId(String docId) {
		return em.createNativeQuery(" SELECT STATUS FROM AD_DOC_INFO WHERE DOC_ID = :docId")
					.setParameter("docId", docId)
					.getSingleResult();
	}
	
	public List<Object> checkNextAppr(String docId){
		return em.createNativeQuery("	SELECT TO_CHAR(MIN(AI.SEQ) KEEP(DENSE_RANK FIRST ORDER BY SEQ)) SEQ, MIN(AI.APPROVAL_USER_ID) KEEP(DENSE_RANK FIRST ORDER BY SEQ) APPROVAL_USER_ID\r\n" + 
									"	FROM AD_DOC_APPROVAL_INFO AI\r\n" + 
									"	JOIN AD_DOC_INFO DI ON AI.DOC_ID = DI.DOC_ID AND DI.STATUS = '0'\r\n" + 
									"	WHERE AI.STATUS = '0'\r\n" + 
									"	AND AI.DOC_ID = :docId \r\n" + 
									"	GROUP BY AI.DOC_ID ")
					.setParameter("docId", docId)
				.getResultList();
	}
}
