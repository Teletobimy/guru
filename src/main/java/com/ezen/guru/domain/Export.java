package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Export {

    @ManyToOne
    @JoinColumn(name = "materialId", referencedColumnName = "materialId")
//    private Material material;

    @Column(name = "materialId", insertable = false, updatable = false)
    private int materialId;
}
