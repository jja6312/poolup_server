package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameWebSocketResponse {
    private String roomId;   // 방 ID
    private String status;   // 방 상태 (READY/START 등)
}
