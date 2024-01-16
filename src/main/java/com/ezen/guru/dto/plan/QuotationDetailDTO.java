package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.Material;
import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuotationDetailDTO {

    private int id;
    private String quotation_id;
    private int material_id;
    private String materialName;
    private Integer quotationCnt;
    private String quotationMeasure;
    private Integer quotationPrice;


    public QuotationDetail toEntity() {
        return QuotationDetail.builder()
                .id(this.id)
                .quotation(Quotation.builder().id(this.quotation_id).build()) // 예시로 Quotation을 만들어서 설정
                .material_id(this.material_id)
                .materialName(this.materialName)
                .quotationCnt(this.quotationCnt)
                .quotationMeasure(this.quotationMeasure)
                .quotationPrice(this.quotationPrice)
                .build();
    }
}