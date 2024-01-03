package com.ezen.guru.controller.purchaseOrder;

import com.ezen.guru.dto.purchaseOrder.PcorderListViewResponse;
import com.ezen.guru.service.purchaseOrder.PcorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PcorderController {

    private final PcorderService pcorderService;

    @GetMapping("/pcorder_1")
    public String getPcorderList(Model model, int purchaseOrderStatus) {
        List<PcorderListViewResponse> pclist = pcorderService. findByEntityPurchaseOrderStatus(purchaseOrderStatus).stream()
                .map(PcorderListViewResponse::new)
                .toList();
        model.addAttribute("pclist", pclist);
        return "purchaseOrder/pcorder_1";
    }

    @GetMapping("/pcorder_2")
    public String pcorder_2() {
        return "purchaseOrder/pcorder_2";
    }

    @GetMapping("/pcorder_3")
    public String pcorder_3() {
        return "purchaseOrder/pcorder_3";
    }

    @GetMapping("/pcorder_4")
    public String pcorder_4() {
        return "purchaseOrder/pcorder_4";
    }
}
