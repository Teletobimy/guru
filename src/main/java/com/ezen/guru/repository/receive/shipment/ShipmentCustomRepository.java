package com.ezen.guru.repository.receive.shipment;

import com.ezen.guru.domain.Shipment;

import java.util.List;

public interface ShipmentCustomRepository {
List<Shipment> shipmentList(Shipment shipment);
}
