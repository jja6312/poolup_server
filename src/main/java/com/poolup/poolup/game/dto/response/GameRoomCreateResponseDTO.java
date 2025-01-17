package com.poolup.poolup.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomCreateResponseDTO {
    private final String roomId;
}
