package com.ezen.guru.controller.purchase;

import com.ezen.guru.dto.purchase.AddCompanyRequest;
import com.ezen.guru.dto.purchase.UpdateCompanyRequest;
import com.ezen.guru.service.purchase.CompanyService;
import com.ezen.guru.service.purchase.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase/api")
public class OrderApiController {

    private final OrderService orderService;
    private final CompanyService companyService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_B')")
    @PostMapping("orders/{orderId}/update-status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam(name = "newStatus") int newStatus) {
        orderService.updateOrderDetailStatus(orderId, newStatus);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_B')")
    @PutMapping("/order/{id}/update-status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String id) {
        orderService.updateOrderStatus(id);
        return ResponseEntity.ok("발주 상태가 업데이트되었습니다.");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_B')")
    @PutMapping("/order/{id}/force-close")
    public ResponseEntity<String> forceOrderClose(@PathVariable String id) {
        try {
            orderService.forceClose(id);
            return ResponseEntity.ok("발주 마감 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("발주 마감 중 오류 발생");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_B')")
    @PutMapping("/company/{companyId}/update")
    public ResponseEntity<?> updateCompany(@PathVariable String companyId, @Valid @RequestBody UpdateCompanyRequest company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 검증 오류가 발생한 경우
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        companyService.updateCompany(companyId, company);
        return ResponseEntity.ok("협력사 정보가 수정되었습니다.");
    }
}
