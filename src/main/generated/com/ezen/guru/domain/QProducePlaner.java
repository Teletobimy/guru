package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProducePlaner is a Querydsl query type for ProducePlaner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProducePlaner extends EntityPathBase<ProducePlaner> {

    private static final long serialVersionUID = -203900634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProducePlaner producePlaner = new QProducePlaner("producePlaner");

    public final QBicycle bicycle;

    public final QProducePlanerId id;

    public final QMaterial material;

    public final NumberPath<Integer> produceBicycleCnt = createNumber("produceBicycleCnt", Integer.class);

    public final NumberPath<Integer> produceMaterialCnt = createNumber("produceMaterialCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> producePlanerDeadline = createDateTime("producePlanerDeadline", java.time.LocalDateTime.class);

    public final NumberPath<Integer> producePlanerStatus = createNumber("producePlanerStatus", Integer.class);

    public QProducePlaner(String variable) {
        this(ProducePlaner.class, forVariable(variable), INITS);
    }

    public QProducePlaner(Path<? extends ProducePlaner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProducePlaner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProducePlaner(PathMetadata metadata, PathInits inits) {
        this(ProducePlaner.class, metadata, inits);
    }

    public QProducePlaner(Class<? extends ProducePlaner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bicycle = inits.isInitialized("bicycle") ? new QBicycle(forProperty("bicycle")) : null;
        this.id = inits.isInitialized("id") ? new QProducePlanerId(forProperty("id")) : null;
        this.material = inits.isInitialized("material") ? new QMaterial(forProperty("material")) : null;
    }

}
