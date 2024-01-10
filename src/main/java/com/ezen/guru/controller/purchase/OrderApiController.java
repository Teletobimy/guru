package com.ezen.guru.controller.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.UpdateOrderCheckRequest;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class OrderApiController {

    private final OrderService orderService;

    @PutMapping("/api/order_detail/{id}")
    public ResponseEntity<PurchaseOrderDetail> updateOrderCheck(@PathVariable int detailId, @RequestBody UpdateOrderCheckRequest request) {
        PurchaseOrderDetail updatedOrder = orderService.update(detailId, request);
        return ResponseEntity.ok()
                .body(updatedOrder);
    }
}
