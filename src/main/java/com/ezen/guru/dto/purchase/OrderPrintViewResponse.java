package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderPrintViewResponse {

    private final String id; //
    private final LocalDateTime regdate; //
    private final LocalDateTime deadline; //
    private final String companyName; //
    private final String companyId; //
    private final String ceo;
    private final String tel;
    private final String email;
    private final String address;
    private final String materialName; //
    private final int category; //
    private final int price; //
    private final String cnt; //
    private final int materialprice; //
    private final int totalprice; //
    private final String leadTime; //
    private final String tradeTerms; //
    private final String paymentTerms; //
    private final String memo; //
}
