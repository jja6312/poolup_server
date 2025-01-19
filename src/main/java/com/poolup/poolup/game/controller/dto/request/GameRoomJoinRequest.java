package com.poolup.poolup.game.controller.dto.request;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomJoinRequest {
    private Long memberId;
    private String roomId;
}
