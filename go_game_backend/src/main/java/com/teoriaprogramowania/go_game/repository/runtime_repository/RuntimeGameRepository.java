package com.teoriaprogramowania.go_game.repository.runtime_repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.repository.interfaces.GameRepositoryInterface;

@Repository
public class RuntimeGameRepository implements GameRepositoryInterface{

    private List<Game> games = new ArrayList<>();
    Long counter = 100l;

    @Override
    public Game retrieveGameById(Long id) {
        return games.stream().filter( game -> game.getId() == id).toList().get(0);
    }

    @Override
    public List<Game> retrieveAllGames() {
        return games;
    }

    @Override
    public void deleteGameById(Long id) {
        games.removeIf( game -> game.getId() == id);
    }

    @Override
    public Game createGame(int size) {
        Game game = new Game(size);
        game.setId(counter++);
        games.add(game);
        return game;
    }

    @Override
    public void updateGame(Game game) {
        int index = games.stream().map( g -> g.getId()).toList().indexOf(game.getId());
        if(index == -1) throw new RuntimeException("The game is not found");
        games.set(index, game);
    }

    
    
}
