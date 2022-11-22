package team.project.WhatToEatToday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import team.project.WhatToEatToday.Service.CategoryService;
import team.project.WhatToEatToday.Service.ConditionCategoryService;
import team.project.WhatToEatToday.Service.ConditionMenuService;
import team.project.WhatToEatToday.Service.ConditionService;
import team.project.WhatToEatToday.Service.CustomerService;
import team.project.WhatToEatToday.Service.EatingHouseService;
import team.project.WhatToEatToday.Service.MenuService;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.domain.ConditionCategory;
import team.project.WhatToEatToday.domain.ConditionMenu;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.JoinForm;
import team.project.WhatToEatToday.dto.LongIdForm;
import team.project.WhatToEatToday.dto.MenuForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.*;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService customerService;

	@GetMapping("/mypage/{customerId}")
    public String getMypage(@PathVariable Long customerId, Model model) {
        return customerService.getMypage(customerId, model);
    }

    @GetMapping("/mypage/edit/{customerId}")
    public String getEditMypage(@PathVariable Long customerId, Model model, JoinForm joinForm) {
        return customerService.getEditMypage(customerId, model, joinForm);
    }

    @PostMapping("/mypage/edit/{customerId}")
    public String postEditMypage(HttpServletRequest request , @PathVariable Long customerId, @Valid JoinForm joinForm) {
        return customerService.postEditMypage(request, customerId, joinForm);
    }

    @GetMapping("/mypage/delete/{customerId}")
    public String deleteMypage(HttpServletRequest request , @PathVariable Long customerId) {
        return customerService.deleteMypage(request, customerId);
    }

	@GetMapping("/recommend")
    public String recommendMenu(HttpServletRequest request, Model model, LongIdForm longIdForm) {
        return customerService.recommendMenu(request, model, longIdForm);
    }

	@GetMapping("/recommendResult")
    public String recommendMenuResult(LongIdForm longIdForm, Model model, @RequestParam(name = "longId", required = false) List<Long> longid) {
        return customerService.recommendMenuResult(longIdForm, model, longid);
    }

    @GetMapping("/eating_house/{eatingHouseId}")
    public String searchEatingHouse(@PathVariable Long eatingHouseId, Model model) {
        return customerService.searchEatingHouse(eatingHouseId, model);
    }
	
	@GetMapping("/menuAll")
    public String viewMenuAll(Model model) {
        return customerService.viewMenuAll(model);
    }

    @GetMapping("/eating_house/All")
    public String viewAll(Model model){
        return customerService.viewAll(model);
    }

    @GetMapping("/eating_house/All/{eatingHouseId}")
    public String viewMenu(@PathVariable Long eatingHouseId,  Model model) {
        return customerService.viewMenu(eatingHouseId, model);
    }


    @GetMapping("/eating_house/Kfood")
    public String viewKfood(Model model) {
       return customerService.viewKfood(model);
    }

    @GetMapping("/eating_house/Jfood")
    public String viewJfood(Model model) {
        return customerService.viewJfood(model);
    }

    @GetMapping("/eating_house/Cfood")
    public String viewCfood(Model model) {
        return customerService.viewCfood(model);
    }

    @GetMapping("/eating_house/Wfood")
    public String viewWfood(Model model) {
        return customerService.viewWfood(model);
    }

    @GetMapping("/eating_house/Chicken")
    public String viewChicken(Model model) {
        return customerService.viewChicken(model);
    }

    @GetMapping("/eating_house/Bunsik")
    public String viewBunsik(Model model) {
        return customerService.viewBunsik(model);
    }

    @GetMapping("/eating_house/Dessert")
    public String viewDessert(Model model) {
        return customerService.viewDessert(model);
    }


}
