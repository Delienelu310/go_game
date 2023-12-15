package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Room;
import com.teoriaprogramowania.go_game.resources.RoomDetails;
import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class RoomManagementController {
    RepositoryInterface repositoryInterface;
    
    public RoomManagementController(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @GetMapping("/rooms") 
    public List<Room> getClients(){
        return repositoryInterface.getRoomRepository().retrieveRooms();
    }

    @GetMapping("/rooms/{id}")
    public Room getRoomById(@PathVariable Long id){
        return repositoryInterface.getRoomRepository().retrieveRoomById(id);
    }


    @DeleteMapping("/rooms/{id}")
    public void deleteRoomById(@PathVariable Long id){
        repositoryInterface.getRoomRepository().deleteRoomById(id);
    }

    @PostMapping("/clients/{client_id}/rooms")
    public Room addRoom(@RequestBody RoomDetails roomDetails, @PathVariable("client_id") Long clientId, @RequestParam("size") int size){
        Client admin = repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Room room = new Room();
        room.setRoomDetails(roomDetails);
        room.setAdmin(admin);
        admin.setCurrentRoom(room);

        room.setGame(repositoryInterface.getGameRepository().createGame(size));

        return repositoryInterface.getRoomRepository().addRoom(room);
    }
}
