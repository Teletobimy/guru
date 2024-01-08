package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.ProducePlaner;
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
public class ProducePlanerDTO {

    private String producePlanerId;
    private int bicycleId;
    private String bicycleName;
    private int produceBicycleCnt;
    private int materialId;
    private String materialName;
    private int produceMaterialCnt;
    private LocalDateTime producePlanerDeadline;
    private int producePlanerStatus;

    public ProducePlanerDTO (final ProducePlaner entity) {
        this.producePlanerId = entity.getId().getProducePlanerId();
        this.bicycleId = entity.getId().getBicycleId();
        this.bicycleName = entity.getBicycle().getBicycleName();
        this.produceBicycleCnt = entity.getProduceBicycleCnt();
        this.materialId = entity.getId().getMaterialId();
        this.materialName = entity.getMaterial().getMaterialName();
        this.produceMaterialCnt = entity.getProduceMaterialCnt();
        this.producePlanerDeadline = entity.getProducePlanerDeadline();
        this.producePlanerStatus = entity.getProducePlanerStatus();
    }

    public static ProducePlaner toEntity(final ProducePlanerDTO dto) {
        return ProducePlaner.builder()
                .id(ProducePlanerId.builder()
                        .producePlanerId(dto.getProducePlanerId())
                        .bicycleId(dto.getBicycleId())
                        .materialId(dto.getMaterialId())
                        .build())
                .produceBicycleCnt(dto.getProduceBicycleCnt())
                .produceMaterialCnt(dto.getProduceMaterialCnt())
                .producePlanerDeadline(dto.getProducePlanerDeadline())
                .producePlanerStatus(dto.getProducePlanerStatus())
                .build();
    }
}
