package com.ezen.guru.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaterial is a Querydsl query type for Material
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterial extends EntityPathBase<Material> {

    private static final long serialVersionUID = 461558935L;

    public static final QMaterial material = new QMaterial("material");

    public final StringPath companyId = createString("companyId");

    public final NumberPath<Integer> materialCategory = createNumber("materialCategory", Integer.class);

    public final StringPath materialCode = createString("materialCode");

    public final StringPath materialDescription = createString("materialDescription");

    public final NumberPath<Integer> materialId = createNumber("materialId", Integer.class);

    public final StringPath materialMeasure = createString("materialMeasure");

    public final StringPath materialName = createString("materialName");

    public final NumberPath<Integer> materialPrice = createNumber("materialPrice", Integer.class);

    public final NumberPath<Integer> materialStock = createNumber("materialStock", Integer.class);

    public QMaterial(String variable) {
        super(Material.class, forVariable(variable));
    }

    public QMaterial(Path<? extends Material> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaterial(PathMetadata metadata) {
        super(Material.class, metadata);
    }

}

//