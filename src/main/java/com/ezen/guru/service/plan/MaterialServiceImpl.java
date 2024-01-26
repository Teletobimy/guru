package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.Material;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.plan.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final CodeRepository codeRepository;


    public List<Code> findByCodeCategory(String codeCategory) {

        return codeRepository.findByCodeCategory(codeCategory);
    }


    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setMaterialId(material.getMaterialId());
        materialDTO.setMaterialCode(material.getMaterialCode());
        materialDTO.setCompanyId(material.getCompanyId());
        materialDTO.setCompanyName(material.getCompanyName());
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
                .companyName(materialDTO.getCompanyName())
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
    public MaterialDTO getMaterialById(int materialId){
        try {
            Optional<Material> materialOptional = materialRepository.findById(materialId);

            if (materialOptional.isPresent()) {
                Material material = materialOptional.get();
                return convertToDTO(material);
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public Page<MaterialDTO> getAllMaterials(String materialName, Integer materialCategory, Pageable pageable) {

        if ((materialName == null || materialName.isEmpty()) && (materialCategory == null || materialCategory == -1)) {
            return materialRepository.findAll(pageable).map(this::convertToDTO);
        } else if (materialName == null || materialName.isEmpty()) {
            return materialRepository.findBymaterialCategory(materialCategory, pageable).map(this::convertToDTO);
        } else if (materialCategory == null || materialCategory == -1) {
            return materialRepository.findByMaterialName(materialName, pageable).map(this::convertToDTO);

        } else {
            return materialRepository.findAllWithkeywordWithcategory(materialName, materialCategory, pageable).map(this::convertToDTO);
        }

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
