package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExport is a Querydsl query type for Export
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExport extends EntityPathBase<Export> {

    private static final long serialVersionUID = 1405984548L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExport export = new QExport("export");

    public final StringPath bicycleName = createString("bicycleName");

    public final NumberPath<Integer> exportCnt = createNumber("exportCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> exportDate = createDateTime("exportDate", java.time.LocalDateTime.class);

    public final QProducePlanerId id;

    public final StringPath materialName = createString("materialName");

    public final QProducePlaner producePlaner;

    public QExport(String variable) {
        this(Export.class, forVariable(variable), INITS);
    }

    public QExport(Path<? extends Export> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExport(PathMetadata metadata, PathInits inits) {
        this(Export.class, metadata, inits);
    }

    public QExport(Class<? extends Export> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QProducePlanerId(forProperty("id")) : null;
        this.producePlaner = inits.isInitialized("producePlaner") ? new QProducePlaner(forProperty("producePlaner"), inits.get("producePlaner")) : null;
    }

}

