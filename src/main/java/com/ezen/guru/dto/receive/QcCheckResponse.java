package com.ezen.guru.dto.receive;

import com.ezen.guru.domain.Material;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class QcCheckResponse {
    private String materialName;
    private int qcCheckId;
    private int returnStatus;
    private int shipmentId;
    private int materialId;
    private String manager;
    private int qcCheckCnt;
    private int processStatus;
    private String purchaseOrderId;
    private LocalDateTime qccheckDate;

    public QcCheckResponse(String materialName, int qcCheckId,
                           int returnStatus, int shipmentId,
                           int materialId, String manager,
                           int qcCheckCnt, int processStatus, String purchaseOrderId, LocalDateTime qccheckDate) {
        this.materialName = materialName;
        this.qcCheckId = qcCheckId;
        this.returnStatus = returnStatus;
        this.shipmentId = shipmentId;
        this.materialId = materialId;
        this.manager = manager;
        this.qcCheckCnt = qcCheckCnt;
        this.processStatus = processStatus;
        this.purchaseOrderId = purchaseOrderId;
        this.qccheckDate = qccheckDate;
    }
}
