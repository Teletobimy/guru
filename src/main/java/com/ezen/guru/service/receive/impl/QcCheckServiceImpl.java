package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.receive.QcCheckRepository;
import com.ezen.guru.repository.receive.QcCheckSpecifications;
import com.ezen.guru.service.receive.QcCheckService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
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
    private final EntityManager entityManager;
    @Override
    public Page<QcCheckResponse> qcCheckList(int processStatus, String search, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<QcCheck> spec = Specification.where(null);
        if(processStatus > 0){
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
        qcCheckRepository.updatePurchaseReturnStatus(qcCheckId, qcCheckCnt);
        orderDetailRepository.updateQcCheckCnt(qcCheckId, qcCheckCnt);
        qcCheckRepository.updateProcessStatus();

        log.info("id: " + qcCheckId + ", cnt: " + qcCheckCnt);

        return 1;

    }


}
