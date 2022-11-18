package team.project.WhatToEatToday.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.ConditionCategory;

@Repository
@RequiredArgsConstructor
public class ConditionCategoryRepository {
	
	
	 public final EntityManager em;

	    public void save(ConditionCategory conditionCategory) {
	        if(conditionCategory.getId() == null) {
	            em.persist(conditionCategory);
	        } else {
	            em.merge(conditionCategory);
	        }
	    }

	    public ConditionCategory findOne(Long id) {
	        return em.find(ConditionCategory.class, id);
	    }

}
