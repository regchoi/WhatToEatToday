package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.WhatToEatToday.domain.ConditionMenu;

import java.util.List;


public interface ConditionMenuRepository extends JpaRepository<ConditionMenu, Long> {
    @Query("SELECT c FROM ConditionMenu c WHERE condition.id = :id")
   List<ConditionMenu> findAllByConditionId(@Param("id") Long id);
    @Query("SELECT m FROM ConditionMenu m WHERE m.name like concat('%', :name, '%')")
    ConditionMenu findByName(@Param("name") String name);


}
