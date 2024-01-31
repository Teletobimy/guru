package com.ezen.guru.config;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecipeId is a Querydsl query type for RecipeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecipeId extends BeanPath<RecipeId> {

    private static final long serialVersionUID = -422288901L;

    public static final QRecipeId recipeId = new QRecipeId("recipeId");

    public final NumberPath<Integer> bicycleId = createNumber("bicycleId", Integer.class);

    public final StringPath material_name = createString("material_name");

    public QRecipeId(String variable) {
        super(RecipeId.class, forVariable(variable));
    }

    public QRecipeId(Path<? extends RecipeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipeId(PathMetadata metadata) {
        super(RecipeId.class, metadata);
    }

}

