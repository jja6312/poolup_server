package com.poolup.poolup.game.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class GameWebSocketRequest {
    private String roomId;      // 방 ID(==UUID)
    private Long playerId;    // 플레이어 ID(2P)
}
