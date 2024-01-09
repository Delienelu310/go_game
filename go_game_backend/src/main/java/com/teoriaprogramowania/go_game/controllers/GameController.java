package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;

@RestController
public class GameController {
    RepositoryInterface repositoryInterface;
    JacksonMapper jacksonMapper;
    
    public GameController(RepositoryInterface repositoryInterface, JacksonMapper jacksonMapper) {
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    @GetMapping("/games") 
    public MappingJacksonValue getGames(){
        List<Game> games = repositoryInterface.getGameRepository().retrieveAllGames();
        MappingJacksonValue gamesJacksonValue = new MappingJacksonValue(games);
        gamesJacksonValue.setFilters(jacksonMapper.getRoomMainFilterProvider());
        return gamesJacksonValue;
    }

    @GetMapping("/games/{id}")
    public Game getGameById(@PathVariable Long id){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(id);
        MappingJacksonValue gameJackson = new MappingJacksonValue(game);
        gameJackson.setFilters(jacksonMapper.getRoomMainFilterProvider());

        return repositoryInterface.getGameRepository().retrieveGameById(id);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGameById(@PathVariable Long id){
        repositoryInterface.getGameRepository().deleteGameById(id);
    }


}
