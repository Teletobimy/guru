package com.ezen.guru.dto.receive;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TradeDetailDTO {
    private final int purchaseOrderDetailId;
    private final String purchaseOrderId;
    private final String materialName;
    private final int materialCategory;
    private final String materialMeasure;
    private final int materialPrice;
    private final int materialTotalPrice;
    private final int materialCnt;
    private final LocalDateTime purchaseOrderRegDate;
    private final String companyId;
    private final String companyName;
    private final String ceo;
    private final String tel;
    private final String email;
    private final String address;
}
