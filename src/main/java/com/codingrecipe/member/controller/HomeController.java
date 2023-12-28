package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller     // 스프링이 @Controller 어노테이션을 확인해서 구현해준다.
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index"; // jsp 파일 이름이라 생각하면 됨
    }
}