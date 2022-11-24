package team.project.WhatToEatToday.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.domain.ConditionCategory;
import team.project.WhatToEatToday.domain.Menu;


public interface ConditionRepository extends JpaRepository<Condition, Long> {

    @Query("SELECT c FROM Condition c where concate.id = :id")
   public List<Condition> findAllByConditionCategoryId(@Param("id") Long id);

}
