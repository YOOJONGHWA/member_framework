package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")  // 자주쓸 주소는 한꺼번에 모아서 할수도 있다!
@RequiredArgsConstructor       // IoC 의존성 주입을 해서 객체를 생성하고 사용할수 있게 한다.
public class MemberController {
    private final MemberService memberService;  // final 사용이유 변하지 않는 변수값을 사용하기 위해

    //    @GetMapping("/member/save") // /member/member/save
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {   // 뷰페이지에서 컨트롤러로 한꺼번에 데이터를 보내기위해서 사용한다
        int saveResult = memberService.save(memberDTO);
        if(saveResult > 0) {
            return "login";
        }
        else {
            return "save";
        }
    }
}
