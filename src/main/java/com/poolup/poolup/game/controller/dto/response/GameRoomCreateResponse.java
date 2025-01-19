package com.poolup.poolup.game.controller.dto.response;

import com.poolup.poolup.game.domain.model.RoomStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomCreateResponse {
    private final String roomId;
    private final RoomStatus roomStatus;
    private final Player1P player1P;

    @Getter
    @Builder
    public static class Player1P {
        private final Long memberId;
        private final String name;
    }
}