package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.domain.ConditionCategory;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.JoinForm;
import team.project.WhatToEatToday.dto.LongIdForm;
import team.project.WhatToEatToday.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final ConditionCategoryRepository conditionCategoryRepository;
    private final ConditionRepository conditionRepository;
    private final MembersRepository memberRepository;
    private final MenuRepository menuRepository;
    private final EatingHouseRepository eatingHouseRepository;

    public String getMypage(@PathVariable Long customerId, Model model) {
        model.addAttribute("customer", memberRepository.findById(customerId).orElseThrow());
        model.addAttribute("page", "myPage");
        return "layout";
    }

    public String getEditMypage(@PathVariable Long customerId, Model model, JoinForm joinForm) {
        model.addAttribute("customer", memberRepository.findById(customerId).orElseThrow());
        model.addAttribute("JoinForm", joinForm);
        model.addAttribute("page", "editCustomerInfo");
        return "layout";
    }

    @Transactional
    public String postEditMypage(HttpServletRequest request , @PathVariable Long customerId, @Valid JoinForm joinForm) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) memberRepository.findById(customerId).orElseThrow();
        customer.setName(joinForm.getName());
        customer.setPassword(joinForm.getPassword());
        customer.setEmail(joinForm.getEmail());
        customer.setTel(joinForm.getTel());
        customer.setAddress(joinForm.getAddress());
        customer.setAddressDetail(joinForm.getAddressDetail());
        memberRepository.save(customer);
        session.setAttribute("message", "회원정보 변경");
        return "redirect:/customer/mypage/" + customerId;
    }

    @Transactional
    public String deleteMypage(HttpServletRequest request , @PathVariable Long customerId) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) memberRepository.findById(customerId).orElseThrow();
        memberRepository.delete(customer);
        session.setAttribute("message", "회원탈퇴");
        return "redirect:/logout";
    }



    public String recommendMenu(HttpServletRequest request, Model model, LongIdForm longIdForm) {
        HttpSession session= request.getSession();
        try {
            Member member = (Member) session.getAttribute("member");

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

            model.addAttribute("longIdForm", longIdForm);
            model.addAttribute("page", "menuRecommend");

            return "layout";
        }
        catch (Exception e){
            session.setAttribute("message","로그인 이후에 이용 가능합니다");
            return "redirect:/";
        }
    }

    public String recommendMenuResult(LongIdForm longIdForm, Model model, @RequestParam(name = "longId", required = false) List<Long> longid) {

        List<Menu> menuList = menuRepository.findAll();

        HashMap<String, Object> menus = new HashMap<>();
        List<Long> menuId = new ArrayList<>();
        List<String> menuName = new ArrayList<>();
        List<Integer> menuPrice = new ArrayList<>();
        List<String> menuAddress = new ArrayList<>();
        List<String> menuStore = new ArrayList<>();
        for (Menu menu : menuList) {
            menuId.add(menu.getId());
            menuName.add(menu.getName());
            menuPrice.add(menu.getPrice());
            menuAddress.add(menu.getEatingHouse().getAddress());
            menuStore.add(menu.getEatingHouse().getName());
        }
        menus.put("id", menuId);
        menus.put("name", menuName);
        menus.put("price", menuPrice);
        menus.put("address", menuAddress);
        menus.put("store", menuStore);

        model.addAttribute("menus", menus);
        model.addAttribute("menuList", menuList);
        model.addAttribute("page", "menuRecommendResult");
        return "layout";
    }

    public String searchEatingHouse(@PathVariable Long eatingHouseId, Model model) {


        EatingHouse eatingHouses = eatingHouseRepository.findById(eatingHouseId).orElseThrow();

        model.addAttribute("page", "viewKfood");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewMenuAll(Model model) {
        model.addAttribute("page", "viewAll");
        return "layout";
    }

    public String viewAll(Model model){
        List<Menu> menu = menuRepository.findAll();

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewAll");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewMenu(@PathVariable Long eatingHouseId,  Model model) {
        EatingHouse eatingHouses = eatingHouseRepository.findById(eatingHouseId).orElseThrow();

        model.addAttribute("eatingHouse", eatingHouses);
        model.addAttribute("page", "menuList");
        return "layout";
    }

    public String viewKfood(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(2L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0 ){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewKfood");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewJfood(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(3L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }
        model.addAttribute("page", "viewJfood");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewCfood(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(4L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewCfood");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewWfood(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(5L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewWfood");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewChicken(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(6L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewChicken");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewBunsik(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(7L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewBunsik");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }

    public String viewDessert(Model model) {
        List<Menu> menu = menuRepository.findAllByCategoryId(8L);

        List<EatingHouse> eatingHouses = new ArrayList<>();
        for(int i=0; i<menu.size(); i++){
            int x = 0;
            for(int j=0; j< eatingHouses.size(); j++){
                if(eatingHouses.get(j).getId().equals(menu.get(i).getEatingHouse().getId())){
                    x++;
                }
            }
            if(x ==0){
                eatingHouses.add(menu.get(i).getEatingHouse());
            }
        }

        model.addAttribute("page", "viewDessert");
        model.addAttribute("eatingHouse", eatingHouses);

        return "layout";
    }




}
