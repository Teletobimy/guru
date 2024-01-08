package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Material;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.repository.plan.MaterialRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setMaterialId(material.getMaterialId());
        materialDTO.setMaterialCode(material.getMaterialCode());
        materialDTO.setCompanyId(material.getCompanyId());
        materialDTO.setMaterialName(material.getMaterialName());
        materialDTO.setMaterialDescription(material.getMaterialDescription());
        materialDTO.setMaterialStock(material.getMaterialStock());
        materialDTO.setMaterialPrice(material.getMaterialPrice());
        materialDTO.setMaterialMeasure(material.getMaterialMeasure());
        materialDTO.setMaterialCategory(material.getMaterialCategory());
        return materialDTO;
    }

    private Material convertToEntity(MaterialDTO materialDTO) {
        Material material = Material.builder()
                .materialId(materialDTO.getMaterialId())
                .materialCode(materialDTO.getMaterialCode())
                .companyId(materialDTO.getCompanyId())
                .materialName(materialDTO.getMaterialName())
                .materialDescription(materialDTO.getMaterialDescription())
                .materialStock(materialDTO.getMaterialStock())
                .materialPrice(materialDTO.getMaterialPrice())
                .materialMeasure(materialDTO.getMaterialMeasure())
                .materialCategory(materialDTO.getMaterialCategory())
                .build();
        return material;
    }

    @Override
    public MaterialDTO getMaterialById(int materialId) {
      return  null;
    }

    @Override
    public List<MaterialDTO> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        return materials.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveMaterial(MaterialDTO materialDTO) {
        Material material = convertToEntity(materialDTO);
        materialRepository.save(material);
    }

    @Override
    public void updateMaterial(MaterialDTO materialDTO) {
        // Logic to update Material based on MaterialDTO
    }

    @Override
    public void deleteMaterial(int materialId) {
        materialRepository.deleteById(materialId);
    }
}
