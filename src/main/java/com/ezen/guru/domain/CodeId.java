package com.ezen.guru.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeId implements Serializable {
    private String codeCategory;
    private int codeNum;
}
