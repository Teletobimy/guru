package com.ezen.guru.controller.plan;



import com.ezen.guru.domain.*;
import com.ezen.guru.dto.plan.*;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.DocumentService;
import com.ezen.guru.service.plan.MaterialService;
import com.ezen.guru.service.plan.QuotationService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@RequiredArgsConstructor
@Controller
@RequestMapping("/plan")
public class PlanController {

    private final BicycleService bicycleService;
    private final MaterialService materialService;
    private final DocumentService documentService;
    private final QuotationService quotationService;


    @GetMapping("/item")
    public String item(Model model,
                       @RequestParam(value="size", defaultValue = "10") int size,
                       @RequestParam(value="page", defaultValue = "0") int page,
                       @RequestParam(value = "bicycleName", required = false) String bicycleName){
        Page<BicycleDTO> bicyclePage = bicycleService.getAllBicycles(bicycleName, PageRequest.of(page, size));
        List<BicycleDTO> bicycles = bicyclePage.getContent();
        if(bicycleName==null||bicycleName==""||bicycleName.length()<1){bicycleName = "";}
        model.addAttribute("bicycleName",bicycleName);
        model.addAttribute("bicycles", bicycles);
        model.addAttribute("page", bicyclePage);

        return "plan/item";
    }

    @GetMapping("/material")
    public String material(Model model,
                           @RequestParam(value="size", defaultValue = "10") int size,
                           @RequestParam(value="page", defaultValue = "0") int page,
                           @RequestParam(value = "materialName", required = false) String materialName,
                           @RequestParam(value = "materialCategory", required = false) Integer  materialCategory){
        Page<MaterialDTO> materialPage = materialService.getAllMaterials(materialName, materialCategory, PageRequest.of(page, size));
        List<MaterialDTO> materials = materialPage.getContent();
        if(materialName==null||materialName==""||materialName.length()<1){materialName = "";}
        List<Code> codeList = materialService.findByCodeCategory("material_category");
        model.addAttribute("materialName", materialName);
        model.addAttribute("materials", materials);
        model.addAttribute("page", materialPage);
        model.addAttribute("code",codeList);
        System.out.println("codeList!!! : "+ codeList);

        return "plan/material";
    }

    @GetMapping("/item_search")
    public String item_search(Model model,
                              @RequestParam(value="size", defaultValue = "10") int size,
                              @RequestParam(value="page", defaultValue = "0") int page,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "category", defaultValue = "-1") int category){
        Page<MaterialDTO> materialPage = materialService.getAllMaterials(keyword, category, PageRequest.of(page, size));
        List<MaterialDTO> materials = materialPage.getContent();

        model.addAttribute("keyword", keyword);
        model.addAttribute("materials", materials);
        model.addAttribute("page", materialPage);
        return "plan/item_search";
    }

    @GetMapping("/material_search/{materialId}") // 실제 컨트롤러 엔드포인트 URL로 변경해주세요
    @ResponseBody
    public MaterialDTO getMaterialById(@PathVariable int materialId) {

        return materialService.getMaterialById(materialId);
    }



    @GetMapping("/quotation")
    public String quotation(Model model){
        System.out.println("견적서접속요청");
        return "plan/quotation";
    }

    @GetMapping("/procurementPlan")
    public String procurementPlan(Model model){
        List<DocumentDTO> documents = documentService.getAllProcurementPlan();
        model.addAttribute("documents", documents);

        return "plan/procurementPlan";
    }

    @PostMapping("/save_quotation")
    @ResponseBody
    public String saveQuotation(@RequestBody QuotationDTO quotationDTO) {
        String quotationId = LocalDateTime.now().toString() + "-" + new Random().nextInt(10000);
        quotationDTO.setId(quotationId);
        if(quotationDTO.getQuotationDetails() !=null){
            for(QuotationDetailDTO detailDTO : quotationDTO.getQuotationDetails()){
                detailDTO.setQuotation_id(quotationId);
            }
        }

       System.out.println(quotationDTO);
        return quotationService.saveQuotation(quotationDTO);
    }


    @GetMapping("/document")
    public String document(Model model){
        List<DocumentDTO> documents = documentService.getAllDocuments();
        model.addAttribute("documents", documents);
        return "plan/document";
    }

    @GetMapping("/document/{documentId}")
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

    @GetMapping("/document_insert")
    public String documentInsert(Model model){


        return "plan/document_insert";
    }
    @GetMapping("/company_search")
    public String company_search(Model model){



        return "plan/company_search";
    }

    @GetMapping("/searchCompany")
    public String searchCompany(@RequestParam("name") String companyName, Model model) {
        // 여기서 companyName을 사용하여 MySQL 데이터베이스에서 회사 정보를 검색
        // 예를 들어, CompanyService를 통해 데이터베이스에서 정보를 가져온다고 가정
       System.out.println(companyName);



            // 검색된 회사 정보를 모델에 추가하여 HTML로 반환
         //   model.addAttribute("company", company);
            return "plan/company_search"; // 회사 정보 템플릿 이름

    }

}
