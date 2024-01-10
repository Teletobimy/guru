package com.ezen.guru.controller.purchase;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.purchase.*;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/purchase")
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public String getOrderList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "category", defaultValue = "-1") int category) {
        Page<OrderListViewResponse> orderList = orderService.orderList(size, page,keyword,category);
        List<Code> codeList = orderService.findByCodeCategory("purchase_order_status");

        model.addAttribute("list", orderList);
        model.addAttribute("code",codeList);
        model.addAttribute("category",category);
        return "purchase/order";
    }
    @GetMapping("/order_detail")
    public String getDetail(Model model, @RequestParam String id) {
        List<OrderDetailViewResponse> list = orderService.getPurchaseOrderDetail(id).stream()
                .map(OrderDetailViewResponse::new)
                .toList();
        model.addAttribute("list", list);
        return "purchase/order_detail";
    }
    @GetMapping("/order_form")
    public String getForm() {

        return "purchase/order_form";
    }
    @GetMapping("/order_print")
    public String getPrint() {

        return "purchase/order_print";
    }

//    미발주 계약 및 조달 건들의 번호, 회사명, 제목, 날짜를 목록으로 보여주고
//    상세 버튼을 누르면 상세 내역 열람하는 페이지로 이동
//    그 후 발주 버튼을 누르면 상세 내역과 비교하며 입력할 수 있는 폼이 있는 모달 창
//    입력 후 완료 버튼 누르면 유효성 검사 실행, 성공하면 DB에서 발주 상태로 바뀜
//    필요한 발주를 완료한 후 상세 페이지에서 목록 버튼 누르면 화면으로 redirect

//    발주 완료된 발주서 목록을 보여주고
//    상세 버튼을 누르면 trade.html 형태의 발주서 열람하는 페이지로 이동
//    끝! 단순 조회 페이지 (수정, 삭제 불가능?)
//    +) 검수 일정 입력하러 가는 페이지(jquery datepicker_예정)

//    발주가 마감된 품목의 목록을 띄워줍니다
//    클릭하면 발주서 조회

}
