package com.ezen.guru.controller.purchase;

import com.ezen.guru.dto.purchase.AddCompanyRequest;
import com.ezen.guru.dto.purchase.UpdateCompanyRequest;
import com.ezen.guru.service.purchase.CompanyService;
import com.ezen.guru.service.purchase.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase/api")
public class OrderApiController {

    private final OrderService orderService;
    private final CompanyService companyService;

    @PostMapping("orders/{orderId}/update-status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam int newStatus) {
        orderService.updateOrderDetailStatus(orderId, newStatus);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }

    @PutMapping("/order/{id}/update-status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String id) {
        orderService.updateOrderStatus(id);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }

    @PutMapping("/order/{id}/force-close")
    public ResponseEntity<String> forceOrderClose(@PathVariable String id) {
        try {
            orderService.forceClose(id);
            return ResponseEntity.ok("발주 마감 검사 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("발주 마감 검사 중 오류 발생");
        }
    }

    @PutMapping("/company/{companyId}/update")
    public ResponseEntity<String> updateCompany(@PathVariable String companyId, @Valid @RequestBody UpdateCompanyRequest company) {
        companyService.updateCompany(companyId, company);
        return ResponseEntity.ok("협력사 정보가 수정되었습니다.");
    }
}
