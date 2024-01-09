package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentDetail is a Querydsl query type for DocumentDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocumentDetail extends EntityPathBase<DocumentDetail> {

    private static final long serialVersionUID = 95252668L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentDetail documentDetail = new QDocumentDetail("documentDetail");

    public final QDocument document;

    public final NumberPath<Integer> documentCnt = createNumber("documentCnt", Integer.class);

    public final StringPath documentMeasure = createString("documentMeasure");

    public final NumberPath<Integer> documentPrice = createNumber("documentPrice", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final StringPath materialName = createString("materialName");

    public QDocumentDetail(String variable) {
        this(DocumentDetail.class, forVariable(variable), INITS);
    }

    public QDocumentDetail(Path<? extends DocumentDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocumentDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocumentDetail(PathMetadata metadata, PathInits inits) {
        this(DocumentDetail.class, metadata, inits);
    }

    public QDocumentDetail(Class<? extends DocumentDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document")) : null;
    }

}

//