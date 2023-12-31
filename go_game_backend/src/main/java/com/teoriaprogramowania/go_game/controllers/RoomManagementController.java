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

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Player;
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

    @PutMapping("/rooms/{id}/add/{client_id}")
    public void addParticipant(@PathVariable("id") Long id, @PathVariable("client_id") Long clientId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(id);
        Client client = repositoryInterface.getClientRepository().retrieveClientById(clientId);

        if(room.getParticipants().stream().anyMatch(cl -> cl.getId() == clientId)) return;
        room.getParticipants().add(client);

        repositoryInterface.getRoomRepository().updateRoom(room);
    }

    

    @PutMapping("/rooms/{id}/remove/{client_id}")
    public void removeParticipant(@PathVariable("id") Long id, @PathVariable("client_id") Long clientId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(id);
        room.getParticipants().removeIf(client -> client.getId() == clientId);

        repositoryInterface.getRoomRepository().updateRoom(room);
    }
    
    @PutMapping("/rooms/{id}/set/players/size/{size}")
    public void setPlayersSize(@PathVariable("id") Long roomId, @PathVariable("size") Integer size){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);

        Game game = room.getGame();
        List<Player> players = game.getPlayers();
        while(players.size() < size){
            players.add(null);
        }
        while(players.size() > size){
            players.remove(players.size() - 1);
        }

        game.setPlayers(players);

        repositoryInterface.getRoomRepository().updateRoom(room);
        repositoryInterface.getGameRepository().updateGame(game);

    }

    @PutMapping("/rooms/{id}/set/players/{position}/{client_id}")
    public void setPlayer(@PathVariable("id") Long roomId, @PathVariable("client_id") Long clientId, @PathVariable("position") Integer position){
        
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);

        Game game = room.getGame();
        List<Player> players = game.getPlayers();
  
        if(players.size() <= position) throw new RuntimeException("Invalid position");
        if(players.get(position) != null) throw new RuntimeException("Position is held");

        
        if(clientId == -1){
            players.set(position, null);
        }else{
            Client client = repositoryInterface.getClientRepository().retrieveClientById(clientId);
            Player player = new Player(client);
            players.set(position, player);
        } 

        repositoryInterface.getGameRepository().updateGame(game);
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
