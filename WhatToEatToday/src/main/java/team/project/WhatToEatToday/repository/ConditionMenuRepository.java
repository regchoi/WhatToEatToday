package team.project.WhatToEatToday.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.domain.ConditionMenu;
import team.project.WhatToEatToday.domain.Menu;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConditionMenuRepository {
	
	public final EntityManager em;

    public void save(ConditionMenu conditionMenu) {
        if(conditionMenu.getId() == null) {
            em.persist(conditionMenu);
        } else {
            em.merge(conditionMenu);
        }
    }

    public void delete(ConditionMenu conditionMenu) {
        em.remove(conditionMenu);
    }


    public ConditionMenu findOne(Long id) {
        return em.find(ConditionMenu.class, id);
    }

    public List<ConditionMenu> findOneList(Long id) {
        return em.createQuery("select c from ConditionMenu c where c.id = :id", ConditionMenu.class)
                .setParameter("id", id)
                .getResultList();
    }


    public List<ConditionMenu> findAll() {
        return em.createQuery("SELECT c FROM ConditionMenu c", ConditionMenu.class)
                .getResultList();
    }

   public List<ConditionMenu> findOneByConditionId(Long id){
	   return em.createQuery("SELECT c FROM ConditionMenu c where condition.id = :id", ConditionMenu.class )
			   .setParameter("id", id)
			   .getResultList();
   }
    public ConditionMenu findByName(String name){
        return em.createQuery("select m from ConditionMenu m where m.name like concat('%', :name, '%')", ConditionMenu.class)
                .setParameter("name", name)
                .getSingleResult();
    }

//    public List<ConditionMenu> findOneByConditionId(Long id){
//        return em.createQuery("SELECT c FROM Condition c where condition.id = :id", ConditionMenu.class )
//                .setParameter("id", id)
//                .getResultList();
//    }

}
