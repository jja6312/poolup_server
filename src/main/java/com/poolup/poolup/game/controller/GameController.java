package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.application.GameService;
import com.poolup.poolup.game.dto.request.GameRoomCreateRequestDTO;
import com.poolup.poolup.game.dto.request.GameRoomJoinRequestDTO;
import com.poolup.poolup.game.dto.request.TemporaryLoginRequestDTO;
import com.poolup.poolup.game.dto.response.GameRoomCreateResponseDTO;
import com.poolup.poolup.game.dto.response.GameRoomJoinResponseDTO;
import com.poolup.poolup.game.dto.response.TemporaryLoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 임시로그인, 카드 관련 controller 역할을 수행하는 계층.
 * 단일 책임 원칙에 어긋나지만, 리팩토링 편의성을 위해 하나의 도메인에 혼용하였음.
 * 따라서, 멤버 관련 기능(temporaryLogin 등)은 추후 새로 만들어서 사용하시길 바람.
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
public class GameController {
    private final GameService gameService;


    // 1. 임시 로그인 메서드 (비밀번호 없이 email로 member조회)
    @PostMapping("/temporaryLogin")
    public ResponseEntity<TemporaryLoginResponseDTO> temporaryLogin(@RequestBody TemporaryLoginRequestDTO temporaryLoginRequestDTO) {
        // 응답 DTO 형식에 맞게 사용자 정보를 반환
        return ResponseEntity.ok(gameService.temporaryLogin(temporaryLoginRequestDTO));
    }


    // 2. 게임 방 관련
    // 2-1. 방 생성
    @PostMapping("/room")
    public ResponseEntity<GameRoomCreateResponseDTO> createRoom(@RequestBody GameRoomCreateRequestDTO gameRoomRequestDTO) {
        // roomId(UUID == 초대링크) 반환
        return ResponseEntity.ok().body(gameService.createRoom(gameRoomRequestDTO.getMemberId()));
    }

    // 2-2. 방 참가
    @PostMapping("/room/join")
    public ResponseEntity<GameRoomJoinResponseDTO> joinRoom(@RequestBody GameRoomJoinRequestDTO gameRoomJoinRequestDTO) {
        gameService.joinRoom(gameRoomJoinRequestDTO.getRoomId(), gameRoomJoinRequestDTO.getMemberId());
        return ResponseEntity.ok(GameRoomJoinResponseDTO.builder()
                    .message("방 참가 성공")
                    .build());
    }
}

