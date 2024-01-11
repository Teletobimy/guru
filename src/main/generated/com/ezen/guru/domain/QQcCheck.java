package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQcCheck is a Querydsl query type for QcCheck
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQcCheck extends EntityPathBase<QcCheck> {

    private static final long serialVersionUID = 2052965542L;

    public static final QQcCheck qcCheck = new QQcCheck("qcCheck");

    public final StringPath manager = createString("manager");

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final NumberPath<Integer> processStatus = createNumber("processStatus", Integer.class);

    public final NumberPath<Integer> qcCheckCnt = createNumber("qcCheckCnt", Integer.class);

    public final NumberPath<Integer> qcCheckId = createNumber("qcCheckId", Integer.class);

    public final NumberPath<Integer> returnStatus = createNumber("returnStatus", Integer.class);

    public final NumberPath<Integer> shipmentId = createNumber("shipmentId", Integer.class);

    public QQcCheck(String variable) {
        super(QcCheck.class, forVariable(variable));
    }

    public QQcCheck(Path<? extends QcCheck> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQcCheck(PathMetadata metadata) {
        super(QcCheck.class, metadata);
    }

}

