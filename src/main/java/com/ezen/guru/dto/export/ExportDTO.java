package com.ezen.guru.dto.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExportDTO {

    private String producePlanerId;  // 복합 기본 키의 일부분
    private int bicycleId;  // 복합 기본 키의 일부분
    private int materialId;  // 복합 기본 키의 일부분
    private int exportCnt;
    private LocalDateTime exportDate;
}
