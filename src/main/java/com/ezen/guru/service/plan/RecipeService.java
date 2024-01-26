package com.ezen.guru.service.plan;
import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.domain.Recipe;
import com.ezen.guru.dto.plan.RecipeDTO;

import java.util.List;

public interface RecipeService {
    Recipe saveRecipe(Recipe recipe);


    void deleteRecipe(Integer BicycleId, String materialName);

    List<Recipe> getAllRecipes();
    // 다른 필요한 메서드들 추가 가능
    List<Recipe> findByBicycle(Bicycle bicycle);

    void deleteByBicycleId(Integer bicycleId);
}

