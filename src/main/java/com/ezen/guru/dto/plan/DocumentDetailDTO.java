package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.Document;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DocumentDetailDTO {
    private int id;
    private Document document;
    private int materialId;
    private String materialName;
    private int documentCnt;
    private String documentMeasure;
    private int documentPrice;


}


