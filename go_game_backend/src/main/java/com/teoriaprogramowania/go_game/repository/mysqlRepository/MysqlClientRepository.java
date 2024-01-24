package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.resources.ClientDetails;

public abstract class MysqlClientRepository implements ClientRepositoryInterface, JpaRepository<Client, Long>{

    @Override
    public List<Client> retrieveClients() {
        return this.findAll();
    }

    @Override
    public Client retrieveClientById(Long id) {
        return this.findById(id).get();
    }

    @Override
    @Query("SELECT * FROM Client c WHERE c.clientDetails.username =:username")
    abstract public Client retrieveClientByUsername(String username);

    @Override
    public void deleteClientById(Long id) {
        this.deleteById(id);
    }

    @Override
    public Client addClient(ClientDetails clientDetails) {
        Client client = new Client();
        client.setClientDetails(clientDetails);
        this.updateClient(client);

        return this.retrieveClientByUsername(clientDetails.getUsername());
        
    }

    @Override
    public void updateClient(ClientDetails clientDetails, Long id) {
        Client client = this.findById(id).get();
        this.updateClient(client);
    }
    
}
