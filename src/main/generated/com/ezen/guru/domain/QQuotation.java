package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuotation is a Querydsl query type for Quotation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuotation extends EntityPathBase<Quotation> {

    private static final long serialVersionUID = -743435236L;

    public static final QQuotation quotation = new QQuotation("quotation");

    public final NumberPath<Integer> biddingNo = createNumber("biddingNo", Integer.class);

    public final StringPath company_id = createString("company_id");

    public final StringPath company_name = createString("company_name");

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final StringPath leadTime = createString("leadTime");

    public final StringPath paymentTerms = createString("paymentTerms");

    public final StringPath quotation_memo = createString("quotation_memo");

    public final NumberPath<Integer> quotation_totalprice = createNumber("quotation_totalprice", Integer.class);

    public final ListPath<QuotationDetail, QQuotationDetail> quotationDetails = this.<QuotationDetail, QQuotationDetail>createList("quotationDetails", QuotationDetail.class, QQuotationDetail.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regdate = createDateTime("regdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tradeTerms = createString("tradeTerms");

    public QQuotation(String variable) {
        super(Quotation.class, forVariable(variable));
    }

    public QQuotation(Path<? extends Quotation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuotation(PathMetadata metadata) {
        super(Quotation.class, metadata);
    }

}

