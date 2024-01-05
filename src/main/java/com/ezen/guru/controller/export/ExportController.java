package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model) {

        List<ProducePlanerDTO> producePlanerList = exportService.findProducePlanerList(99);
        List<ProducePlanerDTO> producePlanerIdList;
        List<Code> codeList = new ArrayList<>();

        for (int i = 0; i < producePlanerList.size(); i++) {

            producePlanerIdList = new ArrayList<>();
            producePlanerIdList = exportService.findByProducePlanerId(producePlanerList.get(i).getProducePlanerId());
            int cnt0 = 0;
            int cnt99 = 0;

            for (int j = 0; j < producePlanerIdList.size(); j++) {

                int status = producePlanerIdList.get(j).getProducePlanerStatus();

                if (status == 0) {
                    cnt0++;
                } else if (status == 99) {
                    cnt99++;
                }
            }

            int size = producePlanerIdList.size();
            Code code = new Code();

            if (size == cnt0) {
                code = exportService.findByCode("producePlanerStatus", 0);
            } else if (size == cnt99) {
                code = exportService.findByCode("producePlanerStatus", 99);
            } else {
                code = exportService.findByCode("producePlanerStatus", 1);
            }
            System.out.println(code);
            codeList.add(code);
        }

        for (ProducePlanerDTO producePlanerDTO : producePlanerList) {
            System.out.println("controller list id : " + producePlanerDTO.getProducePlanerId());
        }
        model.addAttribute("producePlanerList", producePlanerList);
        model.addAttribute("codeList", codeList);

        return "/export/producePlanerList";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model) {

        List<ProducePlanerDTO> producePlanerList = exportService.findByProducePlanerId(producePlanerId);
        List<Code> statusList = exportService.findByCodeList("producePlanerStatus");

        for (ProducePlanerDTO producePlanerDTO : producePlanerList) {
            System.out.println("controller list id : " + producePlanerDTO.getProducePlanerId());
        }
        model.addAttribute("producePlanerList", producePlanerList);
        model.addAttribute("statusList", statusList);

        return "/export/producePlanerDetail";
    }
}
