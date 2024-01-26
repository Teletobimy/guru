package com.ezen.guru.domain;

import com.ezen.guru.config.RecipeId;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Table(name = "recipe")
@Getter
@Entity
@Setter
@ToString
@NoArgsConstructor
public class Recipe implements Serializable {

    @EmbeddedId
    private RecipeId id;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", insertable = false, updatable = false)
    private Bicycle bicycle;

    @Column(name = "recipe_cnt")
    private int recipeCnt;

    @Builder
    public Recipe(RecipeId id, Bicycle bicycle, int recipeCnt) {
        this.id = id;
        this.bicycle = bicycle;
        this.recipeCnt = recipeCnt;
    }


}
