package com.ezen.guru.service.plan;

import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import com.ezen.guru.dto.plan.QuotationDTO;
import com.ezen.guru.dto.plan.QuotationDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface QuotationService {
    QuotationDTO convertToDTO(Quotation quotation);

    String saveQuotation(QuotationDTO quotationDTO);

    QuotationDetailDTO convertToDetailDTO(QuotationDetail detail);

    Page<QuotationDTO> findAllwithPageable(String keyword, Integer category, Pageable pageable);

    Quotation findById(String id);

    List<QuotationDetailDTO> findAllByQuotation(Quotation quotation);

    void deleteQuotation(String id);



    Page<QuotationDTO> quotationList(String keyword, int category, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    List<QuotationDTO> findAllByBiddingNo(int biddingNo);
}
