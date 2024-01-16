package com.ezen.guru.service.plan;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.Material;
import com.ezen.guru.dto.plan.MaterialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// MaterialService.java

public interface MaterialService {
    MaterialDTO getMaterialById(int materialId);
    Page<MaterialDTO> getAllMaterials(String keyword, Integer materialCategory, Pageable pageable);
    void saveMaterial(MaterialDTO materialDTO);
    void updateMaterial(MaterialDTO materialDTO);
    void deleteMaterial(int materialId);

    public List<Code> findByCodeCategory(String materialCategory);
}
