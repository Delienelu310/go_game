package com.teoriaprogramowania.go_game.repository.runtime_repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.GoGameApplication;
import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.resources.ClientDetails;

@Repository
public class RuntimeClientRepository implements ClientRepositoryInterface{
    Logger logger = LoggerFactory.getLogger(GoGameApplication.class);

    private List<Client> clients = new ArrayList<>();
    private Long clientsCounter = 100l;

    @Override
    public List<Client> retrieveClients() {
        return clients;
    }

    @Override
    public Client retrieveClientById(Long id) {
        return clients.stream().filter( client -> client.getId() == id ).toList().get(0);
    }

    @Override
    public void deleteClientById(Long id) {
        clients.removeIf(client -> client.getId() == id);
    }

    @Override
    public void updateClient(ClientDetails clientDetails, Long id) {

        int index = clients.stream().map( cl -> cl.getId()).toList().indexOf(id);
        if(index == -1) throw new RuntimeException("The client is not found");
        clients.get(index).setClientDetails(clientDetails);
    }

    @Override
    public void updateClient(Client client){
        int index = clients.indexOf(client);
        if(index == -1) throw new RuntimeException("The client is not found");
        clients.set(index, client);
    }

    @Override
    public Client addClient(ClientDetails clientDetails) {
        Client client = new Client();
        client.setClientDetails(clientDetails);
        client.setId(clientsCounter++);
        clients.add(client);
        return client;
    }

    @Override
    public Client retrieveClientByUsername(String username) {
        logger.info(username);
        return clients.stream().filter(client -> client.getClientDetails().getUsername().equals(username)).toList().get(0);
    }

    
    
}
