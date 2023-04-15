package team.project.WhatToEatToday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.WhatToEatToday.domain.member.Member;

public interface MembersRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.loginId= :loginid")
    Member findOneByLoginId(@Param("loginid") String id);

    Boolean existsByLoginId(String id);
}
