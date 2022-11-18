package team.project.WhatToEatToday.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.domain.Menu;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EatingHouseRepository {
    private final EntityManager em;

    public void save(EatingHouse eatingHouse){
        if(eatingHouse.getId() == null) {
            em.persist(eatingHouse);
        } else {
            em.merge(eatingHouse);
        }
    }

    public EatingHouse findOne(Long id) {
        return em.find(EatingHouse.class, id);
    }

    public List<EatingHouse> findAll() {
        return em.createQuery("SELECT e FROM EatingHouse e", EatingHouse.class)
                .getResultList();
    }

    public List<EatingHouse> findById(Long id) {
        return em.createQuery("SELECT e FROM EatingHouse e where e.id = :id", EatingHouse.class)
                .setParameter("id", id)
                .getResultList();
    }


    public void delete(EatingHouse eatingHouse){
        List<Menu> menus = eatingHouse.getMenus();
        for(Menu menu : menus){
            em.remove(menu);
        }
        em.remove(eatingHouse);
    }
}
