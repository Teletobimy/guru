package com.ezen.guru.dto.plan;
import com.ezen.guru.domain.Recipe;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BicycleDTO {

    private int bicycleId;
    private String bicycleName;
    private String bicycleDescription;
    private int bicycleStock;
    private int bicyclePrice;
    private List<RecipeDTO> recipes;
}
