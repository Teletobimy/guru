package com.ezen.guru.dto.export;

import com.ezen.guru.domain.Export;
import com.ezen.guru.domain.ProducePlanerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportDTO {

    private String producePlanerId;  // 복합 기본 키의 일부분
    private int bicycleId;  // 복합 기본 키의 일부분
    private int materialId;  // 복합 기본 키의 일부분
    private String bicycleName;
    private String materialName;
    private int exportCnt;
    private LocalDateTime exportDate;

    public ExportDTO(final Export entity) {
        this.producePlanerId = entity.getEmbeddedId().getProducePlanerId();
        this.bicycleId = entity.getEmbeddedId().getBicycleId();
        this.materialId = entity.getEmbeddedId().getMaterialId();
        this.bicycleName = entity.getBicycleName();
        this.materialName = entity.getMaterialName();
        this.exportCnt = entity.getExportCnt();
        this.exportDate = entity.getExportDate();
    }

    public static Export toEntity(final ExportDTO dto) {
        return Export.builder()
                .embeddedId(ProducePlanerId.builder()
                        .producePlanerId(dto.getProducePlanerId())
                        .bicycleId(dto.getBicycleId())
                        .materialId(dto.getMaterialId())
                        .build())
                .bicycleName(dto.getBicycleName())
                .materialName(dto.getMaterialName())
                .exportCnt(dto.getExportCnt())
                .exportDate(dto.getExportDate())
                .build();
    }
}
