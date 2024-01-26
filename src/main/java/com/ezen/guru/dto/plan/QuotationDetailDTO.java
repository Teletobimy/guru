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


}