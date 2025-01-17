package com.poolup.poolup.game.infrastructure;

import com.poolup.poolup.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// 임시 로그인 레포지토리
public interface TemporaryMemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
