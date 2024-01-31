package com.ezen.guru.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupingMaterialDTO {

    private String materialName;
    private String materialDescription;
    private int materialCategory;
    private int totalStock;

    // 변환 메서드
    public static Page<GroupingMaterialDTO> toDTOList(Page<Object[]> resultList) {
        return (Page<GroupingMaterialDTO>) resultList.stream()
                .map(row -> new GroupingMaterialDTO((String) row[0], (String) row[1], (int) row[2], (int) row[3]))
                .collect(Collectors.toList());
    }
}
