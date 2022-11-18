package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.domain.member.Manager;
import team.project.WhatToEatToday.repository.MenuRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final EntityManager em;
    private final MenuRepository menuRepository;

    @Transactional
    public Long join(Menu menu) {
        menuRepository.save(menu);
        return menu.getId();
    }

    @Transactional
    public Long delete(Menu menu) {
        Long deletedMenuId = menu.getId();
        menuRepository.delete(menu);
        return deletedMenuId;
    }
    //회원 전체 조회
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findOne(Long menuId) {
        return menuRepository.findOne(menuId);
    }


    public List<Menu> findCategoryId(Long id){
        return menuRepository.findByCategoryId(id);
    }

    public List<Menu> findEatingHouseId(Long id){
        return menuRepository.findEatingHouse(id);
    }

    public Menu findCateId(Long id){
        return menuRepository.findByCateId(id);
    }

    public List<Menu> findByName(String name){
        return menuRepository.findByName(name);
    }

    public List<Menu> findByConditionId(Long id) { return menuRepository.findByConditionMenuId(id); }

    public List<Menu> findCrossMenuId(Long id) { return menuRepository.findCrossMenu(id); }


}
