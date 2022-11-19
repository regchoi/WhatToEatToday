package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.project.WhatToEatToday.Service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final MemberService memberService;

    @GetMapping
    public String getLogout(HttpServletRequest request) {
        return memberService.getLogout(request);
    }

}
