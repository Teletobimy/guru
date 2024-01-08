package com.ezen.guru.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducePlanerId implements Serializable {

    @Column(name = "produce_planer_id")
    private String producePlanerId;

    @Column(name = "bicycle_id")
    private int bicycleId;

    @Column(name = "material_id")
    private int materialId;
}
