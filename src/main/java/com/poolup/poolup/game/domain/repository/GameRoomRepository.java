package com.poolup.poolup.game.domain.repository;

import com.poolup.poolup.game.domain.model.GameRoom;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRoomRepository {
    private final ConcurrentHashMap<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    // 방 생성
    public void save(GameRoom gameRoom) {
        gameRooms.put(gameRoom.getRoomId(), gameRoom);
    }

    // 방 조회
    public Optional<GameRoom> findByRoomId(String roomId) {
        return Optional.ofNullable(gameRooms.get(roomId));
    }

    // 방 삭제
    public void deleteByRoomId(String roomId) {
        gameRooms.remove(roomId);
    }

    // 전체 방 확인
    public ConcurrentHashMap<String, GameRoom> getAllRooms() {
        return gameRooms;
    }

}
