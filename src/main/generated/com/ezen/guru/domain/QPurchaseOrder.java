package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseOrder is a Querydsl query type for PurchaseOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseOrder extends EntityPathBase<PurchaseOrder> {

    private static final long serialVersionUID = -2064498339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseOrder purchaseOrder = new QPurchaseOrder("purchaseOrder");

    public final QCompany company;

    public final QDocument document;

    public final StringPath id = createString("id");

    public final StringPath purchaseOrderMemo = createString("purchaseOrderMemo");

    public final DateTimePath<java.time.LocalDateTime> purchaseOrderRegdate = createDateTime("purchaseOrderRegdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> purchaseOrderStatus = createNumber("purchaseOrderStatus", Integer.class);

    public final NumberPath<Integer> purchaseOrderTotalprice = createNumber("purchaseOrderTotalprice", Integer.class);

    public QPurchaseOrder(String variable) {
        this(PurchaseOrder.class, forVariable(variable), INITS);
    }

    public QPurchaseOrder(Path<? extends PurchaseOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseOrder(PathMetadata metadata, PathInits inits) {
        this(PurchaseOrder.class, metadata, inits);
    }

    public QPurchaseOrder(Class<? extends PurchaseOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document")) : null;
    }

}

//