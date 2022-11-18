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

    @GetMapping("")
    public String getJoin(Model model) {
        model.addAttribute("page", "join");
        return "layout";
    }

    @GetMapping("/admin")
    public String getJoinAdmin(Model model) {
        model.addAttribute("page", "joinAdmin");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }

    @PostMapping("/admin")
    public String postJoinAdmin(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();
        try {
            Admin admin = new Admin();
            admin.setId(joinForm.getId());
            admin.setPassword(joinForm.getPassword());
            admin.setName(joinForm.getName());
            admin.setEmail(joinForm.getEmail());
            admin.setTel(joinForm.getTel());
            admin.setAddress(joinForm.getAddress());
            admin.setAddressDetail(joinForm.getAddressDetail());
            adminService.join(admin);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        } catch (IllegalStateException e) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/admin";
        }
    }

    @GetMapping("/manager")
    public String getJoinManager(Model model) {
        model.addAttribute("page", "joinManager");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }

    @PostMapping("/manager")
    public String postJoinManager(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();
        try {
            Manager manager = new Manager();
            manager.setId(joinForm.getId());
            manager.setPassword(joinForm.getPassword());
            manager.setName(joinForm.getName());
            manager.setEmail(joinForm.getEmail());
            manager.setTel(joinForm.getTel());
            manager.setAddress(joinForm.getAddress());
            manager.setAddressDetail(joinForm.getAddressDetail());
            managerService.join(manager);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        } catch (IllegalStateException e) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/manager";
        }
    }

    @GetMapping("/customer")
    public String getJoinCustomer(Model model) {
        model.addAttribute("page", "joinCustomer");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }

    @PostMapping("/customer")
    public String postJoinCustomer(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();
        try {
            Customer customer = new Customer();
            customer.setId(joinForm.getId());
            customer.setPassword(joinForm.getPassword());
            customer.setName(joinForm.getName());
            customer.setEmail(joinForm.getEmail());
            customer.setTel(joinForm.getTel());
            customer.setAddress(joinForm.getAddress());
            customer.setAddressDetail(joinForm.getAddressDetail());
            customerService.join(customer);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        } catch (IllegalStateException e) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/customer";
        }
    }
}
