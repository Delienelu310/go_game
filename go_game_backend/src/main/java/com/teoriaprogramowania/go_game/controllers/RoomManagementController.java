package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
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
import com.teoriaprogramowania.jacksonMappers.JacksonMapperCollection;
import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class RoomManagementController {
    RepositoryInterface repositoryInterface;
    JacksonMapperCollection jacksonMapper;
    
    public RoomManagementController(RepositoryInterface repositoryInterface, JacksonMapperCollection jacksonMapper) {
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    @GetMapping("/rooms") 
    public MappingJacksonValue getClients(){
        List<Room> rooms = repositoryInterface.getRoomRepository().retrieveRooms();
        MappingJacksonValue roomsJackson = new MappingJacksonValue(rooms);
        roomsJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        return roomsJackson;
    }

    @GetMapping("/rooms/{id}")
    public MappingJacksonValue getRoomById(@PathVariable Long id){

        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(id);
        MappingJacksonValue roomJackson = new MappingJacksonValue(room);
        roomJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        return roomJackson;
    }


    @DeleteMapping("/rooms/{id}")
    public void deleteRoomById(@PathVariable Long id){
        repositoryInterface.getRoomRepository().deleteRoomById(id);
    }

    @PostMapping("/clients/{client_id}/rooms")
    public MappingJacksonValue addRoom(@RequestBody RoomDetails roomDetails, @PathVariable("client_id") Long clientId, @RequestParam("size") int size){
        Client admin = repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Room room = new Room();
        room.setRoomDetails(roomDetails);
        room.setAdmin(admin);
        admin.setCurrentRoom(room);

        room.setGame(repositoryInterface.getGameRepository().createGame(size));



        room =  repositoryInterface.getRoomRepository().addRoom(room);
        MappingJacksonValue roomJackson = new MappingJacksonValue(room);
        roomJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        return roomJackson;
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
}
