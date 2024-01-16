package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProducePlanerId is a Querydsl query type for ProducePlanerId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProducePlanerId extends BeanPath<ProducePlanerId> {

    private static final long serialVersionUID = 1619988705L;

    public static final QProducePlanerId producePlanerId1 = new QProducePlanerId("producePlanerId1");

    public final NumberPath<Integer> bicycleId = createNumber("bicycleId", Integer.class);

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final StringPath producePlanerId = createString("producePlanerId");

    public QProducePlanerId(String variable) {
        super(ProducePlanerId.class, forVariable(variable));
    }

    public QProducePlanerId(Path<? extends ProducePlanerId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProducePlanerId(PathMetadata metadata) {
        super(ProducePlanerId.class, metadata);
    }

}

