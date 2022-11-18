package team.project.WhatToEatToday.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.CrossMenu;
import team.project.WhatToEatToday.domain.Menu;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrossMenuRepository {
	
	public final EntityManager em;

    public void save(CrossMenu crossMenu) {
        if(crossMenu.getId() == null) {
            em.persist(crossMenu);
        } else {
            em.merge(crossMenu);
        }
    }

    public CrossMenu findOne(Long id) {
        return em.find(CrossMenu.class, id);
    }

 
    public List<CrossMenu> findAll() {
        return em.createQuery("SELECT c FROM CrossMenu c", CrossMenu.class)
                .getResultList();
    }

   public List<CrossMenu> findOneByConditionId(Long id){
	   return em.createQuery("SELECT c FROM CrossMenu c where condition.id = :id", CrossMenu.class )
			   .setParameter("id", id)
			   .getResultList();
   }
    public CrossMenu findByName(String name){
       return em.createQuery("select m from CrossMenu m where m.name like concat('%', :name, '%')", CrossMenu.class)
               .setParameter("name", name)
               .getSingleResult();
    }
    public List<CrossMenu> findNewByName(String name){
        return em.createQuery("select m from CrossMenu m where m.name like concat('%', :name, '%')", CrossMenu.class)
                .setParameter("name", name)
                .getResultList();
    }

//    public List<ConditionMenu> findOneByConditionId(Long id){
//        return em.createQuery("SELECT c FROM Condition c where condition.id = :id", ConditionMenu.class )
//                .setParameter("id", id)
//                .getResultList();
//    }

}
