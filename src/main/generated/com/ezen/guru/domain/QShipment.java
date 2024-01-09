package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipment is a Querydsl query type for Shipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipment extends EntityPathBase<Shipment> {

    private static final long serialVersionUID = -353836790L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipment shipment = new QShipment("shipment");

    public final QCompany companyId;

    public final StringPath manager = createString("manager");

    public final NumberPath<Integer> materialCategory = createNumber("materialCategory", Integer.class);

    public final QMaterial materialId;

    public final StringPath materialMeasure = createString("materialMeasure");

    public final StringPath materialName = createString("materialName");

    public final NumberPath<Integer> materialPrice = createNumber("materialPrice", Integer.class);

    public final NumberPath<Integer> shipmentCnt = createNumber("shipmentCnt", Integer.class);

    public final NumberPath<Integer> shipmentId = createNumber("shipmentId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> shippingDate = createDateTime("shippingDate", java.time.LocalDateTime.class);

    public QShipment(String variable) {
        this(Shipment.class, forVariable(variable), INITS);
    }

    public QShipment(Path<? extends Shipment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipment(PathMetadata metadata, PathInits inits) {
        this(Shipment.class, metadata, inits);
    }

    public QShipment(Class<? extends Shipment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companyId = inits.isInitialized("companyId") ? new QCompany(forProperty("companyId")) : null;
        this.materialId = inits.isInitialized("materialId") ? new QMaterial(forProperty("materialId")) : null;
    }

}

