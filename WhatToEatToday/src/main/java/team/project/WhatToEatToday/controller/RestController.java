package team.project.WhatToEatToday.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping("/message_reset")
    public void messageReset(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("message", null);
    }



}
