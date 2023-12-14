package com.teoriaprogramowania.go_game.repository.interfaces;

import java.util.List;

import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.resources.ClientDetails;

public interface ClientRepositoryInterface {
    
    List<Client> retrieveClients();
    Client retrieveClientById(Long id);
    Client retrieveClientByUsername(String username);

    void deleteClientById(Long id);
    Client addClient(Client client);    
    void updateClient(ClientDetails clientDetails, Long id);    
}
