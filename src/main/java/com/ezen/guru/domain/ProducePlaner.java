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

    @Id
    private String producePlanerId;

//    @Id
//    @Column(name = "bicycle_id", insertable = false, updatable = false)
//    private int bicycleId;

    @ManyToOne
    @JoinColumn(name="bicycle_id")
    private Bicycle bicycle;

//    @Id
//    @Column(name = "material_id", insertable = false, updatable = false)
//    private int materialId;

    @ManyToOne
    @JoinColumn(name="material_id")
    private Material material;

    @Column(name = "produce_planer_cnt")
    private int producePlanerCnt;

    @Column(name = "produce_planer_deadline")
    private LocalDateTime producePlanerDeadline;

    @Column(name = "produce_planer_status")
    private int producePlanerStatus;

    public ProducePlaner(Bicycle bicycle, Material material, int producePlanerCnt, LocalDateTime createdAt, int producePlanerStatus) {
        this.bicycle = bicycle;
        this.material = material;
        this.producePlanerCnt = producePlanerCnt;
        this.producePlanerDeadline = createdAt;
        this.producePlanerStatus = producePlanerStatus;
    }

    public void update(Bicycle bicycle, Material material, int producePlanerCnt, LocalDateTime updatedAt, int producePlanerStatus) {
        this.bicycle = bicycle;
        this.material = material;
        this.producePlanerCnt = producePlanerCnt;
        this.producePlanerDeadline = updatedAt;
        this.producePlanerStatus = producePlanerStatus;
    }

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
