package com.ezen.guru.dto.plan;
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

}
