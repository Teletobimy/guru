package com.ezen.guru.repository.receive;

import com.ezen.guru.domain.QcCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QcCheckRepository extends JpaRepository<QcCheck, Integer> {
    @Query("SELECT q FROM QcCheck q WHERE q.shipmentId = :shipmentId")
    QcCheck findByShipmentId(@Param("shipmentId") int shipmentId);

}
