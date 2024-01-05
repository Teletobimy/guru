package com.ezen.guru.controller.plan;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@RequiredArgsConstructor
@Controller
public class PlanController {


    @GetMapping("/plan/itemRegister")
    public String itemRegister(){
        return "plan/itemRegister";
    }

}
