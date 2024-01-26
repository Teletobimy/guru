package com.ezen.guru.service.plan;


import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import com.ezen.guru.dto.plan.QuotationDTO;
import com.ezen.guru.dto.plan.QuotationDetailDTO;
import com.ezen.guru.repository.plan.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;


    @Autowired
    public QuotationServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;

    }

    public QuotationDTO convertToDTO(Quotation quotation) {
        QuotationDTO dto = new QuotationDTO();
        dto.setId(quotation.getId());
        dto.setBiddingNo(quotation.getBiddingNo());
        dto.setCompany_id(quotation.getCompany_id());
        dto.setCompany_name(quotation.getCompany_name());
        dto.setQuotation_totalprice(quotation.getQuotation_totalprice());
        dto.setRegdate(quotation.getRegdate());
        dto.setDeadline(quotation.getDeadline());
        dto.setStatus(quotation.getStatus());
        dto.setLeadTime(quotation.getLeadTime());
        dto.setPaymentTerms(quotation.getPaymentTerms());
        dto.setTradeTerms(quotation.getTradeTerms());
        dto.setQuotation_memo(quotation.getQuotation_memo());

        // QuotationDetail 변환
        if (quotation.getQuotationDetails() != null) {
            dto.setQuotationDetails(
                    quotation.getQuotationDetails().stream()
                            .map(detail -> convertToDetailDTO(detail))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Quotation convertToEntity(QuotationDTO dto) {
        Quotation entity = new Quotation();
        entity.setId(dto.getId());
        entity.setBiddingNo(dto.getBiddingNo());
        entity.setCompany_id(dto.getCompany_id());
        entity.setCompany_name(dto.getCompany_name());
        entity.setQuotation_totalprice(dto.getQuotation_totalprice());
        entity.setRegdate(dto.getRegdate());
        entity.setDeadline(dto.getDeadline());
        entity.setStatus(dto.getStatus());
        entity.setLeadTime(dto.getLeadTime());
        entity.setPaymentTerms(dto.getPaymentTerms());
        entity.setTradeTerms(dto.getTradeTerms());
        entity.setQuotation_memo(dto.getQuotation_memo());

        // QuotationDetail 변환
        if (dto.getQuotationDetails() != null) {
            entity.setQuotationDetails(
                    dto.getQuotationDetails().stream()
                            .map(detailDTO -> convertToDetailEntity(detailDTO))
                            .collect(Collectors.toList())
            );
        }

        return entity;
    }


    @Override
    public String saveQuotation(QuotationDTO quotationDTO) {
        Quotation quo = convertToEntity(quotationDTO);
        quotationRepository.save(quo);
        return "Data saved successfully";
    }

    public QuotationDetailDTO convertToDetailDTO(QuotationDetail detail) {
        QuotationDetailDTO detailDTO = new QuotationDetailDTO();
        detailDTO.setId(detail.getId());
        detailDTO.setQuotation_id(detail.getQuotation().getId()); // Assuming getId() method exists in Quotation entity
        detailDTO.setMaterial_id(detail.getMaterial_id());
        detailDTO.setMaterialName(detail.getMaterialName());
        detailDTO.setQuotationCnt(detail.getQuotationCnt());
        detailDTO.setQuotationMeasure(detail.getQuotationMeasure());
        detailDTO.setQuotationPrice(detail.getQuotationPrice());

        return detailDTO;
    }

    public QuotationDetail convertToDetailEntity(QuotationDetailDTO detailDTO) {
        QuotationDetail detailEntity = new QuotationDetail();
        detailEntity.setId(detailDTO.getId());

        // Assuming you have a constructor in QuotationDetail that accepts a Quotation id
        // You need to create a Quotation entity and set its id using setQuotation method.
        Quotation quotation = detailEntity.getQuotation();
        detailEntity.setQuotation(quotation);
        detailEntity.setMaterial_id(detailDTO.getMaterial_id());
        detailEntity.setMaterialName(detailDTO.getMaterialName());
        detailEntity.setQuotationCnt(detailDTO.getQuotationCnt());
        detailEntity.setQuotationMeasure(detailDTO.getQuotationMeasure());
        detailEntity.setQuotationPrice(detailDTO.getQuotationPrice());
        return detailEntity;
    }

    @Override
    public Page<QuotationDTO> findAllwithPageable(Pageable pageable){
    return quotationRepository.findAll(pageable).map(this::convertToDTO);
    }
}