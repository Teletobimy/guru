package com.ezen.guru.service.plan;
import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.domain.Recipe;
import com.ezen.guru.repository.plan.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Integer BicycleId, String materialName) {
        RecipeId recipeId = new RecipeId(BicycleId, materialName);
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findByBicycle(Bicycle bicycle){

        return recipeRepository.findByBicycle(bicycle);
    }

    @Override
    public void deleteByBicycleId(Integer bicycleId) {
        recipeRepository.deleteByBicycleId(bicycleId);
    }


    // 다른 필요한 메서드들의 구현 추가 가능
}
