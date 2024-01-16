package com.ezen.guru.controller.purchase;

import com.ezen.guru.dto.purchase.OrderCompleteRequest;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase/api")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("orders/{orderId}/update-status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam int newStatus) {
        orderService.updateOrderDetailStatus(orderId, newStatus);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderCompleteRequest request) {
        orderService.updateOrderStatus(request);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }
}
