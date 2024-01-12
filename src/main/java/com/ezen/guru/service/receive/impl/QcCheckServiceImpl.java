package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.receive.QcCheckRepository;
import com.ezen.guru.repository.receive.QcCheckSpecifications;
import com.ezen.guru.service.receive.QcCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QcCheckServiceImpl implements QcCheckService {
    private final QcCheckRepository qcCheckRepository;
    private final CodeRepository codeRepository;

    @Override
    public Page<QcCheckResponse> qcCheckList(int processStatus, String search, Pageable pageable) {
        Specification<QcCheck> spec = Specification.where(null);
        if(processStatus > 0){
            spec = spec.and(QcCheckSpecifications.processStatusEquals(processStatus));
        }
        if(search != null && !search.isEmpty()){
            spec = spec.and(QcCheckSpecifications.searchContains(search));
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
}
