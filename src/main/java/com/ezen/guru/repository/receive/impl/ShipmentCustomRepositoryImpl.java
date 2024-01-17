package com.ezen.guru.repository.receive.impl;

import com.ezen.guru.domain.QCompany;
import com.ezen.guru.domain.QShipment;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.repository.receive.ShipmentCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ShipmentCustomRepositoryImpl implements ShipmentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShipmentResponse> shipmentList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate) {

        QShipment qShipment = QShipment.shipment;
        QCompany qCompany = QCompany.company;

        BooleanBuilder whereCondition = new BooleanBuilder();

        if(keyword != null && !keyword.isEmpty()){
            whereCondition.and(qShipment.materialId.materialName.like("%" + keyword + "%"));
        }
        if(category != -1){
            whereCondition.and(qShipment.materialCategory.eq(category));
        }
        if(startDate != null && endDate != null){
            whereCondition.and(qShipment.shippingDate.between(startDate, endDate));
        }

       QueryResults<ShipmentResponse> results = jpaQueryFactory
                .selectDistinct(Projections.constructor(
                        ShipmentResponse.class,
                        qShipment.shipmentId,
                        qShipment.materialId.materialId,
                        qShipment.materialId.materialName,
                        qShipment.shipmentCnt,
                        qShipment.materialMeasure,
                        qShipment.materialPrice,
                        qShipment.materialCategory,
                        qShipment.companyId.companyName,
                        qShipment.shippingDate,
                        qShipment.purchaseOrderId
                ))
                .from(qShipment)
                .leftJoin(qCompany)
                .on(qShipment.materialId.materialId.eq(qCompany.materialId))
               .where(whereCondition)
               .offset(size * page)
               .limit(size)
               .fetchResults();
       // 결과를 Page 객체로 변환
        List<ShipmentResponse> content = results.getResults();
        long total = results.getTotal();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new PageImpl<>(content,pageRequest,total);

    }
}
