package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.Company;
import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuotationDTO {
    private String id;
    private int biddingNo;
    private Company company;
    private String company_name;
    private Integer quotation_totalprice;
    private LocalDateTime regdate;
    private LocalDateTime deadline;
    private String leadTime;
    private String tradeTerms;
    private String paymentTerms;
    private int status;
    private String quotation_memo;
    private List<QuotationDetailDTO> quotationDetails;

}


