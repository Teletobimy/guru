package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "code")
@IdClass(CodeId.class)
public class Code {

    @Id
    @Column(name = "code_category")
    private String codeCategory;

    @Id
    @Column(name = "code_num")
    private int codeNum;

    @Column(name = "code_name")
    private String codeName;

    @Builder
    public Code(String codeCategory, int codeNum, String codeName){
        this.codeCategory = codeCategory;
        this.codeNum = codeNum;
        this.codeName = codeName;
    }
}
