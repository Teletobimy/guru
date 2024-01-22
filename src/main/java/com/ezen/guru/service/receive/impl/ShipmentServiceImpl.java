package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.receive.QcCheckRepository;
import com.ezen.guru.repository.receive.ShipmentRepository;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final CodeRepository codeRepository;
    private final QcCheckRepository qcCheckRepository;

    @Override
    public Page<ShipmentResponse> shipmentList(int size, int page,String keyword, int category,LocalDateTime startDate, LocalDateTime endDate) {
       return shipmentRepository.shipmentList(size, page,keyword,category,startDate,endDate);

    }


    @Override
    public List<Code> findByCodeCategory(String codeCategory) {

        return codeRepository.findByCodeCategory(codeCategory);
    }

    @Override
    public ShipmentDetailResponse findByShimentId(int shipmentId) {
        return shipmentRepository.findByShipmentId(shipmentId);
    }

    @Transactional
    @Override
    public QcCheck addQcCheck(int shipmentId) {
        ShipmentDetailResponse shipment = shipmentRepository.findByShipmentId(shipmentId);
        LocalDateTime targetDateTime = LocalDateTime.now();

        QcCheck qcCheck = QcCheck.builder()
                .returnStatus(0)
                .shipmentId(shipment.getShipmentId())
                .materialId(shipment.getMaterialId())
                .manager(shipment.getManager())
                .qcCheckCnt(shipment.getShipmentCnt())
                .processStatus(2)
                .purchaseOrderId(shipment.getPurchaseOrderId())
                .qccheckDate(targetDateTime)
                .purchaseOrderDetailId(shipment.getPurchaseOrderDetailId())
                .build();

        return qcCheckRepository.save(qcCheck);
    }

    @Override
    public List<QcCheck> qcCheckList() {
        return qcCheckRepository.findAll();
    }

    @Override
    public QcCheck qcCheck(int shipmentId) {
        return qcCheckRepository.findByShipmentId(shipmentId);
    }
}
