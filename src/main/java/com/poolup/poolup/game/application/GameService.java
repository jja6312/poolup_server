package com.poolup.poolup.game.application;

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
    public GameRoomCreateResponse createRoom(Long player1Id) {
        // player1이 있는지 확인
        Member member = temporaryMemberRepository.findById(player1Id)
                .orElseThrow(()-> new IllegalArgumentException("멤버 정보를 찾을 수 없습니다.")); // 없다면 예외 발생

        // 방ID : UUID로 생성
        String roomId = UUID.randomUUID().toString();

        // 게임방은 roomID, 플레이어1Id로 만든다.
        GameRoom gameRoom = GameRoom.builder()
                .roomId(roomId)
                .player1Id(member.getId())
                .build();

        // 방을 인메모리에 ConcurrentHashMap으로 저장한다.
        gameRoomRepository.save(gameRoom);

        return GameRoomCreateResponse.builder()
                .roomId(roomId)
                .roomStatus(RoomStatus.READY)
                .player1P(GameRoomCreateResponse.Player1P.builder()
                        .memberId(member.getId())
                        .name(member.getName())
                        .build())
                .build();
    }

    // 3. 게임 방 참가
    public void joinRoom(String roomId, Long player2Id) {
        //게임방 아이디(==초대코드) 조회
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다.")); //방이 없으면 예외처리

        // 방이 있으면 게임 방에 플레이어2 참가
        gameRoom.addPlayer(player2Id);
    }
}
