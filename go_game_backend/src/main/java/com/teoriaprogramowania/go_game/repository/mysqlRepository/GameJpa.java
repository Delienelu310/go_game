package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.game.Game;

@Repository
public interface GameJpa extends JpaRepository<Game, Long> {
    
    @Query("SELECT * FROM Game g WHERE g.id =: clientId")
    public List<Game> retrieveGamesOfClient(@Param("clientId") Long clientId);
}
