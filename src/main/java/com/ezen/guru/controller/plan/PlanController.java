package com.ezen.guru.controller.plan;



import com.ezen.guru.domain.Document;
import com.ezen.guru.domain.DocumentDetail;
import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.dto.plan.DocumentDTO;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.DocumentService;
import com.ezen.guru.service.plan.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class PlanController {

    private final BicycleService bicycleService;
    private final MaterialService materialService;
    private final DocumentService documentService;


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


    @GetMapping("/plan/document")
    public String document(Model model){
        List<DocumentDTO> documents = documentService.getAllDocuments();
        model.addAttribute("documents", documents);

        return "plan/document";
    }

    @GetMapping("/plan/document/{documentId}")
    public String getDocumentWithDetails(
            @PathVariable("documentId")String documentId,
            Model model
    ){
        DocumentDTO documents = documentService.findDocumentById(documentId);
        List<DocumentDetail> documentDetails = documentService.findDocumentDetailsByDocumentId(documentId);

        model.addAttribute("documents", documents);
        model.addAttribute("documentDetails", documentDetails);
        return "plan/document_detail_list";
    }

    @GetMapping("/plan/document_insert")
    public String documentInsert(Model model){


        return "plan/document_insert";
    }

}
