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

    private int id;
    private int purchaseOrderCnt;
    private String materialName;
    private int materialCategory;
    private String materialMeasure;
    private int materialPrice;
    private int check;
    private int qcCheckCnt;


}