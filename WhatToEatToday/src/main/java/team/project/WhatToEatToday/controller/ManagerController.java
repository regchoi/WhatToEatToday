package team.project.WhatToEatToday.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.project.WhatToEatToday.Service.ManagerService;
import team.project.WhatToEatToday.dto.EatingHouseForm;
import team.project.WhatToEatToday.dto.MenuForm;
import team.project.WhatToEatToday.file.FileStore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final FileStore fileStore;

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
    public String postEatingHouseDetail(@PathVariable Long eatingHouseId, @Valid EatingHouseForm eatingHouseForm) throws IOException {
        return managerService.postEatingHouseEdit(eatingHouseId, eatingHouseForm);
    }

    @GetMapping("/eating_house/delete/{eatingHouseId}")
    public String deleteEatingHouseDetail(HttpServletRequest request, @PathVariable Long eatingHouseId) {
        return managerService.deleteEatingHouseDetail(request, eatingHouseId);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/add")
    public String getAddMenu(@PathVariable Long eatingHouseId, Model model, MenuForm menuForm) {
        return managerService.getAddMenu(eatingHouseId, model, menuForm);
    }

    @PostMapping("/eating_house/edit/{eatingHouseId}/menu/add")
    public String postAddMenu(HttpServletRequest request, @PathVariable Long eatingHouseId, @Valid MenuForm menuForm) {
        return managerService.postAddMenu(request, eatingHouseId, menuForm);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/edit/{menuId}")
    public String getEditMenu(
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId, Model model, MenuForm menuForm) {
        return managerService.getEditMenu(eatingHouseId, menuId, model, menuForm);
    }

    @PostMapping("/eating_house/edit/{eatingHouseId}/menu/edit/{menuId}")
    public String postEditMenu(HttpServletRequest request, @PathVariable Long eatingHouseId,
                               @PathVariable Long menuId, @Valid MenuForm menuForm) throws IOException {
        return managerService.postEditMenu(request, eatingHouseId, menuId, menuForm);
    }

    @GetMapping("/eating_house/edit/{eatingHouseId}/menu/delete/{menuId}")
    public String deleteMenu(
            HttpServletRequest request,
            @PathVariable Long eatingHouseId,
            @PathVariable Long menuId) {
        return managerService.deleteMenu(request, eatingHouseId, menuId);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        System.out.println(fileStore.getFullPath(filename));
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @ResponseBody
    @GetMapping("/images/store")
    public Resource defaultStoreImage() throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFileDefaultDir() + "default/가게.png");
    }
    @ResponseBody
    @GetMapping("/images/menu")
    public Resource defaultMenuImage() throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFileDefaultDir() + "default/음식.png");
    }

}
