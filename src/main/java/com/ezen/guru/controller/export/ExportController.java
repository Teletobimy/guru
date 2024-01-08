package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.ProducePlaner;
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

        List<ProducePlaner> producePlanerList = exportService.findByStatus(99);
        List<ProducePlanerDTO> producePlanerDTOList = producePlanerList.stream().map(ProducePlanerDTO::new).toList();
        List<Code> codeList = new ArrayList<>();

        for (ProducePlanerDTO planerDTO : producePlanerDTOList) {

            List<ProducePlaner> list = exportService.findByProducePlanerId(planerDTO.getProducePlanerId());
            List<ProducePlanerDTO> idList = list.stream().map(ProducePlanerDTO::new).toList();

            int cnt0 = 0;
            int cnt2 = 0;

            for (ProducePlanerDTO producePlanerDTO : idList) {

                int status = producePlanerDTO.getProducePlanerStatus();

                if (status == 0) {
                    cnt0++;
                } else if (status == 2) {
                    cnt2++;
                }
            }

            int size = idList.size();
            System.out.println("idList.size() : " + size + ", cnt0 : " + cnt0 + ", cnt2 : " + cnt2);
            Code code;

            if (size == cnt0) {
                code = exportService.findByCode("produce_planer_status", 0);
            } else if (size == cnt2) {
                code = exportService.findByCode("produce_planer_status", 2);
            } else {
                code = exportService.findByCode("produce_planer_status", 1);
            }
            System.out.println("code : " + code);
            codeList.add(code);
        }

        for (ProducePlanerDTO producePlanerDTO : producePlanerDTOList) {
            System.out.println("controller list id : " + producePlanerDTO.getProducePlanerId());
        }
        model.addAttribute("producePlanerList", producePlanerDTOList);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerList";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model) {

        List<ProducePlaner> producePlanerList = exportService.findByProducePlanerId(producePlanerId);
        List<ProducePlanerDTO> list = producePlanerList.stream().map(ProducePlanerDTO::new).toList();
        List<Code> codeList = new ArrayList<>();

        for (ProducePlanerDTO dto : list) {

            Code code = exportService.findByCode("produce_planer_status", dto.getProducePlanerStatus());
            codeList.add(code);
        }
        model.addAttribute("producePlanerList", list);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerDetail";
    }
}
