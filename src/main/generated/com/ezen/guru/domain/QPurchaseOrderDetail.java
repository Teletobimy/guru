package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseOrderDetail is a Querydsl query type for PurchaseOrderDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseOrderDetail extends EntityPathBase<PurchaseOrderDetail> {

    private static final long serialVersionUID = -1376777202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseOrderDetail purchaseOrderDetail = new QPurchaseOrderDetail("purchaseOrderDetail");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMaterial material;

    public final NumberPath<Integer> materialCategory = createNumber("materialCategory", Integer.class);

    public final StringPath materialMeasure = createString("materialMeasure");

    public final StringPath materialName = createString("materialName");

    public final NumberPath<Integer> materialPrice = createNumber("materialPrice", Integer.class);

    public final QPurchaseOrder purchaseOrder;

    public final NumberPath<Integer> purchaseOrderCnt = createNumber("purchaseOrderCnt", Integer.class);

    public QPurchaseOrderDetail(String variable) {
        this(PurchaseOrderDetail.class, forVariable(variable), INITS);
    }

    public QPurchaseOrderDetail(Path<? extends PurchaseOrderDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseOrderDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseOrderDetail(PathMetadata metadata, PathInits inits) {
        this(PurchaseOrderDetail.class, metadata, inits);
    }

    public QPurchaseOrderDetail(Class<? extends PurchaseOrderDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.material = inits.isInitialized("material") ? new QMaterial(forProperty("material")) : null;
        this.purchaseOrder = inits.isInitialized("purchaseOrder") ? new QPurchaseOrder(forProperty("purchaseOrder"), inits.get("purchaseOrder")) : null;
    }

}

//