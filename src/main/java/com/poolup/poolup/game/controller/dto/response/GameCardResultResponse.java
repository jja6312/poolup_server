package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameCardResultResponse {
    private Long problemNumber; // 문제 번호
    private int score1P; // 1P 점수
    private int score2P; // 2P 점수
}