package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.RoomRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Room;

@Repository
public class MySqlRoomRepository implements RoomRepositoryInterface{

    @Autowired
    private RoomJpa roomJpa;

    public MySqlRoomRepository(){

    }

    @Override
    public Room retrieveRoomById(Long id) {
        return roomJpa.findById(id).get();
    }

    @Override
    public List<Room> retrieveRooms() {
        return roomJpa.findAll();
    }

    @Override
    public void deleteRoomById(Long id) {
        roomJpa.deleteById(id);
    }

    @Override
    public Room addRoom(Room room) {
        return roomJpa.save(room);

    }

    @Override
    public void updateRoom(Room room) {
        roomJpa.save(room);
    }



}
