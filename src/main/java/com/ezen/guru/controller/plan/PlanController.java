package com.ezen.guru.controller.plan;



import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class PlanController {

    private final BicycleService bicycleService;
    private final MaterialService materialService;


    @GetMapping("/plan/item")
    public String item(Model model){
        List<BicycleDTO> bicycles = bicycleService.getAllBicycles();
        model.addAttribute("bicycles", bicycles);

        return "plan/item";
    }

    @GetMapping("/plan/material")
    public String material(Model model){
        List<MaterialDTO> materials = materialService.getAllMaterials();
        model.addAttribute("materials", materials);

        return "plan/material";
    }

}
