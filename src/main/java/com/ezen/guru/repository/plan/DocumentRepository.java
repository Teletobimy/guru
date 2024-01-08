package com.ezen.guru.repository.plan;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ezen.guru.domain.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByType(int type);
}
