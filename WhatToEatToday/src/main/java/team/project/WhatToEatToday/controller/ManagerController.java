package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.project.WhatToEatToday.Service.*;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.CrossMenu;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EatingHouseForm;
import team.project.WhatToEatToday.dto.MenuForm;
import team.project.WhatToEatToday.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final EatingHouseService eatingHouseService;
    private final MenuService menuService;
    private final CategoryService categoryService;
    private final CrossMenuService crossMenuService;

    @GetMapping("/eating_house")
    public String getManager(HttpServletRequest request, Model model) {
        return managerService.getManager(request, model);
    }

    @GetMapping("/eating_house/add")
    public String getAddEatingHouse(Model model) {
        return managerService.getAddEatingHouse(model);
    }

    @PostMapping("/eating_house/add")
    public String postAddEatingHouse(HttpServletRequest request, @Valid EatingHouseForm eatingHouseForm) {
        return managerService.postAddEatingHouse(request, eatingHouseForm);
    }


    @GetMapping("/eating_house/edit/{eatingHouseId}")
    public String getEatingHouseEdit(@PathVariable Long eatingHouseId, Model model, EatingHouseForm eatingHouseForm) {
        return managerService.getEatingHouseEdit(eatingHouseId, model, eatingHouseForm);
    }

    @PostMapping("/eating_house/edit/{eatingHouseId}")
    public String postEatingHouseDetail(@PathVariable Long eatingHouseId, @Valid EatingHouseForm eatingHouseForm) {
        return managerService.postEatingHouseDetail(eatingHouseId, eatingHouseForm);
    }

    @GetMapping("/eating_house/delete/{eatingHouseId}")
    public String deleteEatingHouseDetail(HttpServletRequest request, @PathVariable Long eatingHouseId) {
        return managerService.deleteEatingHouseDetail(request, eatingHouseId);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/add")
    public String getAddMenu(@PathVariable Long eatingHouseId, Model model, MenuForm menuForm){
        return managerService.getAddMenu(eatingHouseId, model, menuForm);
    }

    @PostMapping("/eating_house/edit/{eatingHouseId}/menu/add")
    public String postAddMenu(HttpServletRequest request, @PathVariable Long eatingHouseId, @Valid MenuForm menuForm){
        return managerService.postAddMenu(request, eatingHouseId, menuForm);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/edit/{menuId}")
    public String getEditMenu(
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId, Model model, MenuForm menuForm){
        return managerService.getEditMenu(eatingHouseId, menuId, model, menuForm);
    }

    @PostMapping("/eating_house/edit/{eatingHouseId}/menu/edit/{menuId}")
    public String postEditMenu(HttpServletRequest request, @PathVariable Long eatingHouseId,
                               @PathVariable Long menuId, @Valid MenuForm menuForm) {
        return managerService.postEditMenu(request, eatingHouseId, menuId, menuForm);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/delete/{menuId}")
    public String deleteMenu(
            HttpServletRequest request,
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId){
        return managerService.deleteMenu(request, eatingHouseId, menuId);
    }
}
