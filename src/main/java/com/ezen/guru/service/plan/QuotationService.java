package com.ezen.guru.service.plan;

import com.ezen.guru.dto.plan.QuotationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuotationService {
    String saveQuotation(QuotationDTO quotationDTO);

    Page<QuotationDTO> findAllwithPageable(Pageable pageable);
}
