package com.ezen.guru.dto.receive;

import com.ezen.guru.domain.Material;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QcCheckRequest {
    private int shipmentId;

    private int materialNumber;

    private String materialName;

    private String manager;

    private int qcCheckCnt;

    private int materialPrice;

    private String materialMeasure;

    private int materialCategory;

    private String companyId;

    private String purchaseOrderId;
}
