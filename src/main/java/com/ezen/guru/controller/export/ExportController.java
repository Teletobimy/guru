package com.ezen.guru.controller.export;

import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model) {
        List<ProducePlanerDTO> producePlanerList = exportService.findProducePlanerList(0);
        model.addAttribute("producePlanerList", producePlanerList);
        return "redirect:/export/producePlanerList";
    }
}
