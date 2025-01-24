package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.application.GameService;
import com.poolup.poolup.game.controller.dto.request.GameCardSelectionRequest;
import com.poolup.poolup.game.controller.dto.request.GameWebSocketRequest;
import com.poolup.poolup.game.controller.dto.response.GameCardResultResponse;
import com.poolup.poolup.game.controller.dto.response.GameRoomJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {
    private final GameService gameService;

    @MessageMapping("/room/join") // 클라이언트에서 방 참가 메시지를 처리
    @SendTo("/topic/room-status")  // /topic/room-status를 구독한 모든 클라이언트에게 브로드캐스팅
    public ResponseEntity<GameRoomJoinResponse> handleRoomJoin(GameWebSocketRequest request) {
        return ResponseEntity.ok(gameService.joinRoom(request.getRoomId(), request.getPlayerId()));
    }

    // 정답 처리 메시지 처리
    // 문제 정답 한 쌍을 프론트에서 선택한 경우, 해당 문제번호가 서버로 들어오게 된다.
    @MessageMapping("/card/submit")
    @SendTo("/topic/answer-correct")
    public GameCardResultResponse handleCardSubmission(GameCardSelectionRequest request) {
        // 백엔드에서 problemNumber 정답 여부 검증 및 점수 처리
        return gameService.processCardSubmission(request);
    }

}
