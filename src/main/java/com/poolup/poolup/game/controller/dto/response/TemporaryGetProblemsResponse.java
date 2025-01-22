package com.poolup.poolup.game.controller.dto.response;

import com.poolup.poolup.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TemporaryGetProblemsResponse {
    private List<ProblemCard> problemCards;

    @Getter
    @Builder
    public static class ProblemCard{
        private Long id;
        private String question;
        private String answer;
    }
}
