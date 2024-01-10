package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model) {

        List<ProducePlanerDTO> producePlanerDTOList = exportService.findByStatus(99);
        List<Code> codeList = exportService.setCodeListByProducePlanerStatus(producePlanerDTOList);

        for (ProducePlanerDTO producePlanerDTO : producePlanerDTOList) {
            System.out.println("controller list id : " + producePlanerDTO.getProducePlanerId());
        }
        model.addAttribute("producePlanerList", producePlanerDTOList);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerList";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model) {

        List<ProducePlanerDTO> list = exportService.findByProducePlanerId(producePlanerId);
        List<Code> codeList = exportService.findByCodeList(list);

        model.addAttribute("producePlanerList", list);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerDetail";
    }

    @PostMapping("/producePlanerDetail")
    public ResponseEntity<?> producePlanerDetail(@RequestBody ExportDTO request) {

        try {
            exportService.addExport(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cancelExport")
    public ResponseEntity<?> cancelExport(@RequestBody ExportDTO request) {

        try {
            exportService.deleteExport(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/listExport")
//    public ResponseEntity<?> listExport(@RequestBody )
}
