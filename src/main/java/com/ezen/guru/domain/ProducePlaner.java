package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ProducePlaner {

    @EmbeddedId
    private ProducePlanerId id;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "bicycle_id", insertable = false, updatable = false)
    private Bicycle bicycle;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "material_id", insertable = false, updatable = false)
    private Material material;

    @Column(name = "produce_planer_cnt")
    private int producePlanerCnt;

    @Column(name = "produce_planer_deadline")
    private LocalDateTime producePlanerDeadline;

    @Column(name = "produce_planer_status")
    private int producePlanerStatus;

    @Builder
    public ProducePlaner(ProducePlanerId id, Bicycle bicycle, Material material, int producePlanerCnt, LocalDateTime producePlanerDeadline, int producePlanerStatus) {
        this.id = id;
        this.bicycle = bicycle;
        this.material = material;
        this.producePlanerCnt = producePlanerCnt;
        this.producePlanerDeadline = producePlanerDeadline;
        this.producePlanerStatus = producePlanerStatus;
    }

    public void update(Bicycle bicycle, Material material, int producePlanerCnt, LocalDateTime producePlanerDeadline, int producePlanerStatus) {
        this.bicycle = bicycle;
        this.material = material;
        this.producePlanerCnt = producePlanerCnt;
        this.producePlanerDeadline = producePlanerDeadline;
        this.producePlanerStatus = producePlanerStatus;
    }
}
