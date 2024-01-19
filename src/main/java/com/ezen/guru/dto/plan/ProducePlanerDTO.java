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

    public ProducePlanerDTO(final ProducePlaner entity) {
        this.producePlanerId = entity.getEmbeddedId().getProducePlanerId();
        this.bicycleId = entity.getEmbeddedId().getBicycleId();
        this.bicycleName = entity.getBicycle().getBicycleName();
        this.produceBicycleCnt = entity.getProduceBicycleCnt();
        this.materialId = entity.getEmbeddedId().getMaterialId();
        this.materialName = entity.getMaterial().getMaterialName();
        this.produceMaterialCnt = entity.getProduceMaterialCnt();
        this.producePlanerDeadline = entity.getProducePlanerDeadline();
        this.producePlanerStatus = entity.getProducePlanerStatus();
    }

    public static ProducePlaner toEntity(final ProducePlanerDTO dto) {
        try {
            ProducePlanerId producePlanerId = ProducePlanerId.builder()
                    .producePlanerId(dto.getProducePlanerId())
                    .bicycleId(dto.getBicycleId())
                    .materialId(dto.getMaterialId())
                    .build();

            if (producePlanerId == null) {
                throw new IllegalArgumentException("ProducePlanerId cannot be null");
            }

            return ProducePlaner.builder()
                    .embeddedId(producePlanerId)
                    .bicycleName(dto.getBicycleName())
                    .produceBicycleCnt(dto.getProduceBicycleCnt())
                    .materialName(dto.getMaterialName())
                    .produceMaterialCnt(dto.getProduceMaterialCnt())
                    .producePlanerDeadline(dto.getProducePlanerDeadline())
                    .producePlanerStatus(dto.getProducePlanerStatus())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 예외를 다시 던져서 상위 메서드에서 처리할 수 있도록 함
        }
    }
}
