package com.ezen.guru.repository.receive;

import com.ezen.guru.domain.QcCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QcCheckRepository extends JpaRepository<QcCheck, Integer>, JpaSpecificationExecutor<QcCheck> {
    @Query("SELECT q FROM QcCheck q WHERE q.shipmentId = :shipmentId")
    QcCheck findByShipmentId(@Param("shipmentId") int shipmentId);

    Page<QcCheck> findAll(Specification<QcCheck> spec, Pageable pageable);

    // 정품
    @Modifying(clearAutomatically = true)
    @Query("UPDATE QcCheck qc SET qc.returnStatus = 2, qc.qcCheckCnt = qc.qcCheckCnt - :qcCheckCnt, qc.passCnt = qc.passCnt + :qcCheckCnt WHERE qc.qcCheckId = :qcCheckId AND qc.qcCheckCnt >= :qcCheckCnt")
    int updatePurchaseReturnStatus(@Param("qcCheckId") int qcCheckId, @Param("qcCheckCnt") int qcCheckCnt);


    // 반품
    @Modifying(clearAutomatically = true)
    @Query("UPDATE QcCheck qc SET qc.returnStatus = 1, qc.qcCheckCnt = qc.qcCheckCnt - :qcCheckCnt, qc.returnCnt = qc.returnCnt + :qcCheckCnt   WHERE qc.qcCheckId = :qcCheckId AND qc.qcCheckCnt >= :qcCheckCnt")
    int updateShipmentReturnStatus(@Param("qcCheckId") int qcCheckId, @Param("qcCheckCnt") int qcCheckCnt);

    // 검수 상태
    @Modifying(clearAutomatically = true)
    @Query("UPDATE QcCheck qc SET qc.processStatus = 3 WHERE qc.qcCheckCnt = 0")
    int updateProcessStatus();

    Long countBy();

    Long countByProcessStatus(int processStatus);

}