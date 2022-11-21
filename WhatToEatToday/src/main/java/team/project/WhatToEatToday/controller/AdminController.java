package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import team.project.WhatToEatToday.Service.*;
import team.project.WhatToEatToday.domain.*;
import team.project.WhatToEatToday.domain.member.Admin;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EditConcateForm;
import team.project.WhatToEatToday.dto.EditConditionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.*;

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
    public String editConcate(EditConcateForm editConcateForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        return adminService.editConcate(editConcateForm, model, concateNumber);

    }
    @PostMapping("/admin_recommend/editConcate/{ConcateNumber}")
    public String editConcates(EditConcateForm editConcateForm, Model model, @PathVariable(name = "ConcateNumber") long concateNumber) {
        return adminService.editConcates(editConcateForm, model, concateNumber);
    }


    @GetMapping("/admin_recommend/editCondition/{conditionId}")
    public String editCondition(EditConditionForm editConditionForm, Model model, @PathVariable Long conditionId) {
        return adminService.editCondition(editConditionForm, model, conditionId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}")
    public String editConditions(EditConcateForm editConditonForm, Model model, @PathVariable Long conditionId) {
        return adminService.editConditions(editConditonForm, model, conditionId);
    }

    @GetMapping("/admin_recommend/editCondition/{conditionId}/editMenu/{conditionMenuId}")
    public String editConditionMenu(EditConditionForm editConditionForm, Model model,
                                    @PathVariable Long conditionId,
                                    @PathVariable Long conditionMenuId) {

        return adminService.editConditionMenu(editConditionForm, model, conditionId, conditionMenuId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}/editMenu/{conditionMenuId}")
    public String editConditionMenus(EditConditionForm editConditionForm, Model model,
                                     @PathVariable Long conditionId,
                                     @PathVariable Long conditionMenuId) {
        return adminService.editConditionMenus(editConditionForm, model, conditionId, conditionMenuId);
    }

    @GetMapping("/admin_recommend/editCondition/{conditionId}/delete/{conditionMenuId}")
    public String deleteConditionMenu(
            HttpServletRequest request,
            @PathVariable Long conditionId,
            @PathVariable Long conditionMenuId){
        return adminService.deleteConditionMenu(request, conditionId, conditionMenuId);
    }


    @GetMapping("/admin_recommend/editCondition/{conditionId}/addMenu")
    public String addConditionMenu(EditConditionForm editConditionForm, Model model,
                                   @PathVariable Long conditionId) {

        return adminService.addConditionMenu(editConditionForm, model, conditionId);
    }

    @PostMapping("/admin_recommend/editCondition/{conditionId}/addMenu")
    public String addConditionMenus(EditConditionForm editConditionForm, Model model,
                                    HttpServletRequest request,
                                    @PathVariable Long conditionId) {
        return adminService.addConditionMenus(editConditionForm, model, request, conditionId);
    }



}
