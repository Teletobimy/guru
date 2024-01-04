package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_id", updatable = false)
    private String id;

    @Column(name="document_type")
    private int type;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name="totalprice")
    private int totalprice;

    @Column(name="regdate")
    private LocalDateTime regdate;

    @Column(name="deadline")
    private LocalDateTime deadline;

    @Column(name="document_status")
    private int status;

    @Column(name="document_memo")
    private String memo;
}
