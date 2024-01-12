package com.ezen.guru.repository.receive;

import com.ezen.guru.domain.Shipment;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShipmentRepository extends JpaRepository<Shipment,Integer>,ShipmentCustomRepository {
    @Query("SELECT NEW com.ezen.guru.dto.receive.ShipmentDetailResponse(" +
            "s.shipmentId, " +
            "s.materialId.materialId, " +
            "s.materialName, " +
            "s.shipmentCnt, " +
            "s.materialMeasure, " +
            "s.materialPrice, " +
            "s.materialCategory, " +
            "s.manager," +
            "s.companyId.companyId,"+
            "s.companyId.companyName, " +
            "s.companyId.ceo, " +
            "s.companyId.tel, " +
            "s.companyId.email, " +
            "s.companyId.address, " +
            "s.shippingDate," +
            "s.purchaseOrderId ) " +
            "FROM Shipment s " +
            "JOIN s.companyId c ON s.materialId.materialId = c.materialId " +
            "WHERE s.shipmentId = :shipmentId")
    ShipmentDetailResponse findByShipmentId(@Param("shipmentId") int shipmentId);
}