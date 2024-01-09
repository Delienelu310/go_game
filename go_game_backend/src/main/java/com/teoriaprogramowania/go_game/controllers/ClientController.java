package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.jacksonMappers.JacksonMapperCollection;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.resources.ClientDetails;
import com.teoriaprogramowania.go_game.resources.Room;

@RestController
public class ClientController {

    private RepositoryInterface repositoryInterface;
    private JacksonMapperCollection jacksonMapper;
    
    public ClientController(RepositoryInterface repositoryInterface, JacksonMapperCollection jacksonMapper) {
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    @GetMapping("/clients") 
    public MappingJacksonValue getClients(){
        List<Client> clients = repositoryInterface.getClientRepository().retrieveClients();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(clients);
        mappingJacksonValue.setFilters(jacksonMapper.getClientJacksonMapper().getMainFilterProvider());
        return mappingJacksonValue;
    }

    @GetMapping("/clients/{id}")
    public MappingJacksonValue getClientById(@PathVariable Long id){
        Client client = repositoryInterface.getClientRepository().retrieveClientById(id);
        return jacksonMapper.getClientJacksonMapper().getMainFilterMapped(client);
    }

    @PostMapping("/login")
    public MappingJacksonValue login(@RequestBody ClientDetails clientDetails){
        Client cl = repositoryInterface.getClientRepository().retrieveClientByUsername(clientDetails.getUsername());
        
        if(cl.getClientDetails().getPassword().equals(clientDetails.getPassword())) 
             return jacksonMapper.getClientJacksonMapper().getMainFilterMapped(cl);
        else throw new RuntimeException("Wrong password");
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientById(@PathVariable Long id){
        repositoryInterface.getClientRepository().deleteClientById(id);
    }

    @PostMapping("/clients")
    public MappingJacksonValue addClient(@RequestBody ClientDetails clientDetails){
        Client client = repositoryInterface.getClientRepository().addClient(clientDetails);
        return jacksonMapper.getClientJacksonMapper().getMainFilterMapped(client);
    }

    @PutMapping("/clients/{id}")
    public void updateClient(@RequestBody ClientDetails clientDetails, @PathVariable Long id){
        repositoryInterface.getClientRepository().updateClient(clientDetails, id);
    }

    @PutMapping("/clients/{id}/rooms/{room_id}/exitroom")
    public void exitRoom(@PathVariable("id") Long id, @PathVariable("room_id") Long roomId){
        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        Client client = repositoryInterface.getClientRepository().retrieveClientById(id);
        room.getParticipants().removeIf(participant -> participant.getId() == id);
        client.setIsInRoom(false);

        repositoryInterface.getRoomRepository().updateRoom(room);
        repositoryInterface.getClientRepository().updateClient(client);
    }
}
