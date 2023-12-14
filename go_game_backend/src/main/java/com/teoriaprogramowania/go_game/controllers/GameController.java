package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;

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

    @PostMapping("/games")
    public Game createGame(@RequestBody Game game){
        return repositoryInterface.getGameRepository().createGame(game);
    }

}
