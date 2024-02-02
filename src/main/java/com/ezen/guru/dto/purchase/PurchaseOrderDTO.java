package com.ezen.guru.dto.purchase;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseOrderDTO {
    private String documentId;
    private String deadline;
    private List<PurchaseOrderDetailDTO> purchaseOrderDetailDTOList;

}
