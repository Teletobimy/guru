package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 1024213131L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final NumberPath<Integer> biddingNo = createNumber("biddingNo", Integer.class);

    public final QCompany company;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final StringPath document_memo = createString("document_memo");

    public final NumberPath<Integer> document_totalprice = createNumber("document_totalprice", Integer.class);

    public final ListPath<DocumentDetail, QDocumentDetail> documentDetails = this.<DocumentDetail, QDocumentDetail>createList("documentDetails", DocumentDetail.class, QDocumentDetail.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final StringPath leadTime = createString("leadTime");

    public final StringPath paymentTerms = createString("paymentTerms");

    public final DateTimePath<java.time.LocalDateTime> regdate = createDateTime("regdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tradeTerms = createString("tradeTerms");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

