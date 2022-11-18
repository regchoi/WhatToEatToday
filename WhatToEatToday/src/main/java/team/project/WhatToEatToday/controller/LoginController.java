package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.project.WhatToEatToday.Service.MemberService;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping
    public String getlogin(Model model) {
        model.addAttribute("page", "login");
        model.addAttribute("loginForm", new LoginForm());
        return "layout";
    }

    @PostMapping
    public String postlogin(HttpServletRequest request, @Valid LoginForm loginForm) {
        HttpSession session = request.getSession();
        try {
            Member member = memberService.findOne(loginForm.getId());
            if(member.getPassword().equals(loginForm.getPassword())) {
                session.setAttribute("login", "logined");
                session.setAttribute("member", member);
                session.setAttribute("memberType", member.getClass().getSimpleName());
                session.setAttribute("message", "로그인 성공");
                return "redirect:";
            } else {
                session.setAttribute("message", "비밀번호가 올바르지 않습니다.");
                return "redirect:/login";
            }
        } catch (Exception e){
            session.setAttribute("message", "아이디가 존재하지 않습니다.");
            return "redirect:/login";
        }
    }

}
