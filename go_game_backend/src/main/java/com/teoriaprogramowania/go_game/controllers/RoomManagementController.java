package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("/rooms/{id}/add/{client_id}")
    public Room addParticipant(@PathVariable("id") Long id, @PathVariable("client_id") Long clientId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(id);
        Client client = repositoryInterface.getClientRepository().retrieveClientById(clientId);
        room.getParticipants().add(client);

        repositoryInterface.getRoomRepository().updateRoom(room);
        return room;
    }

    @PostMapping("/rooms/{id}/remove/{client_id}")
    public Room removeParticipant(@PathVariable("id") Long id, @PathVariable("client_id") Long clientId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(id);
        room.getParticipants().removeIf(client -> client.getId() == clientId);

        repositoryInterface.getRoomRepository().updateRoom(room);
        return room;
    }

    @PutMapping("/rooms/{id}/set/white/{client_id}")
    public void setWhitePlayer(@PathVariable("id") Long roomId, @PathVariable("client_id") Long clientId){
        Client whitePlayer = repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        room.getGame().setWhite(whitePlayer);
        repositoryInterface.getGameRepository().updateGame(room.getGame());
        repositoryInterface.getRoomRepository().updateRoom(room);
    }

    @PutMapping("/rooms/{id}/set/black/{client_id}")
    public void setBlackPlayer(@PathVariable("id") Long roomId, @PathVariable("client_id") Long clientId){
        Client blackPlayer = repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        room.getGame().setBlack(blackPlayer);
        repositoryInterface.getGameRepository().updateGame(room.getGame());
        repositoryInterface.getRoomRepository().updateRoom(room);
    }

    @PutMapping("/rooms/{id}/remove/white")
    public void removeWhitePlayer(@PathVariable("id") Long roomId){

        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        room.getGame().setWhite(null);
        repositoryInterface.getGameRepository().updateGame(room.getGame());
        repositoryInterface.getRoomRepository().updateRoom(room);
    }

    @PutMapping("/rooms/{id}/remove/black")
    public void setBlackPlayer(@PathVariable("id") Long roomId){

        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        room.getGame().setBlack(null);
        repositoryInterface.getGameRepository().updateGame(room.getGame());
        repositoryInterface.getRoomRepository().updateRoom(room);
    }

    @PutMapping("/rooms/{id}/start")
    public void startGame(@PathVariable("id") Long roomId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        room.getGame().start();

        repositoryInterface.getGameRepository().updateGame(room.getGame());
        repositoryInterface.getRoomRepository().updateRoom(room);
    }
}
