package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.project.WhatToEatToday.Service.AdminService;
import team.project.WhatToEatToday.dto.EditForm;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public final AdminService adminService;

    @GetMapping("/members")
    public String getMembers(Model model) {
        return adminService.getMembers(model);
    }

    @GetMapping("/members/delete/{memberId}")
    public String deleteMembers(HttpServletRequest request, @PathVariable String memberId) {
        return adminService.deleteMembers(request, memberId);
    }

    @GetMapping("/admin_recommend")
    public String recommendMenu(Model model) {
        return adminService.recommendMenu(model);
    }

    @GetMapping("/admin_recommend/editConcate/{ConcateNumber}")
    public String editConcate(EditForm editForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        return adminService.editConcate(editForm, model, concateNumber);

    }
    @PostMapping("/admin_recommend/editConcate/{ConcateNumber}")
    public String editConcates(EditForm editForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        return adminService.editConcates(editForm, model, concateNumber);
    }


    @GetMapping("/admin_recommend/editCondition/{conditionId}")
    public String editCondition(EditForm editForm, Model model, @PathVariable Long conditionId) {
        return adminService.editCondition(editForm, model, conditionId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}")
    public String editConditions(EditForm editForm, Model model, @PathVariable Long conditionId) {
        return adminService.editConditions(editForm, model, conditionId);
    }

    @GetMapping("/admin_recommend/editCondition/{conditionId}/editMenu/{conditionMenuId}")
    public String editConditionMenu(EditForm editForm, Model model,
                                    @PathVariable Long conditionId,
                                    @PathVariable Long conditionMenuId) {

        return adminService.editConditionMenu(editForm, model, conditionId, conditionMenuId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}/editMenu/{conditionMenuId}")
    public String editConditionMenus(EditForm editForm, Model model,
                                     @PathVariable Long conditionId,
                                     @PathVariable Long conditionMenuId) {
        return adminService.editConditionMenus(editForm, model, conditionId, conditionMenuId);
    }

    @GetMapping("/admin_recommend/editCondition/{conditionId}/delete/{conditionMenuId}")
    public String deleteConditionMenu(
            HttpServletRequest request,
            @PathVariable Long conditionId,
            @PathVariable Long conditionMenuId){
        return adminService.deleteConditionMenu(request, conditionId, conditionMenuId);
    }


    @GetMapping("/admin_recommend/editCondition/{conditionId}/addMenu")
    public String addConditionMenu(EditForm editForm, Model model,
                                   @PathVariable Long conditionId) {

        return adminService.addConditionMenu(editForm, model, conditionId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}/addMenu")
    public String addConditionMenus(EditForm editForm, Model model,
                                    HttpServletRequest request,
                                    @PathVariable Long conditionId) {
        return adminService.addConditionMenus(editForm, model, request, conditionId);
    }



}
