package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.jacksonMappers.JacksonMapperCollection;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;

@RestController
public class GameController {
    RepositoryInterface repositoryInterface;
    JacksonMapperCollection jacksonMapper;
    
    public GameController(RepositoryInterface repositoryInterface, JacksonMapperCollection jacksonMapper) {
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    @GetMapping("/games") 
    public MappingJacksonValue getGames(){
        List<Game> games = repositoryInterface.getGameRepository().retrieveAllGames();
        MappingJacksonValue gamesJacksonValue = new MappingJacksonValue(games);
        gamesJacksonValue.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        return gamesJacksonValue;
    }

    @GetMapping("/games/{id}")
    public MappingJacksonValue getGameById(@PathVariable Long id){

        Game game = repositoryInterface.getGameRepository().retrieveGameById(id);
        MappingJacksonValue gameJackson = new MappingJacksonValue(game);
        gameJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());

        return gameJackson;
    }

    @GetMapping("/client/{id}/games")
    public MappingJacksonValue getGameByClientId(@PathVariable Long id){

        List<Game> game = repositoryInterface.getGameRepository().retrieveGamesOfClient(id);

        MappingJacksonValue gameJackson = new MappingJacksonValue(game);
        gameJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        return gameJackson;

    }

    @GetMapping("/games/{id}/move/{moveNumber}")
    public MappingJacksonValue getGameRecreated(@PathVariable("id") Long id, @PathVariable("moveNumber") Integer moveNumber){
        
        Game originalGame = repositoryInterface.getGameRepository().retrieveGameById(id);
        MappingJacksonValue jacksonValue;
        if(moveNumber == originalGame.getMoves().size()){
            jacksonValue = new MappingJacksonValue(originalGame);
        }else{
            List<Move> movesTaken = originalGame.getMoves().subList(0, moveNumber);

            Game recreatedGame = new Game(originalGame.getBoard().getSize());
            recreatedGame.setMoves(movesTaken);

            jacksonValue = new MappingJacksonValue(recreatedGame);
        }
        
        jacksonValue.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
        
        return jacksonValue;
    }

    @DeleteMapping("/games/{id}")
    public void deleteGameById(@PathVariable Long id){
        repositoryInterface.getGameRepository().deleteGameById(id);
    }


}
