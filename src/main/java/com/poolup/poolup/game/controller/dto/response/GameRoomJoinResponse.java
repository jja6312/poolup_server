package com.poolup.poolup.game.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomJoinResponse {
    private final String message;
}
