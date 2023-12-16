package com.teoriaprogramowania.go_game.repository.runtime_repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.RoomRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Room;

@Repository
public class RuntimeRoomRepository implements RoomRepositoryInterface{

    private List<Room> rooms = new ArrayList<>();
    private Long counter = 100l;

    @Override
    public Room retrieveRoomById(Long id) {
        return rooms.stream().filter(room -> room.getId() == id).toList().get(0);
    }

    @Override
    public List<Room> retrieveRooms() {
        return rooms;
    }

    @Override
    public void deleteRoomById(Long id) {
        rooms.removeIf( room -> room.getId() == id);
    }

    @Override
    public Room addRoom(Room room) {
        room.setId(counter++);
        rooms.add(room);
        return room;
    }

    @Override
    public void updateRoom(Room room) {
        int index = rooms.stream().map( r -> r.getId()).toList().indexOf(room.getId());
        if(index == -1) throw new RuntimeException("The client is not found");
        rooms.set(index, room);
    }

}
