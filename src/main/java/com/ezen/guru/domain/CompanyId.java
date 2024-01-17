package com.ezen.guru.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyId implements Serializable {
    private String companyId;
    private int materialId;
}
