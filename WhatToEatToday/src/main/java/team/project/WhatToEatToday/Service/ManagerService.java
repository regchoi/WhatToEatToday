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
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EatingHouseForm;
import team.project.WhatToEatToday.dto.MenuForm;
import team.project.WhatToEatToday.repository.member.ManagerRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final EatingHouseService eatingHouseService;
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final CrossMenuService crossMenuService;


    @Transactional
    public String join(Manager manager) {
        validateDuplicateAdmin(manager);
        managerRepository.save(manager);
        return manager.getId();
    }

    private void validateDuplicateAdmin(Manager manager) {
        List<Manager> findAdmins = managerRepository.findById(manager.getId());
        if(!findAdmins.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    //회원 전체 조회
    public List<Manager> findManagers() {
        return managerRepository.findAll();
    }

    public Manager findOne(String managerId) {
        return managerRepository.findOne(managerId);
    }

    @Transactional
    public String delete(Manager manager) {
        String deletedManagerId = manager.getId();
        managerRepository.delete(manager);
        return deletedManagerId;
    }

    public String getManager(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        try {
            Member member = (Member) session.getAttribute("member");
            Manager manager = managerRepository.findOne(member.getId());
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
            Manager manager = findOne(member.getId());
            eatingHouse.setManager(manager);
            eatingHouse.setAddress(eatingHouseForm.getAddress());
            eatingHouse.setAddressDetail(eatingHouseForm.getAddressDetail());
            eatingHouseService.join(eatingHouse);
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
            menuService.join(menu);

            List<CrossMenu> checkCrossMenu = crossMenuService.findAll();
            for(int i=0; i<checkCrossMenu.size(); i++){
                String checkMenu = checkCrossMenu.get(i).getName();
                int j = menuService.findByName(checkMenu).size();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");//
                System.out.println(checkCrossMenu.get(i).getName());
                System.out.println(j);
                System.out.println(checkCrossMenu.size());
                System.out.println(menuService.findByName(checkMenu));
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if(!(j==0)){
                    if(menu.getId().equals(menuService.findByName(checkMenu).get(j-1).getId())){
                        checkId = checkCrossMenu.get(i).getId();
                        break;
                    }
                }

            }

            System.out.println("========================================");
            System.out.println(menu.getId());
            System.out.println(checkId);
            System.out.println(checkId.toString().isEmpty());
            System.out.println(checkId.toString().isBlank());
            System.out.println("========================================");

            if(!(checkId==123456789L)){
                menu.setCrossMenu(crossMenuService.findOne(checkId));
                System.out.println("========================================");
                System.out.println(menu.getCrossMenu());
                System.out.println("========================================");
                menuService.join(menu);
            }else if(checkId==123456789L){
                menu.setCrossMenu(crossMenuService.findByName("기타"));
//                CrossMenu crossMenu = new CrossMenu();
//                crossMenu.setName(menuForm.getName());
//                menu.setCrossMenu(crossMenu);
//                crossMenuService.save(crossMenu);
                menuService.join(menu);
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
        Menu menu = menuService.findOne(menuId);
        Category category = categoryService.findOne(menu.getCategorys().getId());
        model.addAttribute("cateid", category.getId());
        model.addAttribute("menu", menuService.findOne(menuId));
        model.addAttribute("eatingHouse", eatingHouseService.findOne(eatingHouseId));
        return "layout";
    }

    public String postEditMenu(HttpServletRequest request, @PathVariable Long eatingHouseId,
                               @PathVariable Long menuId, @Valid MenuForm menuForm) {
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴수정");
        Menu menu = menuService.findOne(menuId);
        menu.setName(menuForm.getName());
        menu.setPrice(menuForm.getPrice());
        menu.setCategorys(categoryService.findOne(menuForm.getCategory()));
        menuService.join(menu);

        Long checkId = 123456789L;
        List<CrossMenu> checkCrossMenu = crossMenuService.findAll();
        for (int i = 0; i < checkCrossMenu.size(); i++) {
            String checkMenu = checkCrossMenu.get(i).getName();
            int j = menuService.findByName(checkMenu).size();
            if (!(j == 0)) {
                if (menu.getId().equals(menuService.findByName(checkMenu).get(j - 1).getId())) {
                    checkId = checkCrossMenu.get(i).getId();
                    break;
                }
            }
        }
        if (!(checkId == 123456789L)) {
            menu.setCrossMenu(crossMenuService.findOne(checkId));
            menuService.join(menu);
        } else if(checkId == 123456789L) {
            menu.setCrossMenu(crossMenuService.findByName("기타"));
            menuService.join(menu);
        }

        return "redirect:/manager/eating_house/edit/" + eatingHouseId;
    }

    public String deleteMenu(
            HttpServletRequest request,
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId){
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴삭제");
        Menu menu = menuService.findOne(menuId);
        menuService.delete(menu);
        return "redirect:/manager/eating_house/edit/" + eatingHouseId;
    }
}
