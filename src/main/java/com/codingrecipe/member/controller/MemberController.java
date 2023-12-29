package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @GetMapping("/login")   // 로그인 페이지를 보여주기 위해서 get 방식 그리고 login.jsp 화면 보여주는 주소
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")     // 로그인 등록 화면 dto를 한번에 보내주는 역활
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {  // 로그인을 하면 세션이 유지되야 하기떄문에 세션사용
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());// jsp 화면에서 유저 이름을 보여주기 위해 네임을 지정
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping("/")    // 회원 목록을 보여주기 위한 방식 리스트에 dto 값을 보내준다. 그리고 jsp화면에 뿌리기 위해 memberList 이름 지정
    public String findAll(Model model) {
       List<MemberDTO> memberDTOList = memberService.findAll();
       model.addAttribute("memberList",memberDTOList);
       return "list";
    }

}
