package com.ezen.guru.repository.receive;

import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QcCheckRepository extends JpaRepository<QcCheck, Integer>, JpaSpecificationExecutor<QcCheck> {
    @Query("SELECT q FROM QcCheck q WHERE q.shipmentId = :shipmentId")
    QcCheck findByShipmentId(@Param("shipmentId") int shipmentId);

    Page<QcCheck> findAll(Specification<QcCheck>spec, Pageable pageable);
}
