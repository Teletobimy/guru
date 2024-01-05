package com.ezen.guru.repository.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

//    public OrderCustomRepositoryImpl() {
//        super(OrderCustomRepositoryImpl.class);
//    }
}
