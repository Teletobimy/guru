package com.ezen.guru.service.plan;
import com.ezen.guru.dto.plan.DocumentDTO;
import com.ezen.guru.repository.plan.DocumentDetailRepository;
import com.ezen.guru.repository.plan.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.DocumentDetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentDetailRepository documentDetailRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentDetailRepository documentDetailRepository) {
        this.documentRepository = documentRepository;
        this.documentDetailRepository = documentDetailRepository;
    }


    public DocumentDTO findDocumentDTOById(String documentId) {
        Document document = documentRepository.findById(documentId).orElse(null);
        if (document != null) {
            return convertToDocumentDTO(document);
        }
        return null;
    }

    // Entity to DTO 변환 메서드
    private DocumentDTO convertToDocumentDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setType(document.getType());
        documentDTO.setCompanyId(document.getCompany_id());
        documentDTO.setTotalprice(document.getTotalprice());
        documentDTO.setRegdate(document.getRegdate());
        documentDTO.setDeadline(document.getDeadline());
        documentDTO.setStatus(document.getStatus());
        documentDTO.setMemo(document.getMemo());
        return documentDTO;
    }


    @Override
    public List<DocumentDTO> getAllDocuments() {
        List<Document> documents = documentRepository.findByType(1);
        return documents.stream().map(this::convertToDocumentDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO findDocumentById(String documentId) {
        // documentRepository를 사용하여 documentId에 해당하는 Document 엔티티를 찾습니다.
        Optional<Document> documentOptional = documentRepository.findById(documentId);

        // Document가 존재하면 DocumentDTO로 변환하여 반환합니다.
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            return convertToDocumentDTO(document);
        }

        // 문서를 찾지 못한 경우 null을 반환하거나 다른 방법을 선택할 수 있습니다.
        return null;
    }

    @Override
    public List<DocumentDetail> findDocumentDetailsByDocumentId(String documentId) {
        List<DocumentDetail> documentDetails = documentDetailRepository.findByDocumentId(documentId);
        return documentDetails;
    }

    // DTO to Entity 변환 메서드도 추가 가능

    // 다른 메서드들...
}
