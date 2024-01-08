package com.ezen.guru.repository.receive;

import com.ezen.guru.dto.receive.ShipmentResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    public void testShipmentList() {
        // Given (you may need to set up test data in your database)

        // When
        Page<ShipmentResponse> shipmentList = shipmentRepository.shipmentList(0,10,"",1);

        // Then
        assertNotNull(shipmentList);
        // Add more assertions based on your specific test scenario
    }
}
