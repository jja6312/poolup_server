package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.controller.dto.request.GameWebSocketRequest;
import com.poolup.poolup.game.controller.dto.response.GameWebSocketResponse;
import com.poolup.poolup.game.domain.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GameWebSocketController {
    @MessageMapping("/room/create") // 1. 클라이언트에서 전송되는 메시지를 핸들러매핑. WebSocketConfig의 prefix에 따라, /app/room/create 경로를 매핑한다.
    @SendTo("/topic/room-status")  // 2. 메시지를 특정 구독 경로로 브로드캐스팅. /topic/room-status를 구독한 모든 클라이언트는 메시지를 받을 수 있다.
    public GameWebSocketResponse handleRoomCreate(GameWebSocketRequest gameWebSocketRequest) {
        // 방 생성 메시지를 처리
        System.out.println("요청 수신 - roomId: " + gameWebSocketRequest.getRoomId());
        System.out.println("요청 수신 - playerId: " + gameWebSocketRequest.getPlayerId());
        System.out.println("방 생성 메시지 수신: " + gameWebSocketRequest);

        // 클라이언트로 브로드캐스트할 데이터
        return GameWebSocketResponse.builder()
                .roomId(gameWebSocketRequest.getRoomId())
                .status(RoomStatus.WAITING.name())
                .build();
    }

    @MessageMapping("/room/join") // 클라이언트에서 방 참가 메시지 처리
    @SendTo("/topic/room-status")  // 브로드캐스트 경로
    public GameWebSocketResponse handleRoomJoin(GameWebSocketRequest gameWebSocketRequest) {
        // 방 참가 메시지를 처리
        System.out.println("방 참가 메시지 수신: " + gameWebSocketRequest);

        return GameWebSocketResponse.builder()
                .roomId(gameWebSocketRequest.getRoomId())
                .status(RoomStatus.READY.name())
                .build();
    }
}
