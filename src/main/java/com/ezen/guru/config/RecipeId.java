package com.ezen.guru.config;


import com.ezen.guru.domain.Bicycle;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
@Embeddable
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RecipeId implements Serializable {


    @Column(name = "bicycle_id")
    @EqualsAndHashCode.Include
    private int bicycleId;

    @Column(name = "material_name")
    @EqualsAndHashCode.Include
    private String material_name;

}