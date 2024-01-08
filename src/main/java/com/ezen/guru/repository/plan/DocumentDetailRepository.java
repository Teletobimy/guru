package com.ezen.guru.repository.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ezen.guru.domain.DocumentDetail;
import java.util.List;

public interface DocumentDetailRepository extends JpaRepository<DocumentDetail, Integer> {
    List<DocumentDetail> findByDocumentId(String documentId);

}
