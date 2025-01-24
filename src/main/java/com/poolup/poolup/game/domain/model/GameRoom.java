package com.poolup.poolup.game.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
public class GameRoom {
    private String roomId; // 게임방 id
    private Long player1Id; // 플레이어1 id
    private Long player2Id; // 플레이어2 id
    private RoomStatus status; // 게임방 상태(WAITING, READY, ...)
    private int player1Score = 0; // 1P 점수
    private int player2Score = 0; // 2P 점수
    private final Set<Long> solvedProblems = new HashSet<>(); // 이미 처리된 정답 문제 번호.



    @Builder
    public GameRoom(String roomId, Long player1Id) {
        this.roomId = roomId;
        this.player1Id = player1Id;
        this.status = RoomStatus.WAITING;
    }

    public void addPlayer(Long player2Id) {
        //만약 이미 플레이어2 자리에 누군가 있다면, 예외 처리
        if (this.player2Id != null) throw new IllegalStateException("이미 방이 가득 찼습니다.");
        
        //입장이 가능하다면 데이터 할당 및 RoomStatus 업데이트
        this.player2Id = player2Id;
        this.status = RoomStatus.READY;
    }

    // 문제를 정답으로 처리
    public void markProblemAsSolved(Long problemNumber) {
        solvedProblems.add(problemNumber);
    }

    // 문제 번호가 이미 정답 처리되었는지 확인
    public boolean isProblemAlreadySolved(Long problemNumber) {
        return solvedProblems.contains(problemNumber);
    }

    // 점수 증가
    public void incrementScore(Long playerId) {
        if (playerId.equals(player1Id)) {
            player1Score++;
        } else if (playerId.equals(player2Id)) {
            player2Score++;
        }
    }
}
