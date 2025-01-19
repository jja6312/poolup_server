package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebSocketResponse {
    private String roomId;   // 방 ID
    private String status;   // 방 상태 (READY/START 등)
    private String message;  // 클라이언트로 보낼 메시지
}
