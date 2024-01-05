package com.ezen.guru.controller.export;

import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model) {
        List<ProducePlanerDTO> producePlanerList = exportService.findProducePlanerList(0);
        model.addAttribute("producePlanerList", producePlanerList);
        return "export/producePlanerList";
    }
}
