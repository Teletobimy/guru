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
    private int materialId;
    private int producePlanerCnt;
    private LocalDateTime producePlanerDeadline;
    private int producePlanerStatus;

    public ProducePlanerDTO (ProducePlaner producePlaner) {
        this.producePlanerId = producePlaner.getId().getProducePlanerId();
        this.bicycleId = producePlaner.getId().getBicycleId();
        this.materialId = producePlaner.getId().getMaterialId();
        this.producePlanerCnt = producePlaner.getProducePlanerCnt();
        this.producePlanerDeadline = producePlaner.getProducePlanerDeadline();
        this.producePlanerStatus = producePlaner.getProducePlanerStatus();
    }
}
