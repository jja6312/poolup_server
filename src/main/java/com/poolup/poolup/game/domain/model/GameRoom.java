package com.poolup.poolup.game.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameRoom {
    private String roomId;
    private Long player1Id;
    private Long player2Id;
    private RoomStatus status;

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
}
