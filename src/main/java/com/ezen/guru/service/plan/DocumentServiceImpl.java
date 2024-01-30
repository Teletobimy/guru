package com.ezen.guru.service.plan;
import com.ezen.guru.dto.plan.DocumentDTO;
import com.ezen.guru.repository.plan.DocumentDetailRepository;
import com.ezen.guru.repository.plan.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.DocumentDetail;

import java.time.LocalDateTime;
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
        documentDTO.setBiddingNo(document.getBiddingNo());
        documentDTO.setType(document.getType());
        documentDTO.setCompany(document.getCompany());
        documentDTO.setDocumentTotalPrice(document.getDocument_totalprice());
        documentDTO.setRegdate(document.getRegdate());
        documentDTO.setDeadline(document.getDeadline());
        documentDTO.setStatus(document.getStatus());
        documentDTO.setLeadTime(document.getLeadTime());
        documentDTO.setTradeTerms(document.getTradeTerms());
        documentDTO.setPaymentTerms(document.getPaymentTerms());
        documentDTO.setDocumentMemo(document.getDocument_memo());
        documentDTO.setDocumentDetails(document.getDocumentDetails());
        return documentDTO;
    }

    @Override
    public Document convertToDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setId(documentDTO.getId());
        document.setBiddingNo(documentDTO.getBiddingNo());
        document.setType(documentDTO.getType());
        document.setCompany(documentDTO.getCompany());
        document.setDocument_totalprice(documentDTO.getDocumentTotalPrice());
        document.setRegdate(documentDTO.getRegdate());
        document.setDeadline(documentDTO.getDeadline());
        document.setStatus(documentDTO.getStatus());
        document.setLeadTime(documentDTO.getLeadTime());
        document.setTradeTerms(documentDTO.getTradeTerms());
        document.setPaymentTerms(documentDTO.getPaymentTerms());
        document.setDocument_memo(documentDTO.getDocumentMemo());
        document.setDocumentDetails(documentDTO.getDocumentDetails());
        return document;
    }


    @Override
    public List<DocumentDTO> getAllDocuments() {
        List<Document> documents = documentRepository.findByType(1);
        return documents.stream().map(this::convertToDocumentDTO).collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getAllProcurementPlan() {
        List<Document> documents = documentRepository.findByType(0);
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

    @Override
    public Page<DocumentDTO> procurementList(String keyword, int category, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        if(category==-1){
            return documentRepository.findDocumentsByIdAndDateRange(0, keyword,startDate,endDate,pageable).map(this::convertToDocumentDTO);
        }else {
            return documentRepository.procurementList(0, keyword, category, startDate, endDate, pageable).map(this::convertToDocumentDTO);
        }
    }

    @Override
    public void documentSave(DocumentDTO documentDTO){

        documentRepository.save(convertToDocument(documentDTO));
    }

    @Override
    public void documentDelete(String id) {
        documentRepository.deleteById(id);
    }

    // DTO to Entity 변환 메서드도 추가 가능

    // 다른 메서드들...
}