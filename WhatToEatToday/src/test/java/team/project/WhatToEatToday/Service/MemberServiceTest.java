package team.project.WhatToEatToday.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.LoginForm;
import team.project.WhatToEatToday.repository.member.MemberRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    EntityManager em;

    @Test
    public void loginTest() {
//        LoginForm loginForm = new LoginForm();
//        loginForm.setId("admin");
//        loginForm.setPassword("admin");
//        postlogin(loginForm);
        System.out.println(memberRepository.existsByLoginId("admin"));
        System.out.println(memberRepository.existsByLoginId("mm"));
    }

    @Test
    public void saveTest() {
//        Manager manager = new Manager();
//        manager.setLoginId("mm");
//        memberRepository.save(manager);
//        em.clear();

    }

    @Test
    public void postlogin(@Valid LoginForm loginForm) {
        Member member = memberRepository.findOneByLoginId(loginForm.getId());
        System.out.println(member.getLoginId());
        System.out.println(member.getId());

//        if(member.getPassword().equals(loginForm.getPassword())) {
//            session.setAttribute("login", "logined");
//            session.setAttribute("member", member);
//            session.setAttribute("id", member.getId());
//            session.setAttribute("memberType", member.getClass().getSimpleName());
//            session.setAttribute("message", "로그인 성공");
//            return "redirect:";
//        }
//        else {
//            session.setAttribute("message", "비밀번호가 올바르지 않습니다.");
//            return "redirect:/login";
//        }
    }


}