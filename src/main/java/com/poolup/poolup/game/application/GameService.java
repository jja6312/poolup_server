package com.poolup.poolup.game.application;

import com.poolup.poolup.game.controller.dto.request.GameCardSelectionRequest;
import com.poolup.poolup.game.controller.dto.response.*;
import com.poolup.poolup.game.domain.model.RoomStatus;
import com.poolup.poolup.game.controller.dto.request.TemporaryLoginRequest;
import com.poolup.poolup.game.domain.model.GameRoom;
import com.poolup.poolup.game.domain.repository.GameRoomRepository;
import com.poolup.poolup.game.infrastructure.TemporaryMemberRepository;
import com.poolup.poolup.game.infrastructure.TemporaryProblemRepository;
import com.poolup.poolup.member.domain.Member;
import com.poolup.poolup.problem.domain.Problem;
import com.poolup.poolup.problem.enums.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final TemporaryProblemRepository temporaryProblemRepository;

    // 1. 멤버 관련 로직
    // 1-1. 임시 로그인
    public TemporaryLoginResponse temporaryLogin(TemporaryLoginRequest temporaryLoginRequest) {
        // 이메일을 통해 멤버를 가져온다
        Member member = temporaryMemberRepository.findByEmail(temporaryLoginRequest.getEmail());
        // 응답 DTO에 id, email, name을 담아 넘긴다.
        return TemporaryLoginResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    // 2. 게임 관련 로직
    // 2-1. 방 생성
    public GameRoomCreateResponse createRoom(Long player1Id) {
        Member player1 = temporaryMemberRepository.findById(player1Id)
                .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다."));

        // 방 식별번호(==UUID) 생성
        String roomId = UUID.randomUUID().toString();

        // 게임방 생성
        GameRoom gameRoom = GameRoom.builder()
                .roomId(roomId)
                .player1Id(player1.getId())
                .build();

        // 인메모리에 저장(DB아님.)
        gameRoomRepository.save(gameRoom);

        // 화면에 뿌릴 정보 반환
        return GameRoomCreateResponse.builder()
                .roomId(roomId)
                .roomStatus(RoomStatus.WAITING)
                .player1P(GameRoomCreateResponse.Player1P.builder()
                        .memberId(player1.getId())
                        .name(player1.getName())
                        .build())
                .build();
    }


    // 2-2. 방 참가
    public GameRoomJoinResponse joinRoom(String roomId, Long player2Id) {
        // 실제로 존재하는 방인지 찾기
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        // 실제로 존재하는 player2인지 찾기
        Member player2 = temporaryMemberRepository.findById(player2Id)
                .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다."));


        // 실제로 존재하는 player1인지 찾기
        Member player1 = temporaryMemberRepository.findById(gameRoom.getPlayer1Id())
                .orElseThrow(() -> new IllegalArgumentException("플레이어 정보를 찾을 수 없습니다. ID: " + gameRoom.getPlayer1Id()));

        // 인메모리 게임방에 참가시키기(DB엔 게임관련해서 저장안할예정. 성능때문에.)
        gameRoom.addPlayer(player2.getId());

        return GameRoomJoinResponse.builder()
                .roomId(roomId)
                .status(RoomStatus.READY.name())
                .player1P(GameRoomJoinResponse.Player.builder()
                        .memberId(player1.getId())
                        .name(player1.getName())
                        .build())
                .player2P(GameRoomJoinResponse.Player.builder()
                        .memberId(player2.getId())
                        .name(player2.getName())
                        .build())
                .build();
    }


    // 2-3. 게임 시작시, 문제 카드쌍들 가져오기
    public TemporaryGetProblemsResponse getTemporaryProblems(int limit) {//카드 가져오기
        // limit개수만큼(15개) 카드 가져오기
        Pageable pageable = PageRequest.of(0, limit);
        List<Problem> problems = temporaryProblemRepository.findByProblemTypeOrderByCreatedAtDesc(ProblemType.SUBJECTIVE, pageable);

        // List<Problem> -> List<TemporaryGetProblemsResponse.ProblemCard> 형태로 stream을통해 변경
        List<TemporaryGetProblemsResponse.ProblemCard> problemCards = problems.stream()
                .map(problem -> TemporaryGetProblemsResponse.ProblemCard.builder()
                        .id(problem.getId())
                        .question(problem.getQuestion())
                        .answer(problem.getAnswer())
                        .build())
                .toList();

        return TemporaryGetProblemsResponse.builder()
                .problemCards(problemCards)
                .build();
    }

    // 2-4. 한 플레이어가 프론트에서 정답쌍을 골랐을 때, 이미 정답처리 된 문제인지 확인 및 점수 처리
    public GameCardResultResponse processCardSubmission(GameCardSelectionRequest request) {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("방을 찾을 수 없습니다."));

        // 이미 정답처리 되었는지 확인
        if (gameRoom.isProblemAlreadySolved(request.getProblemNumber())) { // 이미정답처리되었다면
            return GameCardResultResponse.builder() //변경된 정보 없이 바로 반환시킨다.
                    .problemNumber(request.getProblemNumber())
                    .score1P(gameRoom.getPlayer1Score())
                    .score2P(gameRoom.getPlayer2Score())
                    .build();
        }

        // 아직 정답처리안되었다면,
        gameRoom.markProblemAsSolved(request.getProblemNumber());//문제를 정답으로 처리
        gameRoom.incrementScore(request.getPlayerId());//스코어 반영

        return GameCardResultResponse.builder()
                .problemNumber(request.getProblemNumber())
                .score1P(gameRoom.getPlayer1Score())
                .score2P(gameRoom.getPlayer2Score())
                .build();
    }
}
