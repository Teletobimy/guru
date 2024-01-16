package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.repository.plan.DocumentRepository;
import com.ezen.guru.repository.purchase.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompleteRequest {
    String id;
    int newStatus;

    public static OrderCompleteRequest fromEntity(PurchaseOrder purchaseOrder) {
        return OrderCompleteRequest.builder()
                .id(purchaseOrder.getId())
                .newStatus(purchaseOrder.getStatus())
                .build();
    }

    public static PurchaseOrder toEntity(OrderCompleteRequest dto, DocumentRepository documentRepository, CompanyRepository companyRepository) {
        String documentId = "document_id";
        String companyId = "company_id";

        Document document = documentRepository.findById(documentId).orElse(null);
        Company company = Company.builder().companyId(companyId).build();

        return PurchaseOrder.builder()
                .id(dto.getId())
                .document(document)
                .company(company)
                .status(dto.getNewStatus())
                .build();
    }
}
