package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Company {

    @Id
    @Column(name="company_id", updatable = false)
    private String companyId;

    @Column(name="material_id")
    private int materialId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @Column(name="material_id")
//    private Material material;

    @Column(name="company_name")
    private String companyName;

    @Column(name="ceo")
    private String ceo;

    @Column(name="tel")
    private String tel;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;

    @Builder
    public Company(String companyId,
                   int materialId,
                   String companyName,
                   String ceo,
                   String tel,
                   String email,
                   String address) {
        this.companyId = companyId;
        this.materialId = materialId;
        this.companyName = companyName;
        this.ceo = ceo;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }
}
