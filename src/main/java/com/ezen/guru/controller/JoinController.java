package com.ezen.guru.controller;

import com.ezen.guru.dto.JoinDTO;
import com.ezen.guru.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){
        joinService.join(joinDTO);

        return "redirect:/login";
    }
}
