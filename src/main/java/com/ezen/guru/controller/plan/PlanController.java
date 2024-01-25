package com.ezen.guru.controller.plan;



import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.*;
import com.ezen.guru.dto.plan.*;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.service.plan.*;
import com.ezen.guru.service.purchase.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;


@RequiredArgsConstructor
@Controller
@RequestMapping("/plan")
public class PlanController {

    private final BicycleService bicycleService;
    private final MaterialService materialService;
    private final DocumentService documentService;
    private final QuotationService quotationService;
    private final CompanyService companyService;
    private final RecipeService recipeService;


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

    @GetMapping("/bicycle_detail")
    public String bicycle_detail(Model model,
                                 @RequestParam(value = "bicycleId") Integer bicycleId
    ){

        Bicycle savedBicycle = bicycleService.getBicycleById(bicycleId);
        BicycleDTO bicycleDTO = bicycleService.convertToDTO(bicycleService.getBicycleById(bicycleId));
        List<Recipe> recipe = recipeService.findByBicycle(savedBicycle);

        model.addAttribute("bicycle", savedBicycle);
        model.addAttribute("recipes", recipe);
        return "plan/bicycle_detail";
    }

    @GetMapping("/bicycle_insert")
    public String bicycle_insert(Model model){
        System.out.println("아이템생성");

        return "plan/bicycle_insert";
    }
    @PostMapping("/bicycle_save")
    public String bicycle_save(@RequestBody BicycleDTO newBicycle) {
       Bicycle savedBicycle = bicycleService.saveBicycle(newBicycle);
        List<RecipeDTO> recipeList = newBicycle.getRecipes();
        if (recipeList != null) {
            for (RecipeDTO recipeDTO : recipeList) {
                RecipeId recipeId = new RecipeId();
                recipeId.setBicycleId(savedBicycle.getBicycleId());
                recipeId.setMaterial_name(recipeDTO.getMaterialName());
                Recipe newRecipe = Recipe.builder()
                        .id(recipeId)
                        .bicycle(savedBicycle)
                        .recipeCnt(recipeDTO.getRecipeCnt())
                        .build();
                recipeService.saveRecipe(newRecipe);
            }
        }


//         저장 후에는 다시 목록 화면으로 리다이렉트 또는 다른 작업 수행 가능
        return "redirect:/plan/item";
    }

    @PostMapping("/bicycle_delete")
    @ResponseBody
    public ResponseEntity<String> bicycle_delete(@RequestBody Integer bicycleId){

        try {
            List<Recipe> recipe = recipeService.findByBicycle(bicycleService.getBicycleById(bicycleId));
            if (recipe.isEmpty()) {
                bicycleService.deleteBicycle(bicycleId);
                return ResponseEntity.ok("삭제완료");

            } else {
                recipeService.deleteByBicycleId(bicycleId);
                bicycleService.deleteBicycle(bicycleId);
                return ResponseEntity.ok("삭제완료");

            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }

    }


    @PostMapping("/recipe_add")
    @ResponseBody
    public ResponseEntity<String> recipe_add (
            @RequestParam String previousCellValue,
            @RequestParam Integer bicycleIdValue
    ){

        RecipeId recipeId = new RecipeId(bicycleIdValue, previousCellValue);

        Recipe recipe =  new Recipe(recipeId,bicycleService.getBicycleById(bicycleIdValue),0);
        try {
            recipeService.saveRecipe(recipe);

            return ResponseEntity.ok("추가완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추가실패");
        }

    }

    @PostMapping("/recipe_update")
    @ResponseBody
    public ResponseEntity<String> recipe_update(
            @RequestParam String previousCellValue,
            @RequestParam Integer previousInputValue,
            @RequestParam Integer bicycleIdValue
    ){
        System.out.println("previousCellValue : "+previousCellValue);
        System.out.println("previousInputValue :"+previousInputValue);
        System.out.println("bicycleIdValue :"+bicycleIdValue);

        RecipeId recipeId = new RecipeId(bicycleIdValue, previousCellValue);

        Recipe recipe =  new Recipe(recipeId,bicycleService.getBicycleById(bicycleIdValue),previousInputValue);

        try {
            recipeService.saveRecipe(recipe);

            return ResponseEntity.ok("수정완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정실패");
        }
    }


    @PostMapping("/recipe_delete")
    @ResponseBody
    public ResponseEntity<String> recipe_delete (
            @RequestParam String previousCellValue,
            @RequestParam Integer bicycleIdValue
    ){
        System.out.println("previousCellValue : "+previousCellValue);
        System.out.println("bicycleIdValue :"+bicycleIdValue);
        try {
            recipeService.deleteRecipe(bicycleIdValue, previousCellValue);
            return ResponseEntity.ok("삭제완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }
    }




    @GetMapping("/material")
    public String material(Model model,
                           @RequestParam(value="size", defaultValue = "10") int size,
                           @RequestParam(value="page", defaultValue = "0") int page,
                           @RequestParam(value = "materialName", required = false) String materialName,
                           @RequestParam(value = "materialCategory", defaultValue = "-1") Integer  materialCategory
                          ){

            Page<MaterialDTO> materialPage = materialService.getAllMaterials(materialName, materialCategory, PageRequest.of(page, size));

        List<MaterialDTO> materials = materialPage.getContent();
        if(materialName==null||materialName==""||materialName.length()<1){materialName = "";}
        List<Code> codeList = materialService.findByCodeCategory("material_category");
        model.addAttribute("materialName", materialName);
        model.addAttribute("materials", materials);
        model.addAttribute("page", materialPage);
        model.addAttribute("code",codeList);

        model.addAttribute("category",materialCategory);
        System.out.println("codeList!!! : "+ codeList);

        return "plan/material";
    }
    @GetMapping("/material_detail")
    public String material_detail(
            Model model, @RequestParam(value = "materialId") Integer materialId){
        System.out.println("materialId : " + materialId);

        MaterialDTO materialDTO = materialService.getMaterialById(materialId);
        System.out.println(materialDTO);
        List<Code> codeList = materialService.findByCodeCategory("material_category");

        model.addAttribute("code",codeList);
        model.addAttribute("material", materialDTO);
        return "plan/material_detail";
    }

    @GetMapping("/material_insert")
    public String material_insert(Model model){


        return "plan/material_insert";
    }

    @PostMapping("/material_save")
    public String material_save(
            MaterialDTO materialDTO
    ){
        System.out.println(materialDTO);
       materialService.saveMaterial(materialDTO);

        return "plan/material_insert";
    }

    @PostMapping("/material_update")
    @ResponseBody
    public ResponseEntity<String> material_update(
            MaterialDTO materialDTO
    ){
        System.out.println(materialDTO);
        try {
        materialService.saveMaterial(materialDTO);
            return ResponseEntity.ok("수정완료");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정실패");
        }
    }

    @PostMapping("/material_delete")
    @ResponseBody
    public ResponseEntity<String> material_delete (
            @RequestBody Integer materialId
    ){
        try {
            materialService.deleteMaterial(materialId);
            return ResponseEntity.ok("삭제완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }
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
    public String quotation(Model model,
                            @RequestParam(value="size", defaultValue = "10") int size,
                            @RequestParam(value="page", defaultValue = "0") int page
                            ){
        System.out.println("견적서접속요청");

        Page<QuotationDTO> QuotationPage = quotationService.findAllwithPageable(PageRequest.of(page, size));
        List<QuotationDTO> quotationDetailDTOList = QuotationPage.getContent();
        model.addAttribute("page", QuotationPage);
        model.addAttribute("quotations", quotationDetailDTOList);

        return "plan/quotation";
    }

    @GetMapping("/procurementPlan")
    public String procurementPlan(Model model){
        List<DocumentDTO> documents = documentService.getAllProcurementPlan();
        model.addAttribute("documents", documents);

        return "plan/procurementPlan";
    }
    @GetMapping("/procurementPlan_insert")
    public String procurementPlan_insert(Model model){
        System.out.println("조달계획서생성");

        return "plan/procurementPlan_insert";
    }

    @PostMapping("/save_quotation")
    @ResponseBody
    public String saveQuotation(@RequestBody QuotationDTO quotationDTO) {
//        String quotationId = LocalDateTime.now().toString() + "-" + new Random().nextInt(10000);
//        quotationDTO.setId(quotationId);
//        if(quotationDTO.getQuotationDetails() !=null){
//            for(QuotationDetailDTO detailDTO : quotationDTO.getQuotationDetails()){
//                detailDTO.setQuotation_id(quotationId);
//            }
//        }
//
       System.out.println(quotationDTO);

        quotationService.saveQuotation(quotationDTO);
        return "견적서저장";
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

    @GetMapping("/quotation_insert")
    public String documentInsert(Model model){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        String quotationId = formattedDate.toString() +"00"+ new Random().nextInt(1000);

        model.addAttribute("quotationId",quotationId);
        return "plan/quotation_insert";
    }

    @GetMapping("/company_search")
    public String company_search(Model model){
        List<CompanyListViewResponse> list = companyService.getCompanyList().stream()
                .map(CompanyListViewResponse::new)
                .toList();
        model.addAttribute("list", list);
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
