package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.Company;
import com.ezen.guru.domain.DocumentDetail;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DocumentDTO {
    private String id;
    private int biddingNo;
    private int type;
    private Company company; // Assuming you want to send company ID
    private int documentTotalPrice;
    private LocalDateTime regdate;
    private LocalDateTime deadline;
    private int status;
    private String leadTime;
    private String tradeTerms;
    private String paymentTerms;
    private String documentMemo;
    private List<DocumentDetail> documentDetails;

}


