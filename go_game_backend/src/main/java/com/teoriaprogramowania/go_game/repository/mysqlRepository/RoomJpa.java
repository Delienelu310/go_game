package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.resources.Room;

@Repository
public interface RoomJpa extends JpaRepository<Room, Long>{
    
}
