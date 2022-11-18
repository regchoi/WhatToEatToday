package team.project.WhatToEatToday.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.Menu;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MenuRepository {
    public final EntityManager em;

    public void save(Menu menu) {
        if(menu.getId() == null) {
            em.persist(menu);
        } else {
            em.merge(menu);
        }
    }

    public void delete(Menu menu) {
        em.remove(menu);
    }

    public Menu findOne(Long id) {
        return em.find(Menu.class, id);
    }

    public List<Menu> findAll() {
        return em.createQuery("SELECT m FROM Menu m", Menu.class)
                .getResultList();
    }
    public List<Menu> findById(Long id) {
        return em.createQuery("SELECT m FROM Menu m where m.id = :Id", Menu.class)
                .setParameter("Id", id)
                .getResultList();
    }


    public List<Menu> findByCategoryId(Long id){
        return em.createQuery("SELECT m FROM Menu m where categorys.id = :id", Menu.class)
                .setParameter("id", id)
                .getResultList();

    }

    public List<Menu> findEatingHouse(Long id){
        return em.createQuery("SELECT m FROM Menu m where eatingHouse.id = :id", Menu.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Menu> findCrossMenu(Long id){
        return em.createQuery("SELECT m FROM Menu m where crossMenu.id = :id", Menu.class)
                .setParameter("id", id)
                .getResultList();
    }


    //List로 안받기
    public Menu findByCateId(Long id){
        return em.createQuery("SELECT m FROM Menu m where categorys.id = :id", Menu.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public List<Menu> findByName(String name){
        return em.createQuery("select m from Menu m where m.name like concat('%', :name, '%')", Menu.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Menu> findByConditionMenuId(Long id){
        return em.createQuery("select m from Menu m where conditionMenu.id = :id", Menu.class)
                .setParameter("id", id)
                .getResultList();
    }

}
