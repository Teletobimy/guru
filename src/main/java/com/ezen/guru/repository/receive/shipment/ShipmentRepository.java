package com.ezen.guru.repository.receive.shipment;

import com.ezen.guru.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment,Integer>,ShipmentCustomRepository {
}
