package com.poolup.poolup.game.application;

import com.poolup.poolup.game.controller.dto.response.GameRoomJoinResponse;
import com.poolup.poolup.game.controller.dto.response.GameWebSocketResponse;
import com.poolup.poolup.game.domain.model.RoomStatus;
import com.poolup.poolup.game.controller.dto.response.GameRoomCreateResponse;
import com.poolup.poolup.game.controller.dto.request.TemporaryLoginRequest;
import com.poolup.poolup.game.controller.dto.response.TemporaryLoginResponse;
import com.poolup.poolup.game.domain.model.GameRoom;
import com.poolup.poolup.game.domain.repository.GameRoomRepository;
import com.poolup.poolup.game.infrastructure.TemporaryMemberRepository;
import com.poolup.poolup.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


/*
* 임시로그인, 카드 관련 로직을 수행하는 Service계층.
* 단일 책임 원칙에 어긋나지만, 리팩토링 편의성을 위해 하나의 도메인에 혼용하였음.
* 따라서, 멤버 관련 기능은 추후 새로 만들어서 사용하시길 바람.
* */
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRoomRepository gameRoomRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    // 1. 멤버 관련 로직
    // 1-1. 임시 로그인
    public TemporaryLoginResponse temporaryLogin(TemporaryLoginRequest temporaryLoginRequest) {
        System.out.println("**email:"+temporaryLoginRequest.getEmail());
        // 이메일을 통해 멤버를 가져온다
        Member member = temporaryMemberRepository.findByEmail(temporaryLoginRequest.getEmail());

        System.out.println("member:"+member);

        // 응답 DTO에 id, email, name을 담아 넘긴다.
        return TemporaryLoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    // 2. 게임 관련 로직
    // 2-1. 방 생성
    public GameRoomCreateResponse createRoom(Long player1Id) {
        Member player1 = temporaryMemberRepository.findById(player1Id)
                .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다."));

        String roomId = UUID.randomUUID().toString();
        GameRoom gameRoom = GameRoom.builder()
                .roomId(roomId)
                .player1Id(player1.getId())
                .build();

        gameRoomRepository.save(gameRoom);

        return GameRoomCreateResponse.builder()
                .roomId(roomId)
                .roomStatus(RoomStatus.WAITING)
                .player1P(GameRoomCreateResponse.Player1P.builder()
                        .memberId(player1.getId())
                        .name(player1.getName())
                        .build())
                .build();
    }


    // 3. 게임 방 참가
    // 2-2. 방 참가
    public GameRoomJoinResponse joinRoom(String roomId, Long player2Id) {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        Member player2 = temporaryMemberRepository.findById(player2Id)
                .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다."));

        gameRoom.addPlayer(player2.getId());

        Member player1 = Optional.ofNullable(gameRoom.getPlayer1Id())
                .map(player1Id -> temporaryMemberRepository.findById(player1Id)
                        .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다.")))
                .orElse(null);

        return GameRoomJoinResponse.builder()
                .roomId(roomId)
                .status(RoomStatus.READY.name())
                .player1P(player1 != null ? GameRoomJoinResponse.Player.builder()
                        .memberId(player1.getId())
                        .name(player1.getName())
                        .build() : null)
                .player2P(GameRoomJoinResponse.Player.builder()
                        .memberId(player2.getId())
                        .name(player2.getName())
                        .build())
                .message("방 참가 성공")
                .build();
    }

}
