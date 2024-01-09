package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -1399578867L;

    public static final QCompany company = new QCompany("company");

    public final StringPath address = createString("address");

    public final StringPath ceo = createString("ceo");

    public final StringPath companyId = createString("companyId");

    public final StringPath companyName = createString("companyName");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final StringPath tel = createString("tel");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}
