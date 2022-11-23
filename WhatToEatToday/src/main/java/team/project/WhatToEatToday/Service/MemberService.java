package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.JoinForm;
import team.project.WhatToEatToday.dto.LoginForm;
import team.project.WhatToEatToday.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public String getJoin(Model model) {
        model.addAttribute("page", "join");
        return "layout";
    }

    public String getJoinManager(Model model) {
        model.addAttribute("page", "joinManager");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }

    @Transactional
    public String postJoinManager(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();

        Manager manager = new Manager();
        manager.setLoginId(joinForm.getId());
        manager.setPassword(joinForm.getPassword());
        manager.setName(joinForm.getName());
        manager.setEmail(joinForm.getEmail());
        manager.setTel(joinForm.getTel());
        manager.setAddress(joinForm.getAddress());
        manager.setAddressDetail(joinForm.getAddressDetail());

        if (memberRepository.existsByLoginId(joinForm.getId())) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/manager";
        } else {
            memberRepository.save(manager);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        }
    }

    public String getJoinCustomer(Model model) {
        model.addAttribute("page", "joinCustomer");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }
    @Transactional
    public String postJoinCustomer(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();

        Customer customer = new Customer();
        customer.setLoginId(joinForm.getId());
        customer.setPassword(joinForm.getPassword());
        customer.setName(joinForm.getName());
        customer.setEmail(joinForm.getEmail());
        customer.setTel(joinForm.getTel());
        customer.setAddress(joinForm.getAddress());
        customer.setAddressDetail(joinForm.getAddressDetail());

        if (memberRepository.existsByLoginId(joinForm.getId())) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/customer";
        } else {
            memberRepository.save(customer);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        }
    }

    public String getlogin(Model model) {
        model.addAttribute("page", "login");
        model.addAttribute("loginForm", new LoginForm());
        return "layout";
    }

    public String postlogin(HttpServletRequest request, @Valid LoginForm loginForm) {
        HttpSession session = request.getSession();

        try {
            Member member = memberRepository.findOneByLoginId(loginForm.getId());
            if(member.getPassword().equals(loginForm.getPassword())) {
                session.setAttribute("login", "logined");
                session.setAttribute("member", member);
                session.setAttribute("id", member.getId());
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

    public String getLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("login", null);
        session.setAttribute("member", null);
        session.setAttribute("memberType", null);
        session.setAttribute("message", "로그아웃");
        return "redirect:/";
    }

}
