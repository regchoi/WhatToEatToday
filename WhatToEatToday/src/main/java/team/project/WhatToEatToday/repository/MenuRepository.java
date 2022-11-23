package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.WhatToEatToday.domain.Menu;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {

//    public List<Menu> findAll() {
//        return em.createQuery("SELECT m FROM Menu m", Menu.class)
//                .getResultList();
//    }
//    public List<Menu> findById(Long id) {
//        return em.createQuery("SELECT m FROM Menu m where m.id = :Id", Menu.class)
//                .setParameter("Id", id)
//                .getResultList();
//    }
//
//
//    public List<Menu> findByCategoryId(Long id){
//        return em.createQuery("SELECT m FROM Menu m where categorys.id = :id", Menu.class)
//                .setParameter("id", id)
//                .getResultList();
//
//    }

    @Query("SELECT m FROM Menu m where categorys.id = :id")
    List<Menu> findAllByCategoryId(@Param("id") Long id);

    @Query("select m from Menu m where m.name like concat('%', :name, '%')")
    List<Menu> findAllByName(@Param("name") String name);

//    public List<Menu> findEatingHouse(Long id){
//        return em.createQuery("SELECT m FROM Menu m where eatingHouse.id = :id", Menu.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
//
//    public List<Menu> findCrossMenu(Long id){
//        return em.createQuery("SELECT m FROM Menu m where crossMenu.id = :id", Menu.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
//
//
//    //List로 안받기
//    public Menu findByCateId(Long id){
//        return em.createQuery("SELECT m FROM Menu m where categorys.id = :id", Menu.class)
//                .setParameter("id", id)
//                .getSingleResult();
//    }
//
//
//    public List<Menu> findByName(String name){
//        return em.createQuery("select m from Menu m where m.name like concat('%', :name, '%')", Menu.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//
//    public List<Menu> findByConditionMenuId(Long id){
//        return em.createQuery("select m from Menu m where conditionMenu.id = :id", Menu.class)
//                .setParameter("id", id)
//                .getResultList();
//    }

}
