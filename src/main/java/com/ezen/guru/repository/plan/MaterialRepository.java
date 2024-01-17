package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.Material;
import com.ezen.guru.dto.plan.MaterialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Page<Material> findBymaterialCategory(int materialCategory, Pageable pageable);

//    @Query("SELECT b FROM material b WHERE " +
//            "(:materialName IS NULL OR LOWER(b.keyword) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
//            "ORDER BY b.materialId ASC")
//    Page<Material> findAllWithkeywordWithcategory(String keyword, int category, Pageable pageable);
//    // 추가적인 Material 관련 메서드가 필요한 경우 여기에 추가할 수 있습니다.

    @Query("SELECT b FROM Material b WHERE (:materialName IS NULL OR LOWER(b.materialName) LIKE LOWER(CONCAT('%', :materialName, '%'))) AND (:materialCategory IS NULL OR b.materialCategory = :materialCategory) ORDER BY b.materialId ASC")
    Page<Material> findAllWithkeywordWithcategory(@Param("materialName") String materialName, @Param("materialCategory") Integer materialCategory, Pageable pageable);

}
