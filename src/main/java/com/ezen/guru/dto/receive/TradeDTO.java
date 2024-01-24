package com.ezen.guru.dto.receive;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TradeDTO {
    private final String purchaseOrderId;
    private final String companyName;
    private final int totalPrice;
    private final LocalDateTime regDate;
}
