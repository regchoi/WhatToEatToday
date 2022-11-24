package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.repository.EatingHouseRepository;
import team.project.WhatToEatToday.repository.MenuRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EatingHouseService {

    private final EatingHouseRepository eatingHouseRepository;
    private final MenuRepository menuRepository;

    public String home(Model model) {
        log.info("home controller");
        model.addAttribute("page", "home");
        return "layout";
    }

    public String SearchResult(@RequestParam(required = false, value = "name") String text, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Menu> menuList = menuRepository.findAllByName(text);
        if(text.isBlank()){
            session.setAttribute("message", "해당 음식을 판매하는 매장이 없습니다");
            return "redirect:";
        }
        else if(menuList.isEmpty()){
            session.setAttribute("message", "해당 음식을 판매하는 매장이 없습니다");
            return "redirect:";
        }
        else {
            model.addAttribute("page", "researchMenuList");
            model.addAttribute("menu", menuList);
            return "layout";
        }
    }

}