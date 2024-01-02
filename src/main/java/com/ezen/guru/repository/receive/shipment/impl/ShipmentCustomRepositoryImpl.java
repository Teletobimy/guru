package com.ezen.guru.repository.receive.shipment.impl;

import com.ezen.guru.domain.QShipment;
import com.ezen.guru.domain.Shipment;
import com.ezen.guru.repository.receive.shipment.ShipmentCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ShipmentCustomRepositoryImpl implements ShipmentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Shipment> shipmentList(Shipment shipment) {
        QShipment qShipment = QShipment.shipment;
        return jpaQueryFactory
                .selectFrom(qShipment)
                .fetch();
    }
}
