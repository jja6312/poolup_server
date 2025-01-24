package com.poolup.poolup.game.controller.dto.request;

import lombok.Builder;
import lombok.Getter;

// 문제 정답 한 쌍을 선택한 경우, 해당 문제번호가 서버로 들어오게 된다.
@Getter
@Builder
public class GameCardSelectionRequest {
    private String roomId; // 방 ID
    private Long playerId; // 플레이어 ID
    private Long problemNumber; // 선택된 문제 번호
}
