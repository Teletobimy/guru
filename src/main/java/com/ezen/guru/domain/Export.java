package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Export {

    @Id
    @Column(name = "material_id", insertable = false, updatable = false)
    private int materialId;

    @Id
    @Column(name = "produce_planer_id", insertable = false, updatable = false)
    private Long producePlanerId;

    @ManyToOne
    private Material material;

    @ManyToOne
    private ProducePlaner producePlaner;
}
