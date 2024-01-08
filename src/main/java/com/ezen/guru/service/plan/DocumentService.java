package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.DocumentDetail;
import com.ezen.guru.dto.plan.DocumentDTO;

import java.util.List;

public interface DocumentService {
    List<DocumentDTO> getAllDocuments();
    DocumentDTO findDocumentById(String documentId);
    List<DocumentDetail> findDocumentDetailsByDocumentId(String documentId);
}