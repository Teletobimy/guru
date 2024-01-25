package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "bicycle")
@NoArgsConstructor
@Getter
@Entity
@Setter
@ToString

public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bicycle_id", updatable = false)
    private int bicycleId;

    @Column(name="bicycle_name")
    private String bicycleName;

    @Column(name="bicycle_description")
    private String bicycleDescription;

    @Column(name="bicycle_stock")
    private int bicycleStock;

    @Column(name="bicycle_price")
    private int bicyclePrice;

    @Builder
    public Bicycle(int bicycleId, String bicycleName, String bicycleDescription, int bicycleStock, int bicyclePrice) {
        Recipe newRecipe = new Recipe();
        this.bicycleId = bicycleId;
        this.bicycleName = bicycleName;
        this.bicycleDescription = bicycleDescription;
        this.bicycleStock = bicycleStock;
        this.bicyclePrice = bicyclePrice;
    }

}
