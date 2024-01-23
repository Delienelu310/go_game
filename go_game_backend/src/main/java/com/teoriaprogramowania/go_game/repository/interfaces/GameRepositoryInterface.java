package com.teoriaprogramowania.go_game.repository.interfaces;

import java.util.List;

import com.teoriaprogramowania.go_game.game.Game;

public interface GameRepositoryInterface {
    
    public Game retrieveGameById(Long id);
    public List<Game> retrieveAllGames();
    public List<Game> retrieveGamesOfClient(Long clientId);

    public void deleteGameById(Long id);
    public Game createGame(int size);
    public void updateGame(Game game);

}
