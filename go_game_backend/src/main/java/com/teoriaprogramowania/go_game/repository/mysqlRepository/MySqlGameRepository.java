package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.repository.interfaces.GameRepositoryInterface;

@Repository
public class MySqlGameRepository implements GameRepositoryInterface{

    @Autowired
    public GameJpa gameJpa;

    public MySqlGameRepository(){

    }

    @Override
    public Game retrieveGameById(Long id) {
        return gameJpa.findById(id).get();
    }

    @Override
    public List<Game> retrieveAllGames() {
        return gameJpa.findAll();
    }


    @Override
    public void deleteGameById(Long id) {
        gameJpa.deleteById(id);
    }

    @Override
    public Game createGame(int size) {
        Game game = new Game(size);

        return gameJpa.save(game);
    }

    @Override
    public void updateGame(Game game) {
        gameJpa.save(game);
    }

    @Override
    public List<Game> retrieveGamesOfClient(Long clientId) {
        return gameJpa.retrieveGamesOfClient(clientId);
    }
    
}
