package team.project.WhatToEatToday.repository.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.WhatToEatToday.domain.member.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryss {
    public final EntityManager em;

    public Member findOne(String id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public List<Member> findById(String id) {
        return em.createQuery("SELECT m FROM Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

}
