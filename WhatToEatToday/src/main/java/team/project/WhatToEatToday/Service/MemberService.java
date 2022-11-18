package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.member.Member;
import team.project.WhatToEatToday.repository.member.MemberRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final EntityManager em;
    private final MemberRepository memberRepository;

    //회원 전체 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findOne(String memberId) {
        return memberRepository.findOne(memberId);
    }

}
