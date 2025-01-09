package com.bitc.jwtserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // localhost:8080/board/ 로 리다이렉트하기 위한 컨트롤러 및 메서드
    @GetMapping({"", "/"})
    public String index() throws Exception {
        return "redirect:/board/";
    }
}