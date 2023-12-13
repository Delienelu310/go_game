package com.teoriaprogramowania.go_game.repository.runtime_repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;

@Repository
public class RuntimeClientRepository implements ClientRepositoryInterface{

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
    public Client updateClient(Client client) {
        if(! clients.contains(client)){
            throw new RuntimeException("User does not exist");
        }
        int index = clients.indexOf(client);
        if(index == -1) throw new RuntimeException("The client is not found");
        clients.set(index, client);
        return client;
    }

    @Override
    public Client addClient(Client client) {
        client.setId(clientsCounter++);
        clients.add(client);
        return client;
    }

    @Override
    public Client retrieveClientByUsername(String username) {
        return clients.stream().filter(client -> client.getUsername().equals(username)).toList().get(0);
    }

    
    
}
