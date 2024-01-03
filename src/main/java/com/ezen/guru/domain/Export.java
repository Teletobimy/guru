package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Export {

    @Id
    @Column(name = "material_id", insertable = false, updatable = false)
    private int materialId;

    @Id
    @Column(name = "produce_planer_id", insertable = false, updatable = false)
    private String producePlanerId;

    @ManyToOne
    private Material material;

    @ManyToOne
    private ProducePlaner producePlaner;

    @Column(name = "export_cnt")
    private int exportCnt;

    @Column(name = "export_date")
    private LocalDateTime exportDate;

    public Export(Material material, ProducePlaner producePlaner, int exportCnt, LocalDateTime createdAt) {
        this.material = material;
        this.producePlaner = producePlaner;
        this.exportCnt = exportCnt;
        this.exportDate = createdAt;
    }

    public void update(Material material, ProducePlaner producePlaner, int exportCnt, LocalDateTime updatedAt) {
        this.material = material;
        this.producePlaner = producePlaner;
        this.exportCnt = exportCnt;
        this.exportDate = updatedAt;
    }

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
