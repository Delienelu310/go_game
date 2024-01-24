package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.resources.ClientDetails;

@Repository
public class MysqlClientRepository implements ClientRepositoryInterface{

    @Autowired
    private ClientJpa clientJpa;

    public MysqlClientRepository(){

    }

    @Override
    public List<Client> retrieveClients() {
        return clientJpa.findAll();
    }

    @Override
    public Client retrieveClientById(Long id) {
        return clientJpa.findById(id).get();
    }
   

    @Override
    public void deleteClientById(Long id) {
        clientJpa.deleteById(id);
    }

    @Override
    public Client addClient(ClientDetails clientDetails) {
        Client client = new Client();
        client.setClientDetails(clientDetails);
        clientJpa.save(client);

        return clientJpa.retrieveClientByUsername(clientDetails.getUsername());
        
    }

    @Override
    public void updateClient(ClientDetails clientDetails, Long id) {
        Client client = clientJpa.findById(id).get();
        client.setClientDetails(clientDetails);
        clientJpa.save(client);
    }

    @Override
    public void updateClient(Client client) {
        clientJpa.save(client);
    }

    @Override
    public Client retrieveClientByUsername(String username) {
        return clientJpa.retrieveClientByUsername(username);
    }
    
}
