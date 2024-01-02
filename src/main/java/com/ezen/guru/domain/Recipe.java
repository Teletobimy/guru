package com.ezen.guru.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "recipe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Recipe {


    @Column(name="bicycle_id")
    private int bicycleId;
    @Column(name="material_code")
    private String materialCode;
    @Column(name="recipe_cnt")
    private int recipeCnt;

    @Builder
    public Recipe(int bicycleId,
                  String materialCode,
                  int recipeCnt) {
        this.bicycleId = bicycleId;
        this.materialCode = materialCode;
        this.recipeCnt = recipeCnt;
    }
}
