package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuotationDetail is a Querydsl query type for QuotationDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuotationDetail extends EntityPathBase<QuotationDetail> {

    private static final long serialVersionUID = -1890675827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuotationDetail quotationDetail = new QQuotationDetail("quotationDetail");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMaterial material;

    public final StringPath materialName = createString("materialName");

    public final QQuotation quotation;

    public final NumberPath<Integer> quotationCnt = createNumber("quotationCnt", Integer.class);

    public final StringPath quotationMeasure = createString("quotationMeasure");

    public final NumberPath<Integer> quotationPrice = createNumber("quotationPrice", Integer.class);

    public QQuotationDetail(String variable) {
        this(QuotationDetail.class, forVariable(variable), INITS);
    }

    public QQuotationDetail(Path<? extends QuotationDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuotationDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuotationDetail(PathMetadata metadata, PathInits inits) {
        this(QuotationDetail.class, metadata, inits);
    }

    public QQuotationDetail(Class<? extends QuotationDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.material = inits.isInitialized("material") ? new QMaterial(forProperty("material")) : null;
        this.quotation = inits.isInitialized("quotation") ? new QQuotation(forProperty("quotation"), inits.get("quotation")) : null;
    }

}

