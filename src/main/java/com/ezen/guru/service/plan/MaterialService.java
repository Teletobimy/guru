package com.ezen.guru.service.plan;

import com.ezen.guru.domain.Material;
import com.ezen.guru.dto.plan.MaterialDTO;

import java.util.List;

// MaterialService.java

public interface MaterialService {
    MaterialDTO getMaterialById(int materialId);
    List<MaterialDTO> getAllMaterials();
    void saveMaterial(MaterialDTO materialDTO);
    void updateMaterial(MaterialDTO materialDTO);
    void deleteMaterial(int materialId);
}
