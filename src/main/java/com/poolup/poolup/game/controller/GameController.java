package com.poolup.poolup.game.controller;

import com.poolup.poolup.game.application.GameService;
import com.poolup.poolup.game.controller.dto.request.GameRoomCreateRequest;
import com.poolup.poolup.game.controller.dto.request.GameRoomJoinRequest;
import com.poolup.poolup.game.controller.dto.request.TemporaryLoginRequest;
import com.poolup.poolup.game.controller.dto.response.GameRoomCreateResponse;
import com.poolup.poolup.game.controller.dto.response.GameRoomJoinResponse;
import com.poolup.poolup.game.controller.dto.response.TemporaryLoginResponse;
import com.poolup.poolup.game.controller.dto.response.TemporaryGetProblemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TemporaryLoginResponse> temporaryLogin(@RequestBody TemporaryLoginRequest temporaryLoginRequest) {
        // 응답 DTO 형식에 맞게 사용자 정보를 반환
        return ResponseEntity.ok(gameService.temporaryLogin(temporaryLoginRequest));
    }


    // 2. 게임 방 관련
    // 2-1. 방 생성
    @PostMapping("/room")
    public ResponseEntity<GameRoomCreateResponse> createRoom(@RequestBody GameRoomCreateRequest gameRoomRequest) {
        // roomId(UUID == 초대링크) 반환
        return ResponseEntity.ok(gameService.createRoom(gameRoomRequest.getMemberId()));
    }

    // 3. 임시 카드 불러오기 메서드
    @GetMapping("/temporaryProblems")
    public ResponseEntity<TemporaryGetProblemsResponse> getTemporaryProblems(@RequestParam(defaultValue = "15") int limit){
        return ResponseEntity.ok(gameService.getTemporaryProblems(limit));
    }
}

