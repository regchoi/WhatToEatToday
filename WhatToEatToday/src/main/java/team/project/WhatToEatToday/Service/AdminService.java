package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import team.project.WhatToEatToday.domain.*;
import team.project.WhatToEatToday.domain.member.Admin;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EditForm;
import team.project.WhatToEatToday.dto.JoinForm;
import team.project.WhatToEatToday.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final MemberRepository memberRepository;
    private final ConditionRepository conditionRepository;
    private final ConditionCategoryRepository conditionCategoryRepository;
    private final ConditionMenuRepository conditionMenuRepository;
    private final CrossMenuRepository crossMenuRepository;
    private final MenuRepository menuRepository;


    public String getMembers(Model model) {
        List<Member> members = memberRepository.findAll();
        ArrayList<String> memberKindList = new ArrayList<>(Arrays.asList("Admin", "Manager", "Customer"));
        members.sort(Comparator.comparingInt(a -> memberKindList.indexOf(a.getClass().getSimpleName())));

        HashMap<String, Object> jsMembers = new HashMap<>();
        List<String> membersId = new ArrayList<>();
        List<String> membersPassword = new ArrayList<>();
        List<String> membersName = new ArrayList<>();
        List<String> membersEmail = new ArrayList<>();
        List<String> membersTel = new ArrayList<>();
        List<String> membersAddress = new ArrayList<>();
        List<String> membersAddressDetail = new ArrayList<>();
        for (Member member : members){
            membersId.add(member.getLoginId());
            membersPassword.add(member.getPassword());
            membersName.add(member.getName());
            membersEmail.add(member.getEmail());
            membersTel.add(member.getTel());
            membersAddress.add(member.getAddress());
            membersAddressDetail.add(member.getAddressDetail());
        }
        jsMembers.put("id", membersId);
        jsMembers.put("password", membersPassword);
        jsMembers.put("name", membersName);
        jsMembers.put("email", membersEmail);
        jsMembers.put("tel", membersTel);
        jsMembers.put("address", membersAddress);
        jsMembers.put("addressDetail", membersAddressDetail);

        model.addAttribute("page", "members");
        model.addAttribute("members", members);
        model.addAttribute("jsMembers", jsMembers);

        return "layout";
    }

    public String deleteMembers(HttpServletRequest request, @PathVariable String memberId) {
        HttpSession session = request.getSession();
        RestTemplate restTemplate = new RestTemplate();
        Member member = memberRepository.findOneByLoginId(memberId);
        String memberClassName = member.getClass().getSimpleName();
        switch (memberClassName){
            case "Admin":
                Admin admin = (Admin) memberRepository.findOneByLoginId(memberId);
                memberRepository.delete(admin);
                return "redirect:/logout";
            case "Manager":
                Manager manager = (Manager) memberRepository.findOneByLoginId(memberId);
                memberRepository.delete(manager);
                break;
            case "Customer":
                Customer customer = (Customer) memberRepository.findOneByLoginId(memberId);
                memberRepository.delete(customer);
                break;
            default:
                session.setAttribute("message", "오류");
                return "redirect:/";
        }
        return "redirect:/admin/members";
    }

    public String recommendMenu(Model model) {

        List<Condition> conditionList1 = conditionRepository.findAllByConditionCategoryId(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionRepository.findAllByConditionCategoryId(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionRepository.findAllByConditionCategoryId(3L);
        model.addAttribute("condition3", conditionList3);

        ConditionCategory concate1 = conditionCategoryRepository.findById(1L).orElseThrow();
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryRepository.findById(2L).orElseThrow();
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryRepository.findById(3L).orElseThrow();
        model.addAttribute("concate3", concate3);

        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editConcate(EditForm editForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        String conditionCategory = conditionCategoryRepository.findById(concateNumber).orElseThrow().getName();
        Long number = concateNumber;
        model.addAttribute("number", number);
        model.addAttribute("page", "editConcate");
        model.addAttribute("condition", conditionCategory);
        model.addAttribute("form", editForm);
        return "layout";
    }

    public String editConcates(EditForm editForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        ConditionCategory conditionCategory = conditionCategoryRepository.findById(concateNumber).orElseThrow();
        conditionCategory.setName(editForm.getAfter());
        conditionCategoryRepository.save(conditionCategory);
        ConditionCategory concate1 = conditionCategoryRepository.findById(1L).orElseThrow();
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryRepository.findById(2L).orElseThrow();
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryRepository.findById(3L).orElseThrow();
        model.addAttribute("concate3", concate3);

        List<Condition> conditionList1 = conditionRepository.findAllByConditionCategoryId(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionRepository.findAllByConditionCategoryId(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionRepository.findAllByConditionCategoryId(3L);
        model.addAttribute("condition3", conditionList3);

        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editCondition(EditForm editForm, Model model, @PathVariable Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId).orElseThrow();
        List<ConditionMenu> conditionMenu = conditionMenuRepository.findAllByConditionId(conditionId);
        model.addAttribute("page", "editCondition");
        model.addAttribute("condition_menu", conditionMenu);
        model.addAttribute("condition", condition);
        model.addAttribute("form", editForm);
        return "layout";
    }

    public String editConditions(EditForm editForm, Model model, @PathVariable Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId).orElseThrow();
        condition.setName(editForm.getAfter());
        conditionRepository.save(condition);
        List<Condition> conditionList1 = conditionRepository.findAllByConditionCategoryId(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionRepository.findAllByConditionCategoryId(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionRepository.findAllByConditionCategoryId(3L);
        model.addAttribute("condition3", conditionList3);


        ConditionCategory concate1 = conditionCategoryRepository.findById(1L).orElseThrow();
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryRepository.findById(2L).orElseThrow();
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryRepository.findById(3L).orElseThrow();
        model.addAttribute("concate3", concate3);


        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editConditionMenu(EditForm editForm, Model model,
                                    @PathVariable Long conditionId,
                                    @PathVariable Long conditionMenuId) {

        ConditionMenu conditionMenu = conditionMenuRepository.findById(conditionMenuId).orElseThrow();
        model.addAttribute("conditionId", conditionId);
        model.addAttribute("conditionMenuId", conditionMenuId);
        model.addAttribute("page", "editConditionMenu");
        model.addAttribute("condition", conditionMenu.getName());
        model.addAttribute("form", editForm);
        return "layout";
    }

    @Transactional
    public String editConditionMenus(EditForm editForm, Model model,
                                     @PathVariable Long conditionId,
                                     @PathVariable Long conditionMenuId) {
        if(!(editForm.getAfter().isBlank())){
            ConditionMenu conditionMenu = conditionMenuRepository.findById(conditionMenuId).orElseThrow();
            conditionMenu.setName(editForm.getAfter());
            conditionMenuRepository.save(conditionMenu);

            if(!(crossMenuRepository.findAllByName(editForm.getAfter()).isEmpty())){
                conditionMenu.setCrossMenu(crossMenuRepository.findByName(editForm.getAfter()));
                conditionMenuRepository.save(conditionMenu);
                List<Menu> menuList = menuRepository.findAllByName(editForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuRepository.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenuRepository.findByName(editForm.getAfter()));
                        menuRepository.save(menuList.get(i));
                    }
                }
            } else{
                CrossMenu crossMenu = new CrossMenu();
                crossMenu.setName(editForm.getAfter());
                crossMenuRepository.save(crossMenu);
                conditionMenu.setCrossMenu(crossMenu);
                conditionMenuRepository.save(conditionMenu);

                List<Menu> menuList = menuRepository.findAllByName(editForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuRepository.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenu);
                        menuRepository.save(menuList.get(i));
                    }
                }
            }

            List<Condition> conditionList1 = conditionRepository.findAllByConditionCategoryId(1L);
            model.addAttribute("condition1", conditionList1);
            List<Condition> conditionList2 = conditionRepository.findAllByConditionCategoryId(2L);
            model.addAttribute("condition2", conditionList2);
            List<Condition> conditionList3 = conditionRepository.findAllByConditionCategoryId(3L);
            model.addAttribute("condition3", conditionList3);

            ConditionCategory concate1 = conditionCategoryRepository.findById(1L).orElseThrow();
            model.addAttribute("concate1", concate1);
            ConditionCategory concate2 = conditionCategoryRepository.findById(2L).orElseThrow();
            model.addAttribute("concate2", concate2);
            ConditionCategory concate3 = conditionCategoryRepository.findById(3L).orElseThrow();
            model.addAttribute("concate3", concate3);
            model.addAttribute("page", "menuRecommendAdmin");
            List<Menu> menu = menuRepository.findAllByName(editForm.getBefore());
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        } else{
            List<Condition> conditionList1 = conditionRepository.findAllByConditionCategoryId(1L);
            model.addAttribute("condition1", conditionList1);
            List<Condition> conditionList2 = conditionRepository.findAllByConditionCategoryId(2L);
            model.addAttribute("condition2", conditionList2);
            List<Condition> conditionList3 = conditionRepository.findAllByConditionCategoryId(3L);
            model.addAttribute("condition3", conditionList3);

            ConditionCategory concate1 = conditionCategoryRepository.findById(1L).orElseThrow();
            model.addAttribute("concate1", concate1);
            ConditionCategory concate2 = conditionCategoryRepository.findById(2L).orElseThrow();
            model.addAttribute("concate2", concate2);
            ConditionCategory concate3 = conditionCategoryRepository.findById(3L).orElseThrow();
            model.addAttribute("concate3", concate3);
            model.addAttribute("page", "menuRecommendAdmin");
            List<Menu> menu = menuRepository.findAllByName(editForm.getBefore());
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        }
    }

    public String deleteConditionMenu(
            HttpServletRequest request,
            @PathVariable Long conditionId,
            @PathVariable Long conditionMenuId){
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴삭제");
        ConditionMenu conditionMenu = conditionMenuRepository.findById(conditionMenuId).orElseThrow();
        conditionMenuRepository.delete(conditionMenu);
        return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
    }

    public String addConditionMenu(EditForm editForm, Model model,
                                   @PathVariable Long conditionId) {

        model.addAttribute("conditionId", conditionId);
        model.addAttribute("page", "addConditionMenu");
        model.addAttribute("form", editForm);
        return "layout";
    }

    @Transactional
    public String addConditionMenus(EditForm editForm, Model model,
                                    HttpServletRequest request,
                                    @PathVariable Long conditionId) {
        HttpSession session = request.getSession();
        Condition condition = conditionRepository.findById(conditionId).orElseThrow();
        if(editForm.getAfter().isBlank()) {
            session.setAttribute("message", "내용을 입력해주세요");
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        } else{
            ConditionMenu conditionMenu = new ConditionMenu();
            conditionMenu.setName(editForm.getAfter());
            conditionMenu.setCondition(condition);
            if(!(crossMenuRepository.findAllByName(editForm.getAfter()).isEmpty())){
                conditionMenu.setCrossMenu(crossMenuRepository.findByName(editForm.getAfter()));
                conditionMenuRepository.save(conditionMenu);
            } else{
                CrossMenu crossMenu = new CrossMenu();
                crossMenu.setName(editForm.getAfter());
                conditionMenu.setCrossMenu(crossMenu);
                crossMenuRepository.save(crossMenu);
                conditionMenuRepository.save(conditionMenu);
                List<Menu> menuList = menuRepository.findAllByName(editForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuRepository.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenu);
                        menuRepository.save(menuList.get(i));
                    }
                }

            }
        }
        return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
    }
    public String postJoinAdmin(HttpServletRequest request, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();
        try {
            Admin admin = new Admin();
            admin.setLoginId(joinForm.getId());
            admin.setPassword(joinForm.getPassword());
            admin.setName(joinForm.getName());
            admin.setEmail(joinForm.getEmail());
            admin.setTel(joinForm.getTel());
            admin.setAddress(joinForm.getAddress());
            admin.setAddressDetail(joinForm.getAddressDetail());
            memberRepository.save(admin);
            session.setAttribute("message", "회원가입 되셨습니다.");
            return "redirect:/";
        } catch (IllegalStateException e) {
            session.setAttribute("message", "이미 존재하는 아이디 입니다.");
            return "redirect:/join/admin";
        }
    }

    public String getJoinAdmin(Model model) {
        model.addAttribute("page", "joinAdmin");
        model.addAttribute("joinForm", new JoinForm());
        return "layout";
    }
}


