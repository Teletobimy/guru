package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Material;
import com.ezen.guru.domain.Quotation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseOrderDetailDTO {

    private int materialId;
    private int purchaseOrderCnt;
    private int check;
    private int qcCheckCnt;

}