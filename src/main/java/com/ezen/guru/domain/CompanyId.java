package com.ezen.guru.domain;

import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CompanyId implements Serializable {
    private String companyId;
    private int materialId;
}
