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
    public String getLogin(Model model) {
        return memberService.getlogin(model);
    }

    @PostMapping
    public String postLogin(HttpServletRequest request, @Valid LoginForm loginForm) {
        return memberService.postlogin(request, loginForm);
    }

}
