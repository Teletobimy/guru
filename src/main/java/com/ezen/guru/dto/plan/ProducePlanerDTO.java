package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.ProducePlaner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
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

    public ProducePlanerDTO (ProducePlaner producePlaner) {
        this.producePlanerId = producePlaner.getId().getProducePlanerId();
        this.bicycleId = producePlaner.getId().getBicycleId();
        this.bicycleName = producePlaner.getBicycle().getBicycleName();
        this.produceBicycleCnt = producePlaner.getProduceBicycleCnt();
        this.materialId = producePlaner.getId().getMaterialId();
        this.materialName = producePlaner.getMaterial().getMaterialName();
        this.produceMaterialCnt = producePlaner.getProduceMaterialCnt();
        this.producePlanerDeadline = producePlaner.getProducePlanerDeadline();
        this.producePlanerStatus = producePlaner.getProducePlanerStatus();
    }
}
