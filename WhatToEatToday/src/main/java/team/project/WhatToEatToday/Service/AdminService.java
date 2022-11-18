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
import team.project.WhatToEatToday.dto.EditConcateForm;
import team.project.WhatToEatToday.dto.EditConditionForm;
import team.project.WhatToEatToday.repository.member.AdminRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final EntityManager em;
    private final AdminRepository adminRepository;
    private final MemberService memberService;
    private final ManagerService managerService;
    private final CustomerService customerService;
    private final ConditionService conditionService;
    private final ConditionCategoryService conditionCategoryService;
    private final ConditionMenuService conditionMenuService;
    private final CrossMenuService crossMenuService;
    private final MenuService menuService;






    @Transactional
    public String join(Admin admin) {
        validateDuplicateAdmin(admin);
        adminRepository.save(admin);
        return admin.getId();
    }

    private void validateDuplicateAdmin(Admin admin) {
        List<Admin> findAdmins = adminRepository.findById(admin.getId());
        if(!findAdmins.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    //회원 전체 조회
    public List<Admin> findAdmins() {
        return adminRepository.findAll();
    }

    public Admin findOne(String adminId) {
        return adminRepository.findOne(adminId);
    }

    @Transactional
    public String delete(Admin admin) {
        String deletedAdminId = admin.getId();
        adminRepository.delete(admin);
        return deletedAdminId;
    }

    public String getMembers(Model model) {
        List<Member> members = memberService.findAll();
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
            membersId.add(member.getId());
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
        Member member = memberService.findOne(memberId);
        String memberClassName = member.getClass().getSimpleName();
        switch (memberClassName){
            case "Admin":
                Admin admin = findOne(memberId);
                delete(admin);
                return "redirect:/logout";
            case "Manager":
                Manager manager = managerService.findOne(memberId);
                managerService.delete(manager);
                break;
            case "Customer":
                Customer customer = customerService.findOne(memberId);
                customerService.delete(customer);
                break;
            default:
                session.setAttribute("message", "오류");
                return "redirect:/";
        }
        return "redirect:/admin/members";
    }

    public String recommendMenu(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("member");
        member.getId().isBlank();

        List<Condition> conditionList1 = conditionService.findCate1(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionService.findCate1(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionService.findCate1(3L);
        model.addAttribute("condition3", conditionList3);

        ConditionCategory concate1 = conditionCategoryService.findOne(1L);
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryService.findOne(2L);
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryService.findOne(3L);
        model.addAttribute("concate3", concate3);



        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editConcate(EditConcateForm editConcateForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        String conditionCategory = conditionCategoryService.findOne(concateNumber).getName();
        Long number = concateNumber;
        model.addAttribute("number", number);
        model.addAttribute("page", "editConcate");
        model.addAttribute("condition", conditionCategory);
        model.addAttribute("form", editConcateForm);
        return "layout";
    }

    public String editConcates(EditConcateForm editConcateForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        ConditionCategory conditionCategory = conditionCategoryService.findOne(concateNumber);
        conditionCategory.setName(editConcateForm.getAfter());
        conditionCategoryService.save(conditionCategory);
        ConditionCategory concate1 = conditionCategoryService.findOne(1L);
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryService.findOne(2L);
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryService.findOne(3L);
        model.addAttribute("concate3", concate3);

        List<Condition> conditionList1 = conditionService.findCate1(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionService.findCate1(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionService.findCate1(3L);
        model.addAttribute("condition3", conditionList3);

        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editCondition(EditConditionForm editConditionForm, Model model, @PathVariable Long conditionId) {
        Condition condition = conditionService.findOne(conditionId);
        List<ConditionMenu> conditionMenu = conditionMenuService.findByConditionId(conditionId);
        model.addAttribute("page", "editCondition");
        model.addAttribute("condition_menu", conditionMenu);
        model.addAttribute("condition", condition);
        model.addAttribute("form", editConditionForm);
        return "layout";
    }

    public String editConditions(EditConcateForm editConditonForm, Model model, @PathVariable Long conditionId) {
        Condition condition = conditionService.findOne(conditionId);
        condition.setName(editConditonForm.getAfter());
        conditionService.join(condition);
        List<Condition> conditionList1 = conditionService.findCate1(1L);
        model.addAttribute("condition1", conditionList1);
        List<Condition> conditionList2 = conditionService.findCate1(2L);
        model.addAttribute("condition2", conditionList2);
        List<Condition> conditionList3 = conditionService.findCate1(3L);
        model.addAttribute("condition3", conditionList3);


        ConditionCategory concate1 = conditionCategoryService.findOne(1L);
        model.addAttribute("concate1", concate1);
        ConditionCategory concate2 = conditionCategoryService.findOne(2L);
        model.addAttribute("concate2", concate2);
        ConditionCategory concate3 = conditionCategoryService.findOne(3L);
        model.addAttribute("concate3", concate3);


        model.addAttribute("page", "menuRecommendAdmin");
        return "layout";
    }

    public String editConditionMenu(EditConditionForm editConditionForm, Model model,
                                    @PathVariable Long conditionId,
                                    @PathVariable Long conditionMenuId) {

        ConditionMenu conditionMenu = conditionMenuService.findOne(conditionMenuId);
        model.addAttribute("conditionId", conditionId);
        model.addAttribute("conditionMenuId", conditionMenuId);
        model.addAttribute("page", "editConditionMenu");
        model.addAttribute("condition", conditionMenu.getName());
        model.addAttribute("form", editConditionForm);
        return "layout";
    }

    public String editConditionMenus(EditConditionForm editConditionForm, Model model,
                                     @PathVariable Long conditionId,
                                     @PathVariable Long conditionMenuId) {
        if(!(editConditionForm.getAfter().isBlank())){
            ConditionMenu conditionMenu = conditionMenuService.findOne(conditionMenuId);
            conditionMenu.setName(editConditionForm.getAfter());
            conditionMenuService.save(conditionMenu);

            if(!(crossMenuService.findNewByName(editConditionForm.getAfter()).isEmpty())){
                conditionMenu.setCrossMenu(crossMenuService.findByName(editConditionForm.getAfter()));
                conditionMenuService.save(conditionMenu);
                List<Menu> menuList = menuService.findByName(editConditionForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuService.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenuService.findByName(editConditionForm.getAfter()));
                        menuService.join(menuList.get(i));
                    }
                }
            } else{
                CrossMenu crossMenu = new CrossMenu();
                crossMenu.setName(editConditionForm.getAfter());
                crossMenuService.save(crossMenu);
                conditionMenu.setCrossMenu(crossMenu);
                conditionMenuService.save(conditionMenu);

                List<Menu> menuList = menuService.findByName(editConditionForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuService.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenu);
                        menuService.join(menuList.get(i));
                    }
                }
            }

            List<Condition> conditionList1 = conditionService.findCate1(1L);
            model.addAttribute("condition1", conditionList1);
            List<Condition> conditionList2 = conditionService.findCate1(2L);
            model.addAttribute("condition2", conditionList2);
            List<Condition> conditionList3 = conditionService.findCate1(3L);
            model.addAttribute("condition3", conditionList3);

            ConditionCategory concate1 = conditionCategoryService.findOne(1L);
            model.addAttribute("concate1", concate1);
            ConditionCategory concate2 = conditionCategoryService.findOne(2L);
            model.addAttribute("concate2", concate2);
            ConditionCategory concate3 = conditionCategoryService.findOne(3L);
            model.addAttribute("concate3", concate3);
            model.addAttribute("page", "menuRecommendAdmin");
            List<Menu> menu = menuService.findByName(editConditionForm.getBefore());
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        } else{
            List<Condition> conditionList1 = conditionService.findCate1(1L);
            model.addAttribute("condition1", conditionList1);
            List<Condition> conditionList2 = conditionService.findCate1(2L);
            model.addAttribute("condition2", conditionList2);
            List<Condition> conditionList3 = conditionService.findCate1(3L);
            model.addAttribute("condition3", conditionList3);

            ConditionCategory concate1 = conditionCategoryService.findOne(1L);
            model.addAttribute("concate1", concate1);
            ConditionCategory concate2 = conditionCategoryService.findOne(2L);
            model.addAttribute("concate2", concate2);
            ConditionCategory concate3 = conditionCategoryService.findOne(3L);
            model.addAttribute("concate3", concate3);
            model.addAttribute("page", "menuRecommendAdmin");
            List<Menu> menu = menuService.findByName(editConditionForm.getBefore());
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        }
    }

    public String deleteConditionMenu(
            HttpServletRequest request,
            @PathVariable Long conditionId,
            @PathVariable Long conditionMenuId){
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴삭제");
        ConditionMenu conditionMenu = conditionMenuService.findOne(conditionMenuId);
        conditionMenuService.delete(conditionMenu);
        return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
    }

    public String addConditionMenu(EditConditionForm editConditionForm, Model model,
                                   @PathVariable Long conditionId) {

        model.addAttribute("conditionId", conditionId);
        model.addAttribute("page", "addConditionMenu");
        model.addAttribute("form", editConditionForm);
        return "layout";
    }

    public String addConditionMenus(EditConditionForm editConditionForm, Model model,
                                    HttpServletRequest request,
                                    @PathVariable Long conditionId) {
        HttpSession session = request.getSession();
        Condition condition = conditionService.findOne(conditionId);
        if(editConditionForm.getAfter().isBlank()) {
            session.setAttribute("message", "내용을 입력해주세요");
            return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
        } else{
            ConditionMenu conditionMenu = new ConditionMenu();
            conditionMenu.setName(editConditionForm.getAfter());
            conditionMenu.setCondition(condition);
            if(!(crossMenuService.findNewByName(editConditionForm.getAfter()).isEmpty())){
                conditionMenu.setCrossMenu(crossMenuService.findByName(editConditionForm.getAfter()));
                conditionMenuService.save(conditionMenu);
            } else{
                CrossMenu crossMenu = new CrossMenu();
                crossMenu.setName(editConditionForm.getAfter());
                conditionMenu.setCrossMenu(crossMenu);
                crossMenuService.save(crossMenu);
                conditionMenuService.save(conditionMenu);

                List<Menu> menuList = menuService.findByName(editConditionForm.getAfter());
                for(int i=0; i< menuList.size(); i++){
                    if(menuList.get(i).getCrossMenu().getId().equals(crossMenuService.findByName("기타").getId())){
                        menuList.get(i).setCrossMenu(crossMenu);
                        menuService.join(menuList.get(i));
                    }
                }

            }
        }
        return "redirect:/admin/admin_recommend/editCondition/{conditionId}";
    }
}


