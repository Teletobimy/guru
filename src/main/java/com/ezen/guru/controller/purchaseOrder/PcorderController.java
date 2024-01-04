package com.ezen.guru.controller.purchaseOrder;

import com.ezen.guru.dto.purchaseOrder.PcorderListViewResponse;
import com.ezen.guru.service.purchaseOrder.PcorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/purchase")
@Controller
public class PcorderController {

    private final PcorderService pcorderService;

//    미발주 계약 및 조달 건들을 목록으로 보여주고
//    상세 버튼을 누르면 상세 내역(trade.html 형태) 열람하는 페이지로 이동
//    그 후 발주 버튼을 누르면 상세 내역과 비교하며 입력할 수 있는 폼이 있는 모달 창
//    입력 후 완료 버튼 누르면 유효성 검사 실행, 성공하면 발주 상태로 바뀜
    @GetMapping("/order_1")
    public String getPcorderList(Model model) {

        List<PcorderListViewResponse> pclist = pcorderService.getProductOrderDetailsByPurchaseOrderStatus().stream()
                .map(PcorderListViewResponse::new)
                .toList();
        model.addAttribute("pclist", pclist);
        return "purchase/order_1";
    }

    @GetMapping("/order_2")
    public String pcorder_2() {
        return "order_2";
    }

    @GetMapping("/order_3")
    public String pcorder_3() {
        return "order_3";
    }

    @GetMapping("/order_4")
    public String pcorder_4() {
        return "order_4";
    }
}
