package team.project.WhatToEatToday.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.LoginForm;
import team.project.WhatToEatToday.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EatingHouseService eatingHouseService;

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

    @Test
    public void EatingHouse() {
        EatingHouse eatingHouse = new EatingHouse();
        eatingHouse.setName("mm");
        Manager manager = new Manager();
        manager.setId(1L);
        manager.setLoginId("asdf");
        memberRepository.save(manager);
        manager.setName("asdf");
        eatingHouse.setId(2L);
        eatingHouse.setManager(manager);
        eatingHouseService.join(eatingHouse);

    }


}