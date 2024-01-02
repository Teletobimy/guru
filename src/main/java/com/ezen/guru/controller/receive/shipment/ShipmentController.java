package com.ezen.guru.controller.receive.shipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    @GetMapping("/shipmentList")
    public String shipmentList(){
        return "/shipment/shipmentList";
    }
}
