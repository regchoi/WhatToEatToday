package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.CrossMenu;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EatingHouseForm;
import team.project.WhatToEatToday.dto.MenuForm;
import team.project.WhatToEatToday.repository.EatingHouseRepository;
import team.project.WhatToEatToday.repository.MemberRepository;
import team.project.WhatToEatToday.repository.MenuRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {
    private final MemberRepository memberRepository;
    private final EatingHouseRepository eatingHouseRepository;
    private final EatingHouseService eatingHouseService;
    private final CategoryService categoryService;
    private final MenuRepository menuRepository;
    private final CrossMenuService crossMenuService;

    public String getManager(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        try {
            Member member = (Member) session.getAttribute("member");
            Manager manager = (Manager) memberRepository.findById(member.getId()).orElseThrow();
            model.addAttribute("page", "manager");
            model.addAttribute("eatingHouses", manager.getEatingHouses());
            return "layout";
        } catch (Exception e){
            session.setAttribute("message", "유저 정보가 올바르지 않습니다.");
            return "redirect:/logout";
        }
    }

    public String getAddEatingHouse(Model model) {
        model.addAttribute("page", "addEatingHouse");
        model.addAttribute("eatingHouseForm", new EatingHouseForm());
        return "layout";
    }

    public String postAddEatingHouse(HttpServletRequest request, @Valid EatingHouseForm eatingHouseForm) {
        HttpSession session = request.getSession();
        try {
            EatingHouse eatingHouse = new EatingHouse();
            eatingHouse.setName(eatingHouseForm.getName());
            eatingHouse.setDescription(eatingHouseForm.getDescription());
            Member member = (Member) session.getAttribute("member");
            Manager manager = (Manager) memberRepository.findById(member.getId()).orElseThrow();
            eatingHouse.setManager(manager);
            eatingHouse.setAddress(eatingHouseForm.getAddress());
            eatingHouse.setAddressDetail(eatingHouseForm.getAddressDetail());
            eatingHouseRepository.save(eatingHouse);
            session.setAttribute("message", "매장 등록 성공");
            return "redirect:/manager/eating_house";
        } catch (Exception e) {
            session.setAttribute("message", "매장 등록 실패");
            return "redirect:/manager/eating_house/add";
        }
    }

    public String getEatingHouseEdit(@PathVariable Long eatingHouseId, Model model, EatingHouseForm eatingHouseForm) {
        model.addAttribute("page", "editEatingHouse");
        model.addAttribute("eatingHouseForm", eatingHouseForm);
        model.addAttribute("eatingHouse", eatingHouseService.findOne(eatingHouseId));
        return "layout";
    }

    public String postEatingHouseDetail(@PathVariable Long eatingHouseId, @Valid EatingHouseForm eatingHouseForm) {
        EatingHouse eatingHouse = eatingHouseService.findOne(eatingHouseId);
        eatingHouse.setName(eatingHouseForm.getName());
        eatingHouse.setDescription(eatingHouseForm.getDescription());
        eatingHouse.setAddress(eatingHouseForm.getAddress());
        eatingHouse.setAddressDetail(eatingHouseForm.getAddressDetail());
        eatingHouseService.join(eatingHouse);
        return "redirect:/manager/eating_house";
    }

    public String deleteEatingHouseDetail(HttpServletRequest request, @PathVariable Long eatingHouseId) {
        HttpSession session = request.getSession();
        session.setAttribute("message", "삭제완료");
        EatingHouse eatingHouse = eatingHouseService.findOne(eatingHouseId);
        eatingHouseService.delete(eatingHouse);
        return "redirect:/manager/eating_house";
    }

    public String getAddMenu(@PathVariable Long eatingHouseId, Model model, MenuForm menuForm){
        model.addAttribute("page", "addMenu");
        model.addAttribute("menuForm", menuForm);
        List<Category> categoryList = categoryService.findCategoryExOne();
        model.addAttribute("cate", categoryList);
        model.addAttribute("eatingHouse", eatingHouseService.findOne(eatingHouseId));
        return "layout";
    }

    public String postAddMenu(HttpServletRequest request, @PathVariable Long eatingHouseId, @Valid MenuForm menuForm){
        HttpSession session = request.getSession();
        Long checkId = 123456789L;
        try {
            Menu menu = new Menu();
            menu.setName(menuForm.getName());
            menu.setPrice(menuForm.getPrice());
            menu.setCategorys(categoryService.findOne(menuForm.getCategory()));
            menu.setEatingHouse(eatingHouseService.findOne(eatingHouseId));
            menuRepository.save(menu);

            List<CrossMenu> checkCrossMenu = crossMenuService.findAll();
            for(int i=0; i<checkCrossMenu.size(); i++){
                String checkMenu = checkCrossMenu.get(i).getName();
                int j = menuRepository.findAllByName(checkMenu).size();
                if(!(j==0)){
                    if(menu.getId().equals(menuRepository.findAllByName(checkMenu).get(j-1).getId())){
                        checkId = checkCrossMenu.get(i).getId();
                        break;
                    }
                }
            }

            if(!(checkId==123456789L)){
                menu.setCrossMenu(crossMenuService.findOne(checkId));
                menuRepository.save(menu);
            } else if(checkId==123456789L) {
                menu.setCrossMenu(crossMenuService.findByName("기타"));
                menuRepository.save(menu);
            }
            session.setAttribute("message", "메뉴추가");
            return "redirect:/manager/eating_house/edit/" + eatingHouseId;
        }
        catch (Exception e){
            session.setAttribute("message", "카테고리를 입력하여주세요.");
            return "redirect:/manager/eating_house/edit/" + eatingHouseId;
        }
    }

    public String getEditMenu(
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId, Model model, MenuForm menuForm){
        model.addAttribute("page", "editMenu");
        model.addAttribute("menuForm", menuForm);
        List<Category> categoryList = categoryService.findCategoryExOne();
        model.addAttribute("cate", categoryList);
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        Category category = categoryService.findOne(menu.getCategorys().getId());
        model.addAttribute("cateid", category.getId());
        model.addAttribute("menu", menuRepository.findById(menuId).orElseThrow());
        model.addAttribute("eatingHouse", eatingHouseService.findOne(eatingHouseId));
        return "layout";
    }

    public String postEditMenu(HttpServletRequest request, @PathVariable Long eatingHouseId,
                               @PathVariable Long menuId, @Valid MenuForm menuForm) {
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴수정");
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        menu.setName(menuForm.getName());
        menu.setPrice(menuForm.getPrice());
        menu.setCategorys(categoryService.findOne(menuForm.getCategory()));
        menuRepository.save(menu);

        Long checkId = 123456789L;
        List<CrossMenu> checkCrossMenu = crossMenuService.findAll();
        for (int i = 0; i < checkCrossMenu.size(); i++) {
            String checkMenu = checkCrossMenu.get(i).getName();
            int j = menuRepository.findAllByName(checkMenu).size();
            if (!(j == 0)) {
                if (menu.getId().equals(menuRepository.findAllByName(checkMenu).get(j - 1).getId())) {
                    checkId = checkCrossMenu.get(i).getId();
                    break;
                }
            }
        }
        if (!(checkId == 123456789L)) {
            menu.setCrossMenu(crossMenuService.findOne(checkId));
            menuRepository.save(menu);
        } else if(checkId == 123456789L) {
            menu.setCrossMenu(crossMenuService.findByName("기타"));
            menuRepository.save(menu);
        }

        return "redirect:/manager/eating_house/edit/" + eatingHouseId;
    }

    public String deleteMenu(
            HttpServletRequest request,
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId){
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴삭제");
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        menuRepository.delete(menu);
        return "redirect:/manager/eating_house/edit/" + eatingHouseId;
    }
}
