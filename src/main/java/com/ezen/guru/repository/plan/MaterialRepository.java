package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Page<Material> findBymaterialCategory(int materialCategory, Pageable pageable);

    @Query("SELECT b FROM Material b WHERE (:materialName IS NULL OR LOWER(b.materialName) LIKE LOWER(CONCAT('%', :materialName, '%'))) AND (:materialCategory IS NULL OR b.materialCategory = :materialCategory OR :materialCategory = -1) ORDER BY b.materialId ASC")
    Page<Material> findAllWithKeyword(
            @Param("materialName") String materialName,
            @Param("materialCategory") Integer materialCategory,
            Pageable pageable
    );





    @Query("SELECT b FROM Material b WHERE (:materialName IS NULL OR LOWER(b.materialName) LIKE LOWER(CONCAT('%', :materialName, '%'))) ORDER BY b.materialId ASC")
    Page<Material> findByMaterialName(@Param("materialName") String materialName, Pageable pageable);

    @Query("SELECT b FROM Material b WHERE (:materialName IS NULL OR LOWER(b.materialName) LIKE LOWER(CONCAT('%', :materialName, '%'))) AND (:materialCategory IS NULL OR b.materialCategory = :materialCategory) ORDER BY b.materialId ASC")
    Page<Material> findAllWithkeywordWithcategory(@Param("materialName") String materialName, @Param("materialCategory") Integer materialCategory, Pageable pageable);


    Material findByMaterialId(int materialId);

    @Modifying
    @Query("UPDATE Material m SET m.materialStock = m.materialStock + :qcCheckCnt WHERE m.materialId = (SELECT q.materialId.materialId FROM QcCheck q WHERE q.qcCheckId = :qcCheckId)")
    void updateMaterialStock(@Param("qcCheckId")int qcCheckId, @Param("qcCheckCnt")int qcCheckCnt);

    @Query("SELECT m.materialName, m.materialDescription, m.materialCategory, SUM(m.materialStock) AS totalStock FROM Material m GROUP BY m.materialName, m.materialDescription, m.materialCategory")
    Page<Object[]> getTotalStockByMaterial(Pageable pageable);
}
