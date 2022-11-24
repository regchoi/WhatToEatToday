package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.WhatToEatToday.domain.Menu;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m FROM Menu m where categorys.id = :id")
    List<Menu> findAllByCategoryId(@Param("id") Long id);

    @Query("select m from Menu m where m.name like concat('%', :name, '%')")
    List<Menu> findAllByName(@Param("name") String name);

}
