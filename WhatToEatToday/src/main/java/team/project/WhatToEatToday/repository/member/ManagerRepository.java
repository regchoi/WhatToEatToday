package team.project.WhatToEatToday.repository.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.member.Customer;
import team.project.WhatToEatToday.domain.member.Manager;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {
    public final EntityManager em;

    public void save(Manager manager) {
        if(manager.getId() == null) {
            em.persist(manager);
        } else {
            em.merge(manager);
        }
    }
    public Manager findOne(String id) {
        return em.find(Manager.class, id);
    }

    public List<Manager> findAll() {
        return em.createQuery("SELECT m FROM Manager m", Manager.class)
                .getResultList();
    }

    public List<Manager> findById(String id) {
        return em.createQuery("SELECT m FROM Manager m where m.id = :id", Manager.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void delete(Manager manager) {
        em.remove(manager);
    }
}
