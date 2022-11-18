package team.project.WhatToEatToday.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.domain.ConditionCategory;
import team.project.WhatToEatToday.domain.Menu;

@Repository
@RequiredArgsConstructor
public class ConditionRepository {
	
	public final EntityManager em;

    public void save(Condition condition) {
        if(condition.getId() == null) {
            em.persist(condition);
        } else {
            em.merge(condition);
        }
    }
    
    
    public Condition findOne(Long id) {
        return em.find(Condition.class, id);
    }

 
    public List<Condition> findAll() {
        return em.createQuery("SELECT c FROM Condition c", Condition.class)
                .getResultList();
    }

   public List<Condition> findCate1(Long id){
	   return em.createQuery("SELECT c FROM Condition c where concate.id = :id", Condition.class )
			   .setParameter("id", id)
			   .getResultList();
   }

}
