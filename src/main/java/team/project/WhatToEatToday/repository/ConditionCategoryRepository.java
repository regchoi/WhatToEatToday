package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.WhatToEatToday.domain.ConditionCategory;


public interface ConditionCategoryRepository extends JpaRepository<ConditionCategory, Long> {

}
