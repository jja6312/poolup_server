package com.poolup.poolup.game.domain.model;

public enum RoomStatus {
    WAITING,     // 플레이어 1만 입장한 상태
    READY,       // 플레이어 2명 모두 입장한 상태
    BOOM         // 게임 완료
}
