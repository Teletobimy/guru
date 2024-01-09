package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBicycle is a Querydsl query type for Bicycle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBicycle extends EntityPathBase<Bicycle> {

    private static final long serialVersionUID = 1827144591L;

    public static final QBicycle bicycle = new QBicycle("bicycle");

    public final StringPath bicycleDescription = createString("bicycleDescription");

    public final NumberPath<Integer> bicycleId = createNumber("bicycleId", Integer.class);

    public final StringPath bicycleName = createString("bicycleName");

    public final NumberPath<Integer> bicyclePrice = createNumber("bicyclePrice", Integer.class);

    public final NumberPath<Integer> bicycleStock = createNumber("bicycleStock", Integer.class);

    public QBicycle(String variable) {
        super(Bicycle.class, forVariable(variable));
    }

    public QBicycle(Path<? extends Bicycle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBicycle(PathMetadata metadata) {
        super(Bicycle.class, metadata);
    }

}
