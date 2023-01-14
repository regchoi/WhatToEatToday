package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import team.project.WhatToEatToday.domain.*;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.dto.EatingHouseForm;
import team.project.WhatToEatToday.dto.MenuForm;
import team.project.WhatToEatToday.file.FileStore;
import team.project.WhatToEatToday.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ManagerService {
    private final MembersRepository memberRepository;
    private final EatingHouseRepository eatingHouseRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final CrossMenuRepository crossMenuRepository;
    private final FileStore fileStore;

    public String getManager(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        try {
            Member member = (Member) session.getAttribute("member");
            Manager manager = (Manager) memberRepository.findById(member.getId()).orElseThrow();
            model.addAttribute("page", "manager");
            model.addAttribute("eatingHouses", manager.getEatingHouses());
            return "layout";
        } catch (Exception e){
            log.error("error:",e);
            session.setAttribute("message", "유저 정보가 올바르지 않습니다.");
            return "redirect:/logout";
        }
    }

    public String getAddEatingHouse(Model model) {
        model.addAttribute("page", "addEatingHouse");
        model.addAttribute("eatingHouseForm", new EatingHouseForm());
        return "layout";
    }

    @Transactional
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



            UploadFile file = fileStore.storeFile(eatingHouseForm.getMultipartFile());
            String uploadFile = file.getUploadFileName();
            String storeFile = file.getStoreFileName();
            eatingHouse.setUploadEatingHouseFileName(uploadFile);
            eatingHouse.setStoreEatingHouseFileName(storeFile);


            eatingHouseRepository.save(eatingHouse);
            session.setAttribute("message", "매장 등록 성공");
            return "redirect:/manager/eating_house";
        } catch (Exception e) {
            log.error("error:",e);
            session.setAttribute("message", "매장 등록 실패");
            return "redirect:/manager/eating_house/add";
        }
    }

    public String getEatingHouseEdit(@PathVariable Long eatingHouseId, Model model, EatingHouseForm eatingHouseForm) {
        model.addAttribute("page", "editEatingHouse");
        model.addAttribute("eatingHouseForm", eatingHouseForm);
        model.addAttribute("eatingHouse", eatingHouseRepository.getById(eatingHouseId));
        return "layout";
    }

    @Transactional
    public String postEatingHouseEdit(@PathVariable Long eatingHouseId, @Valid EatingHouseForm eatingHouseForm) throws IOException {
        EatingHouse eatingHouse = eatingHouseRepository.getById(eatingHouseId);

        UploadFile file = fileStore.storeFile(eatingHouseForm.getMultipartFile());
        String uploadFile = file.getUploadFileName();
        String storeFile = file.getStoreFileName();
        eatingHouse.setUploadEatingHouseFileName(uploadFile);
        eatingHouse.setStoreEatingHouseFileName(storeFile);

        eatingHouse.setName(eatingHouseForm.getName());
        eatingHouse.setDescription(eatingHouseForm.getDescription());
        eatingHouse.setAddress(eatingHouseForm.getAddress());
        eatingHouse.setAddressDetail(eatingHouseForm.getAddressDetail());
        eatingHouseRepository.save(eatingHouse);
        return "redirect:/manager/eating_house";
    }

    @Transactional
    public String deleteEatingHouseDetail(HttpServletRequest request, @PathVariable Long eatingHouseId) {
        HttpSession session = request.getSession();
        session.setAttribute("message", "삭제완료");
        EatingHouse eatingHouse = eatingHouseRepository.getById(eatingHouseId);
        eatingHouseRepository.delete(eatingHouse);
        return "redirect:/manager/eating_house";
    }

    public String getAddMenu(@PathVariable Long eatingHouseId, Model model, MenuForm menuForm){
        model.addAttribute("page", "addMenu");
        model.addAttribute("menuForm", menuForm);
        List<Category> categoryList = categoryService.findCategoryExOne();
        model.addAttribute("cate", categoryList);
        model.addAttribute("eatingHouse", eatingHouseRepository.getById(eatingHouseId));
        return "layout";
    }

    @Transactional
    public String postAddMenu(HttpServletRequest request, @PathVariable Long eatingHouseId, @Valid MenuForm menuForm){
        HttpSession session = request.getSession();
        Long checkId = 123456789L;
        try {
            Menu menu = new Menu();
            menu.setName(menuForm.getName());
            menu.setPrice(menuForm.getPrice());
            menu.setCategorys(categoryRepository.findById(menuForm.getCategory()).orElseThrow());
            menu.setEatingHouse(eatingHouseRepository.getById(eatingHouseId));

            UploadFile file = fileStore.storeFile(menuForm.getMultipartFile());
            String uploadFile = file.getUploadFileName();
            String storeFile = file.getStoreFileName();
            menu.setUploadMenuFileName(uploadFile);
            menu.setStoreMenuFileName(storeFile);

            menuRepository.save(menu);

            List<CrossMenu> checkCrossMenu = crossMenuRepository.findAll();
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
                menu.setCrossMenu(crossMenuRepository.findById(checkId).orElseThrow());
                menuRepository.save(menu);
            } else if(checkId==123456789L) {
                menu.setCrossMenu(crossMenuRepository.findByName("기타"));
                menuRepository.save(menu);
            }
            session.setAttribute("message", "메뉴추가");
            return "redirect:/manager/eating_house/edit/" + eatingHouseId;
        } catch (Exception e) {
            log.error("error:",e);
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
        Category category = categoryRepository.findById(menu.getCategorys().getId()).orElseThrow();
        model.addAttribute("cateid", category.getId());
        model.addAttribute("menu", menuRepository.findById(menuId).orElseThrow());
        model.addAttribute("eatingHouse", eatingHouseRepository.findById(eatingHouseId).orElseThrow());
        return "layout";
    }

    @Transactional
    public String postEditMenu(HttpServletRequest request, @PathVariable Long eatingHouseId,
                               @PathVariable Long menuId, @Valid MenuForm menuForm) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("message", "메뉴수정");
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        menu.setName(menuForm.getName());
        menu.setPrice(menuForm.getPrice());
        menu.setCategorys(categoryRepository.findById(menuForm.getCategory()).orElseThrow());

        UploadFile file = fileStore.storeFile(menuForm.getMultipartFile());
        String uploadFile = file.getUploadFileName();
        String storeFile = file.getStoreFileName();
        menu.setUploadMenuFileName(uploadFile);
        menu.setStoreMenuFileName(storeFile);

        menuRepository.save(menu);

        Long checkId = 123456789L;
        List<CrossMenu> checkCrossMenu = crossMenuRepository.findAll();
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
            menu.setCrossMenu(crossMenuRepository.findById(checkId).orElseThrow());
            menuRepository.save(menu);
        } else if(checkId == 123456789L) {
            menu.setCrossMenu(crossMenuRepository.findByName("기타"));
            menuRepository.save(menu);
        }

        return "redirect:/manager/eating_house/edit/" + eatingHouseId;
    }

    @Transactional
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
