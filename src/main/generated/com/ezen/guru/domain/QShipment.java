package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShipment is a Querydsl query type for Shipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipment extends EntityPathBase<Shipment> {

    private static final long serialVersionUID = -353836790L;

    public static final QShipment shipment = new QShipment("shipment");

    public final StringPath companyId = createString("companyId");

    public final NumberPath<Integer> materialCategory = createNumber("materialCategory", Integer.class);

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final StringPath materialMeasure = createString("materialMeasure");

    public final StringPath materialName = createString("materialName");

    public final NumberPath<Integer> materialPrice = createNumber("materialPrice", Integer.class);

    public final NumberPath<Integer> shipmentCnt = createNumber("shipmentCnt", Integer.class);

    public final NumberPath<Integer> shipmentId = createNumber("shipmentId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> shippingDate = createDateTime("shippingDate", java.time.LocalDateTime.class);

    public QShipment(String variable) {
        super(Shipment.class, forVariable(variable));
    }

    public QShipment(Path<? extends Shipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipment(PathMetadata metadata) {
        super(Shipment.class, metadata);
    }

}

