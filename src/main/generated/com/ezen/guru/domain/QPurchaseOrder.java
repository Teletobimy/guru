package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPurchaseOrder is a Querydsl query type for PurchaseOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseOrder extends EntityPathBase<PurchaseOrder> {

    private static final long serialVersionUID = -2064498339L;

    public static final QPurchaseOrder purchaseOrder = new QPurchaseOrder("purchaseOrder");

    public final StringPath companyId = createString("companyId");

    public final StringPath documentId = createString("documentId");

    public final StringPath id = createString("id");

    public final StringPath purchaseOrderMemo = createString("purchaseOrderMemo");

    public final DateTimePath<java.time.LocalDateTime> regdate = createDateTime("regdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> totalprice = createNumber("totalprice", Integer.class);

    public QPurchaseOrder(String variable) {
        super(PurchaseOrder.class, forVariable(variable));
    }

    public QPurchaseOrder(Path<? extends PurchaseOrder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPurchaseOrder(PathMetadata metadata) {
        super(PurchaseOrder.class, metadata);
    }

}

