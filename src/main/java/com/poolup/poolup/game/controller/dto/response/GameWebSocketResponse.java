package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameWebSocketResponse {
    private String roomId;   // 방 ID
    private String status;   // 방 상태 (READY/START 등)
    private Player player1P;
    private Player player2P;

    @Getter
    @Builder
    public static class Player{
        private Long memberId;
        private String name;
    }
}
