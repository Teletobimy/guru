package com.ezen.guru.dto.plan;

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
    private int type;
    private int biddingNo;
    private String companyId;
    private int totalprice;
    private LocalDateTime regdate;
    private LocalDateTime deadline;
    private int status;
    private String leadTime;
    private String tradeTerms;
    private String paymentTerms;
    private String memo;
    private List<DocumentDetailDTO> DocumentDetails;

}


