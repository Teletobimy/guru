package com.ezen.guru.dto.plan;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BicycleDTO {

    private int bicycleId;
    private String bicycleName;
    private String bicycleDescription;
    private int bicycleStock;
    private int bicyclePrice;
}
