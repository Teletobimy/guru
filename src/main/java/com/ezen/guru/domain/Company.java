package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "company")
@NoArgsConstructor
@Getter
@Entity
public class Company {

    @Id
    @Column(name="company_id", updatable = false)
    private String companyId;

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
                   String companyName,
                   String ceo,
                   String tel,
                   String email,
                   String address) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.ceo = ceo;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }

}
