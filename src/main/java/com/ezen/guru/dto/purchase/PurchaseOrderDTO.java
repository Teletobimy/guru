package com.ezen.guru.dto.purchase;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseOrderDTO {

    private String id;
    private String documentId;
    private String companyId;
    private int totalprice;
    private int status;
    private String memo;
    private LocalDateTime deadline;
    private String leadTime;
    private String tradeTerms;
    private String paymentTerms;
    private List<PurchaseOrderDetailDTO> PurchaseOrderDetailDTO;
}
