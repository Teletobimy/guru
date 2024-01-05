package com.ezen.guru.controller.purchase;

import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.service.purchase.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/purchase")
@Controller
public class OrderController {

    private final OrderServiceImpl orderService;

//    미발주 계약 및 조달 건들의 번호, 회사명, 제목, 날짜를 목록으로 보여주고
//    상세 버튼을 누르면 상세 내역(trade.html 형태) 열람하는 페이지로 이동
//    그 후 발주 버튼을 누르면 상세 내역과 비교하며 입력할 수 있는 폼이 있는 모달 창
//    입력 후 완료 버튼 누르면 유효성 검사 실행, 성공하면 DB에서 발주 상태로 바뀜
//    필요한 발주를 완료한 후 상세 페이지에서 목록 버튼 누르면 화면으로 redirect
    @GetMapping("/order_1")
    public String getPcorderList(Model model) {

        List<OrderListViewResponse> pclist = orderService.getProductOrderDetailsByPurchaseOrderStatus().stream()
                .map(OrderListViewResponse::new)
                .toList();
        model.addAttribute("pclist", pclist);
        return "purchase/order_1";
    }
    @GetMapping("/order_1_detail")
    public String getDocument() {

        return "purchase/order_1_detail";
    }
    @GetMapping("/order_1_doc")
    public String getDetail() {

        return "purchase/order_1_doc";
    }

//    발주 완료된 발주서 목록을 보여주고
//    상세 버튼을 누르면 trade.html 형태의 발주서 열람하는 페이지로 이동
//    끝! 단순 조회 페이지 (수정, 삭제 불가능?)
//    +) 검수 일정 입력하러 가는 페이지
    @GetMapping("/order_2")
    public String pcorder_2() {

        return "purchase/order_2";
    }

//    배송 중인 물품의 번호, 협력사이름, 협력사번호를 목록으로 보여주고
//    운송장을..? 띄워줘야 하나...? 클릭하면 어디로 가야 하나요
    @GetMapping("/order_3")
    public String pcorder_3() {

        return "purchase/order_3";
    }

//    발주가 마감된 품목의 목록을 띄워줍니다
//    클릭하면 발주서 조회
    @GetMapping("/order_4")
    public String pcorder_4() {

        return "purchase/order_4";
    }
}
