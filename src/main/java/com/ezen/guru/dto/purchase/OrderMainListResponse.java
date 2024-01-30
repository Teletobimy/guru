package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderMainListResponse {

    private final String id;
    private final String materialName;
    private final int purchaseOrderDetails;
    private final int totalprice;
    private final LocalDateTime deadline;
    private final LocalDateTime shippingDate;

}


