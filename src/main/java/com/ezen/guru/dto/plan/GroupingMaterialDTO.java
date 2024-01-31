package com.ezen.guru.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
    private String materialMeasure;
    private Long totalStock;

    // 변환 메서드
    public static Page<GroupingMaterialDTO> toDTOPage(Page<Object[]> page) {
        List<GroupingMaterialDTO> dtoList = page.getContent().stream()
                .map(row -> new GroupingMaterialDTO((String) row[0], (String) row[1], (int) row[2], (String) row[3], (Long) row[4]))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}
