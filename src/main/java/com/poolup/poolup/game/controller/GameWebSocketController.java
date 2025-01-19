package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.controller.dto.request.WebSocketRequest;
import com.poolup.poolup.game.controller.dto.response.WebSocketResponse;
import com.poolup.poolup.game.domain.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GameWebSocketController {
    @MessageMapping("/room/create") // 클라이언트에서 전송되는 메시지
    @SendTo("/topic/room-status")  // 브로드캐스트 경로
    public WebSocketResponse handleRoomCreate(WebSocketRequest message) {
        // 방 생성 메시지를 처리
        System.out.println("방 생성 메시지 수신: " + message);

        // 클라이언트로 브로드캐스트할 데이터
        return WebSocketResponse.builder()
                .roomId(message.getRoomId())
                .status(RoomStatus.WAITING.name())
                .message("방이 생성되었습니다.")
                .build();
    }

    @MessageMapping("/room/join") // 클라이언트에서 방 참가 메시지 처리
    @SendTo("/topic/room-status")  // 브로드캐스트 경로
    public WebSocketResponse handleRoomJoin(WebSocketRequest message) {
        // 방 참가 메시지를 처리
        System.out.println("방 참가 메시지 수신: " + message);

        return WebSocketResponse.builder()
                .roomId(message.getRoomId())
                .status(RoomStatus.READY.name())
                .message(message.getPlayerName() + "님이 방에 참가했습니다.")
                .build();
    }
}
