package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQcCheck is a Querydsl query type for QcCheck
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQcCheck extends EntityPathBase<QcCheck> {

    private static final long serialVersionUID = 2052965542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQcCheck qcCheck = new QQcCheck("qcCheck");

    public final StringPath manager = createString("manager");

    public final QMaterial materialId;

    public final NumberPath<Integer> passCnt = createNumber("passCnt", Integer.class);

    public final NumberPath<Integer> processStatus = createNumber("processStatus", Integer.class);

    public final NumberPath<Integer> purchaseOrderDetailId = createNumber("purchaseOrderDetailId", Integer.class);

    public final StringPath purchaseOrderId = createString("purchaseOrderId");

    public final NumberPath<Integer> qcCheckCnt = createNumber("qcCheckCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> qccheckDate = createDateTime("qccheckDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> qcCheckId = createNumber("qcCheckId", Integer.class);

    public final NumberPath<Integer> returnCnt = createNumber("returnCnt", Integer.class);

    public final NumberPath<Integer> returnStatus = createNumber("returnStatus", Integer.class);

    public final NumberPath<Integer> shipmentId = createNumber("shipmentId", Integer.class);

    public QQcCheck(String variable) {
        this(QcCheck.class, forVariable(variable), INITS);
    }

    public QQcCheck(Path<? extends QcCheck> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQcCheck(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQcCheck(PathMetadata metadata, PathInits inits) {
        this(QcCheck.class, metadata, inits);
    }

    public QQcCheck(Class<? extends QcCheck> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.materialId = inits.isInitialized("materialId") ? new QMaterial(forProperty("materialId")) : null;
    }

}

