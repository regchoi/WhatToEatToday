package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    @GetMapping
    public String getLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("login", null);
        session.setAttribute("member", null);
        session.setAttribute("memberType", null);
        session.setAttribute("message", "로그아웃");
        return "redirect:/";
    }

}
