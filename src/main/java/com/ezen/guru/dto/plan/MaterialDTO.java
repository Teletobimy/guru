package com.ezen.guru.dto.plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MaterialDTO {
    private int materialId;
    private String materialCode;
    private String companyId;
    private String materialName;
    private String materialDescription;
    private int materialStock;
    private int materialPrice;
    private String materialMeasure;
    private int materialCategory;
}
