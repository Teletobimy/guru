package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.DocumentDetail;
import com.ezen.guru.dto.plan.DocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentService {
    Document convertToDocument(DocumentDTO documentDTO);

    List<DocumentDTO> getAllDocuments();

    List<DocumentDTO> getAllProcurementPlan();

    DocumentDTO findDocumentById(String documentId);
    List<DocumentDetail> findDocumentDetailsByDocumentId(String documentId);

    Page<DocumentDTO> procurementList(String keyword, int category, LocalDateTime startDate, LocalDateTime endDate,  Pageable pageable);

    void documentSave(DocumentDTO documentDTO);

    void documentDelete(String id);

    Page<DocumentDTO> documentList(String keyword, int category, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}