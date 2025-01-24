package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomJoinResponse {
    private String roomId;
    private String status;
    private Player player1P;
    private Player player2P;

    @Getter
    @Builder
    public static class Player {
        private Long memberId;
        private String name;
        private int score;
    }
}
