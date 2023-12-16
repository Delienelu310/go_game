package com.teoriaprogramowania.go_game.repository.interfaces;

import java.util.List;

import com.teoriaprogramowania.go_game.resources.Room;

public interface RoomRepositoryInterface {
    
    public Room retrieveRoomById(Long id);
    public List<Room> retrieveRooms();

    public void deleteRoomById(Long id);
    public Room addRoom(Room room);
    public void updateRoom(Room room);
}
