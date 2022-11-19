package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.project.WhatToEatToday.Service.AdminService;
import team.project.WhatToEatToday.Service.CustomerService;
import team.project.WhatToEatToday.Service.ManagerService;
import team.project.WhatToEatToday.Service.MemberService;
import team.project.WhatToEatToday.domain.member.Admin;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.dto.JoinForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final AdminService adminService;
    private final ManagerService managerService;
    private final CustomerService customerService;

    private final MemberService memberService;

    @GetMapping("")
    public String getJoin(Model model) {
        return memberService.getJoin(model);
    }

    @GetMapping("/admin")
    public String getJoinAdmin(Model model) {
        return memberService.getJoinAdmin(model);
    }

    @PostMapping("/admin")
    public String postJoinAdmin(HttpServletRequest request, @Valid JoinForm joinForm) {
        return memberService.postJoinAdmin(request, joinForm);
    }

    @GetMapping("/manager")
    public String getJoinManager(Model model) {
        return memberService.getJoinManager(model);
    }

    @PostMapping("/manager")
    public String postJoinManager(HttpServletRequest request, @Valid JoinForm joinForm) {
        return memberService.postJoinManager(request, joinForm);
    }

    @GetMapping("/customer")
    public String getJoinCustomer(Model model) {
        return memberService.getJoinCustomer(model);
    }

    @PostMapping("/customer")
    public String postJoinCustomer(HttpServletRequest request, @Valid JoinForm joinForm) {
        return memberService.postJoinCustomer(request, joinForm);
    }
}
