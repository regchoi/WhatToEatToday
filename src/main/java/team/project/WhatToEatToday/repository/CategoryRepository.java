package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.WhatToEatToday.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
