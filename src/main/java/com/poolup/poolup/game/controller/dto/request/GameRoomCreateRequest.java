package com.poolup.poolup.game.controller.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomCreateRequest {
    private Long memberId;
}
