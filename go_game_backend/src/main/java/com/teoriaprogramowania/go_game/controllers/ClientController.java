package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class ClientController {

    RepositoryInterface repositoryInterface;
    
    public ClientController(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @GetMapping("/clients") 
    public List<Client> getClients(){
        return repositoryInterface.getClientRepository().retrieveClients();
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable Long id){
        return repositoryInterface.getClientRepository().retrieveClientById(id);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientById(@PathVariable Long id){
        repositoryInterface.getClientRepository().deleteClientById(id);
    }

    @PostMapping("/clients")
    public Client addClient(@RequestBody Client client){
        return repositoryInterface.getClientRepository().addClient(client);
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@RequestBody Client client, @PathVariable Long id){
        client.setId(id);
        return repositoryInterface.getClientRepository().updateClient(client);
    }
}
