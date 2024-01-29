package com.ezen.guru.dto.plan;
import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.Recipe;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeDTO {

    private int bicycleId;
    private String materialName;
    private int recipeCnt;

    public RecipeDTO(final Recipe entity) {
        this.bicycleId = entity.getId().getBicycleId();
        this.materialName = entity.getId().getMaterial_name();
        this.recipeCnt = entity.getRecipeCnt();
    }

    public static Recipe toEntity(final RecipeDTO dto) {
        return Recipe.builder()
                .id(RecipeId.builder()
                        .bicycleId(dto.getBicycleId())
                        .material_name(dto.getMaterialName())
                        .build())
                .recipeCnt(dto.getRecipeCnt())
                .build();
    }
}
