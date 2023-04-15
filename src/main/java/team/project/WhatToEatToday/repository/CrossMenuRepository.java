package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.WhatToEatToday.domain.CrossMenu;

import javax.persistence.EntityManager;
import java.util.List;

public interface CrossMenuRepository extends JpaRepository<CrossMenu, Long> {

    @Query("SELECT m FROM CrossMenu m WHERE m.name like concat('%', :name, '%')")
    CrossMenu findByName(@Param("name") String name);

    @Query("SELECT m FROM CrossMenu m WHERE m.name like concat('%', :name, '%')")
    List<CrossMenu> findAllByName(@Param("name") String name);


}
