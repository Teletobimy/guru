package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Export {

    @EmbeddedId
    private ProducePlanerId embeddedId;  // 외래 키로 사용할 복합 기본 키

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "produce_planer_id", referencedColumnName = "produce_planer_id", insertable = false, updatable = false),
            @JoinColumn(name = "bicycle_id", referencedColumnName = "bicycle_id", insertable = false, updatable = false),
            @JoinColumn(name = "material_id", referencedColumnName = "material_id", insertable = false, updatable = false)
    })
    private ProducePlaner producePlaner;  // 외래 키에 대한 참조

    @Column(name = "bicycle_name")
    private String bicycleName;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "export_cnt")
    private int exportCnt;

    @Column(name = "export_date")
    private LocalDateTime exportDate;

    @Builder
    public Export(ProducePlanerId embeddedId, String bicycleName, String materialName, int exportCnt, LocalDateTime exportDate) {
        this.embeddedId = embeddedId;
        this.bicycleName = bicycleName;
        this.materialName = materialName;
        this.exportCnt = exportCnt;
        this.exportDate = exportDate;
    }

    public void update(ProducePlanerId embeddedId, String bicycleName, String materialName, int exportCnt, LocalDateTime exportDate) {
        this.embeddedId = embeddedId;
        this.bicycleName = bicycleName;
        this.materialName = materialName;
        this.exportCnt = exportCnt;
        this.exportDate = exportDate;
    }
}
