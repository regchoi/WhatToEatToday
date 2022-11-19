package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.project.WhatToEatToday.Service.EatingHouseService;
import team.project.WhatToEatToday.Service.MenuService;
import team.project.WhatToEatToday.domain.Menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class EatingHouseController {
    private final EatingHouseService eatingHouseService;

    @GetMapping
    public String home(Model model) {
        return eatingHouseService.home(model);
    }

    @GetMapping("/search")
    public String SearchResult(@RequestParam(required = false, value = "name") String text, Model model, HttpServletRequest request){
        return eatingHouseService.SearchResult(text, model, request);
    }


}
