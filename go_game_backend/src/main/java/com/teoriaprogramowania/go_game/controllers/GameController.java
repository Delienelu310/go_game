package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
// import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class GameController {
    RepositoryInterface repositoryInterface;
    
    public GameController(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @GetMapping("/games") 
    public List<Game> getGames(){
        return repositoryInterface.getGameRepository().retrieveAllGames();
    }

    @GetMapping("/games/{id}")
    public Game getGameById(@PathVariable Long id){
        return repositoryInterface.getGameRepository().retrieveGameById(id);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGameById(@PathVariable Long id){
        repositoryInterface.getGameRepository().deleteGameById(id);
    }

    // @PutMapping("/games/{id}/set/white/{client_id}")
    // public Game setWhitePlayer(@PathVariable("id") Long gameId, @PathVariable("client_id") Long clientId){
    //     Client whitePlayer = repositoryInterface.getClientRepository().retrieveClientById(clientId);
    //     Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
    //     game.setWhite(whitePlayer);
    //     return game;
    // }

    // @PutMapping("/games/{id}/set/black/{client_id}")
    // public Game setBlackPlayer(@PathVariable("id") Long gameId, @PathVariable("client_id") Long clientId){
    //     Client whitePlayer = repositoryInterface.getClientRepository().retrieveClientById(clientId);
    //     Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
    //     game.setBlack(whitePlayer);
    //     return game;
    // }


}
