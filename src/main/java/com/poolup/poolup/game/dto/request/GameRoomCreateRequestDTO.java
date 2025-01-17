package com.poolup.poolup.game.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomCreateRequestDTO {
    private Long memberId;
}
