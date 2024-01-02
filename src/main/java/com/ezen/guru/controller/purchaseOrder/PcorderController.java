package com.ezen.guru.controller.purchaseOrder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class PcorderController {
    @GetMapping("/pcorder_1")
    public String pcorder_1() {
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
