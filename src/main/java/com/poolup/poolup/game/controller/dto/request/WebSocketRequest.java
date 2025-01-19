package com.poolup.poolup.game.controller.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebSocketRequest {
    private String roomId;      // 방 ID(==UUID)
    private String playerId;    // 플레이어 ID(2P)
    private String playerName;  // 플레이어 이름
}
