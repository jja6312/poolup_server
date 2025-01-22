package com.poolup.poolup.game.infrastructure;

import com.poolup.poolup.problem.domain.Problem;
import com.poolup.poolup.problem.enums.ProblemType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemporaryProblemRepository extends JpaRepository<Problem,Long> {
    List<Problem> findByProblemTypeOrderByCreatedAtDesc(ProblemType problemType, Pageable pageable);

}
