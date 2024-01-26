package com.ezen.guru.repository.plan;


import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {
    List<Recipe> findByBicycle(Bicycle Bicycle);

    @Transactional
    @Modifying
    @Query("DELETE FROM Recipe r WHERE r.id.bicycleId = :bicycleId")
    void deleteByBicycleId(@Param("bicycleId") int bicycleId);
}