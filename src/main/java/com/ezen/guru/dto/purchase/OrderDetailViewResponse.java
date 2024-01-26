package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderDetailViewResponse {

    private final int type;
    private final String id;
    private final String documentId;
    private final LocalDateTime regdate;
    private final LocalDateTime deadline;
    private final int status;
    private final String companyId;
    private final String companyName;
    private final int detailId;
    private final int materialId;
    private final String materialName;
    private final int category;
    private final int price;
    private final String cnt;
    private final int orderCnt;
    private final int qccheckCnt;
    private final String measure;
    private final int totalprice;
    private final String leadTime;
    private final String tradeTerms;
    private final String paymentTerms;
    private final String memo;
    private final int materialprice;
    private final int check;
}
