package com.ezen.guru.service.plan;


import com.ezen.guru.domain.Material;
import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import com.ezen.guru.dto.plan.QuotationDTO;
import com.ezen.guru.dto.plan.QuotationDetailDTO;
import com.ezen.guru.repository.plan.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;


    @Autowired
    public QuotationServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;

    }


    @Override
    public String saveQuotation(QuotationDTO quotationDTO) {

        Quotation quo = Quotation.builder()
                .id(quotationDTO.getId())
                .company_id(quotationDTO.getCompany_id())
                .company_name(quotationDTO.getCompany_name())
                .quotation_totalprice(quotationDTO.getQuotation_totalprice())
                .regdate(quotationDTO.getRegdate())
                .deadline(quotationDTO.getDeadline())
                .status(quotationDTO.getStatus())
                .quotation_memo(quotationDTO.getQuotation_memo())
                .quotationDetails(
                        quotationDTO.getQuotationDetails()
                                .stream()
                                .map(QuotationDetailDTO::toEntity)
                                .collect(Collectors.toList())
                )
                .build();

        quotationRepository.save(quo);
        return "Data saved successfully";
    }


}