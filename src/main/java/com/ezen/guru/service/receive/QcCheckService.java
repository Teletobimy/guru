package com.ezen.guru.service.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.Shipment;
import com.ezen.guru.dto.receive.QcCheckRequest;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface QcCheckService {
    public Page<QcCheckResponse> qcCheckList(int processStatus, String search, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    public List<Code> findByCodeCategory(String codeCategory);
    public int updateAllStatus(int qcCheckId,int qcCheckCnt);

    public Shipment addShipment(QcCheckRequest shipment, int cnt);

    public int updateReturnStatus(int qcCheckId, int qcCheckCnt);
}
