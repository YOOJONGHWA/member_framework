package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return "member/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {   // 뷰페이지에서 컨트롤러로 한꺼번에 데이터를 보내기위해서 사용한다
        int saveResult = memberService.save(memberDTO);
        if(saveResult > 0) {
            return "member/login";
        }
        else {
            return "member/save";
        }
    }

    @GetMapping("/login")   // 로그인 페이지를 보여주기 위해서 get 방식 그리고 login.jsp 화면 보여주는 주소
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")     // 로그인 등록 화면 dto를 한번에 보내주는 역활
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {  // 로그인을 하면 세션이 유지되야 하기떄문에 세션사용
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());// jsp 화면에서 유저 이름을 보여주기 위해 네임을 지정
            return "member/main";
        } else {
            return "member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 로그아웃 후 이동할 페이지 설정 (홈 페이지로 이동)
        return "redirect:/member/login";
    }

    @GetMapping("/list")    // 회원 목록을 보여주기 위한 방식 리스트에 dto 값을 보내준다. 그리고 jsp화면에 뿌리기 위해 memberList 이름 지정
    public String findAll(Model model) {
       List<MemberDTO> memberDTOList = memberService.findAll();
       model.addAttribute("memberList",memberDTOList);
       return "member/list";
    }

    // /member?id=1
    @GetMapping //
    public  String findById(@RequestParam("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return  "member/detail";
    }

    // delete?id=
    @GetMapping("/delete")
    public  String delete(@RequestParam("id") Long id) {
        memberService.delete(id);
        return  "redirect:/member/list";
    }
    // update
    @GetMapping("/update")
    public  String updateForm(HttpSession session, Model model) {
        String LoginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(LoginEmail);
        model.addAttribute("member",memberDTO);
        return  "member/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        boolean result = memberService.update(memberDTO);
        if(result) {
            return "redirect:/member?id=" + memberDTO.getId();
        }
        else  {
            return "member/index";
        }
    }

    @PostMapping("/email-check")
    public @ResponseBody String emilCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emilCheck(memberEmail );
        return checkResult;
    }

}
