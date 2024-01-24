package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.resources.Client;

@Repository
public interface ClientJpa extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.clientDetails.username =:username")
    abstract public Client retrieveClientByUsername(@Param("username") String username);
}
