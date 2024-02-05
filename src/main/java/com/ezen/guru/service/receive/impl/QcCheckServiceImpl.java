package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.receive.QcCheckRequest;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.plan.MaterialRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.receive.QcCheckRepository;
import com.ezen.guru.repository.receive.QcCheckSpecifications;
import com.ezen.guru.repository.receive.ShipmentRepository;
import com.ezen.guru.service.receive.QcCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class QcCheckServiceImpl implements QcCheckService {
    private final QcCheckRepository qcCheckRepository;
    private final CodeRepository codeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ShipmentRepository shipmentRepository;
    private final MaterialRepository materialRepository;
    @Override
    public Page<QcCheckResponse> qcCheckList(int processStatus, String search, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<QcCheck> spec = Specification.where(null);
        if(processStatus >= 1){
            spec = spec.and(QcCheckSpecifications.processStatusEquals(processStatus));
        }
        if(search != null && !search.isEmpty()){
            spec = spec.and(QcCheckSpecifications.searchContains(search));
        }
        if(startDate != null && endDate != null){
            spec = spec.and(QcCheckSpecifications.dateRangeFilter(startDate, endDate));
        }
        // Fetch the entities from the repository using the specification
        Page<QcCheck> qcCheckPage = qcCheckRepository.findAll(spec, pageable);

        // Convert the entities to DTOs (QcCheckResponse)
        return qcCheckPage.map(qcCheck -> new QcCheckResponse(
                qcCheck.getMaterialId().getMaterialName(),
                qcCheck.getQcCheckId(),
                qcCheck.getReturnStatus(),
                qcCheck.getShipmentId(),
                qcCheck.getMaterialId().getMaterialId(),
                qcCheck.getManager(),
                qcCheck.getQcCheckCnt(),
                qcCheck.getPassCnt(),
                qcCheck.getReturnCnt(),
                qcCheck.getProcessStatus(),
                qcCheck.getPurchaseOrderId(),
                qcCheck.getQccheckDate()
        ));
    }

    @Override
    public List<Code> findByCodeCategory(String codeCategory) {
        return codeRepository.findByCodeCategory(codeCategory);
    }

    @Override
    @Transactional
    public int updateAllStatus( int qcCheckId, int qcCheckCnt) {
        try {
            int result = qcCheckRepository.updatePurchaseReturnStatus(qcCheckId, qcCheckCnt);
            if(result == 0){
                return 0;
            }
            orderDetailRepository.updateQcCheckCnt(qcCheckId, qcCheckCnt);
            qcCheckRepository.updateProcessStatus();
            materialRepository.updateMaterialStock(qcCheckId, qcCheckCnt);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    @Transactional
    public int updateReturnStatus(int qcCheckId, int qcCheckCnt) {
        try {
            int result = qcCheckRepository.updateShipmentReturnStatus(qcCheckId, qcCheckCnt);
            if(result == 0){
                return 0;
            }
            qcCheckRepository.updateProcessStatus();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Shipment addShipment(QcCheckRequest shipment, int cnt, int detailId) {
        LocalDateTime targetDateTime = LocalDateTime.now();

        Shipment shipmentItem = Shipment.builder()
                .materialNumber(shipment.getMaterialNumber())
                .manager(shipment.getManager())
                .shippingDate(targetDateTime)
                .shipmentCnt(cnt)
                .materialName(shipment.getMaterialName())
                .companyid(shipment.getCompanyId())
                .materialCategory(shipment.getMaterialCategory())
                .materialPrice(shipment.getMaterialPrice())
                .materialMeasure(shipment.getMaterialMeasure())
                .purchaseOrderId(shipment.getPurchaseOrderId())
                .purchaseOrderDetailId(detailId)
                .build();

        return shipmentRepository.save(shipmentItem);
    }

    @Override
    public Long countBy() {
        return qcCheckRepository.countBy();
    }

    @Override
    public Long countByProcessStatus(int processStatus) {
        return qcCheckRepository.countByProcessStatus(processStatus);
    }

    @Override
    public Long passCntSum() {
        if(qcCheckRepository.passCntSum() == null){
            return Long.valueOf(100);
        }
        return qcCheckRepository.passCntSum();
    }

    @Override
    public Long returnSum() {
        if(qcCheckRepository.returnSum() == null){
            return Long.valueOf(0);
        }
        return qcCheckRepository.returnSum();
    }

    @Override
    public Long totalSum() {
        if(qcCheckRepository.totalSum() == null){
            return Long.valueOf(100);
        }
        return qcCheckRepository.totalSum();
    }

}
