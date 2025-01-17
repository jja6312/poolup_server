package com.poolup.poolup.game.infrastructure;

import com.poolup.poolup.game.domain.MemberGameResult;
import org.springframework.data.jpa.repository.JpaRepository;

// 카드 레포지토리
public interface GameRepository extends JpaRepository<MemberGameResult, Long> {
}
