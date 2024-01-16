package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProducePlaner {

    @EmbeddedId
    private ProducePlanerId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bicycle_id", referencedColumnName = "bicycle_id", insertable = false, updatable = false),
            @JoinColumn(name = "bicycle_name", referencedColumnName = "bicycle_name", insertable = false, updatable = false)
    })
    private Bicycle bicycle;

    @Column(name = "produce_bicycle_cnt")
    private int produceBicycleCnt;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "material_id", referencedColumnName = "material_id", insertable = false, updatable = false),
            @JoinColumn(name = "material_name", referencedColumnName = "material_name", insertable = false, updatable = false)
    })
    private Material material;

    @Column(name = "produce_material_cnt")
    private int produceMaterialCnt;

    @Column(name = "produce_planer_deadline")
    private LocalDateTime producePlanerDeadline;

    @Column(name = "produce_planer_status")
    private int producePlanerStatus;

    @Builder
    public ProducePlaner(ProducePlanerId id, Bicycle bicycle, int produceBicycleCnt, Material material, int produceMaterialCnt, LocalDateTime producePlanerDeadline, int producePlanerStatus) {
        this.id = id;
        this.bicycle = bicycle;
        this.produceBicycleCnt = produceBicycleCnt;
        this.material = material;
        this.produceMaterialCnt = produceMaterialCnt;
        this.producePlanerDeadline = producePlanerDeadline;
        this.producePlanerStatus = producePlanerStatus;
    }

    public void update(Bicycle bicycle, int produceBicycleCnt, Material material, int produceMaterialCnt, LocalDateTime producePlanerDeadline, int producePlanerStatus) {
        this.bicycle = bicycle;
        this.produceBicycleCnt = produceBicycleCnt;
        this.material = material;
        this.produceMaterialCnt = produceMaterialCnt;
        this.producePlanerDeadline = producePlanerDeadline;
        this.producePlanerStatus = producePlanerStatus;
    }
}
