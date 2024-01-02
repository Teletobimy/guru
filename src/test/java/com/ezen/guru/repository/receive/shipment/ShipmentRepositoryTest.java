package com.ezen.guru.repository.receive.shipment;

import com.ezen.guru.domain.receive.shipment.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShipmentRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    @Transactional
    @DisplayName("QueryDsl 테스트다")
    public void testShipmentList(){
        //given
        Shipment shipment = Shipment.builder()
                .materialId(1)
                .materialName("강철손잡이(sus313)")
                .shipmentCnt(1)
                .materialPrice(4000)
                .materialMeasure("1")
                .materialCategory(1)
                .shippingDate(LocalDateTime.now())
                .companyId("123-45-67891")
                .build();
        em.persist(shipment);
        em.flush();
        em.clear();
        // when
        List<Shipment> shipmentList = shipmentRepository.shipmentList(shipment);
        //then
        assertNotNull(shipmentList);
        assertTrue(shipmentList.size() == 2);

        Shipment retrievedShipment = shipmentList.get(0);
        assertEquals(shipment.getMaterialId(), retrievedShipment.getMaterialId());
    }


}