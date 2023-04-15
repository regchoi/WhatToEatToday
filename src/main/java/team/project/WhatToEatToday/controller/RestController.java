package team.project.WhatToEatToday.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {

    @RequestMapping("/message_reset")
    public void messageReset(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("message", null);
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));
    }



}
