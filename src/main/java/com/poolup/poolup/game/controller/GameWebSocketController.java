package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.application.GameService;
import com.poolup.poolup.game.controller.dto.request.GameWebSocketRequest;
import com.poolup.poolup.game.controller.dto.response.GameRoomCreateResponse;
import com.poolup.poolup.game.controller.dto.response.GameRoomJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {
    private final GameService gameService;

    @MessageMapping("/room/create") // 클라이언트에서 방 생성 메시지를 처리
    @SendTo("/topic/room-status")  // /topic/room-status를 구독한 모든 클라이언트에게 브로드캐스팅
    public GameRoomCreateResponse handleRoomCreate(GameWebSocketRequest request) {
        System.out.println("방 생성 요청: " + request);
        return gameService.createRoom(request.getPlayerId());
    }

    @MessageMapping("/room/join") // 클라이언트에서 방 참가 메시지를 처리
    @SendTo("/topic/room-status")  // /topic/room-status를 구독한 모든 클라이언트에게 브로드캐스팅
    public GameRoomJoinResponse handleRoomJoin(GameWebSocketRequest request) {
        System.out.println("방 참가 요청: " + request);
        return gameService.joinRoom(request.getRoomId(), request.getPlayerId());
    }
}
