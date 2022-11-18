package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.repository.member.ManagerRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {
    private final EntityManager em;
    private final ManagerRepository managerRepository;

    @Transactional
    public String join(Manager manager) {
        validateDuplicateAdmin(manager);
        managerRepository.save(manager);
        return manager.getId();
    }

    private void validateDuplicateAdmin(Manager manager) {
        List<Manager> findAdmins = managerRepository.findById(manager.getId());
        if(!findAdmins.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    //회원 전체 조회
    public List<Manager> findManagers() {
        return managerRepository.findAll();
    }

    public Manager findOne(String managerId) {
        return managerRepository.findOne(managerId);
    }

    @Transactional
    public String delete(Manager manager) {
        String deletedManagerId = manager.getId();
        managerRepository.delete(manager);
        return deletedManagerId;
    }

    public String getManager(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        try {
            Member member = (Member) session.getAttribute("member");
            Manager manager = managerRepository.findOne(member.getId());
            model.addAttribute("page", "manager");
            model.addAttribute("eatingHouses", manager.getEatingHouses());
            return "layout";
        } catch (Exception e){
            session.setAttribute("message", "유저 정보가 올바르지 않습니다.");
            return "redirect:/logout";
        }

    }
}
