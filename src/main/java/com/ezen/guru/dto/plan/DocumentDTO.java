package com.ezen.guru.dto.plan;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DocumentDTO {
    private String id;
    private int type;
    private String companyId;
    private int totalprice;
    private LocalDateTime regdate;
    private LocalDateTime deadline;
    private int status;
    private String memo;

}


