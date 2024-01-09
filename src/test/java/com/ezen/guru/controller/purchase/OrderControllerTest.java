package com.ezen.guru.controller.purchase;

import com.ezen.guru.service.purchase.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderServiceImpl pcorderService;

    /*@Test
    public void getPcorderListTest() throws Exception {
        // 테스트에 사용할 가상의 데이터 생성
        List<BeforeOrderListViewResponse> mockPclist = List.of(
                new BeforeOrderListViewResponse("Item1"),
                new BeforeOrderListViewResponse("Item2")
                // ... 추가 아이템
        );

        // PcorderService의 getProductOrderDetailsByPurchaseOrderStatus() 메서드가 호출될 때 mockPclist를 반환하도록 설정
        when(pcorderService.getProductOrderDetailsByPurchaseOrderStatus()).thenReturn(mockPclist);

        // MockMvc를 사용하여 컨트롤러 메서드 호출 및 결과 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/purchase/order_1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("purchase/order_1"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pclist"))
                .andExpect(MockMvcResultMatchers.model().attribute("pclist", mockPclist));

        // PcorderService의 getProductOrderDetailsByPurchaseOrderStatus() 메서드가 한 번 호출되었는지 검증
        verify(pcorderService, times(1)).getProductOrderDetailsByPurchaseOrderStatus();
    }*/
}