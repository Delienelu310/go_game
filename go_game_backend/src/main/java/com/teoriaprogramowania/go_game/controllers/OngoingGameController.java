package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.Player;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class OngoingGameController {
    private RepositoryInterface repositoryInterface;
    private JacksonMapper jacksonMapper;

    public OngoingGameController(RepositoryInterface repositoryInterface, JacksonMapper jacksonMapper){
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    private MappingJacksonValue getMappedGameMain(Game game){
        MappingJacksonValue gameJackson = new MappingJacksonValue(game);
        gameJackson.setFilters(jacksonMapper.getRoomMainFilterProvider());
        return gameJackson;
    }

    @PutMapping("/games/{id}/set/players_count/{count}")
    public MappingJacksonValue setPlayersCount(@PathVariable("id") Long gameId, @PathVariable("count") Integer count){

        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        
        List<Player> players = game.getPlayers();
        while(players.size() < count){
            players.add(null);
        }
        if(players.size() > count){
            players = players.subList(0, count);
        }
        game.setPlayers(players);
        

        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }


    @PutMapping("/games/{id}/set/player/{client_id}/{position}")
    public MappingJacksonValue setPlayer(@PathVariable("id") Long gameId, @PathVariable("client_id") Long clientId, @PathVariable("position") Integer position){
        Client client = clientId == -1 ? null : repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Player player = clientId == -1 ? null : new Player(client);

        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        
        for(int i = 0; i < game.getPlayers().size(); i++){
            if(game.getPlayers().get(i) != null && game.getPlayers().get(i).getClient().getId() == clientId){
                game.getPlayers().set(i, null);
            }
        }
        game.getPlayers().set(position, player);
        
        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }

    @PutMapping("/games/{id}/add/move")
    public MappingJacksonValue addMove(@PathVariable("id") Long gameId, @RequestBody Move move){
        
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        Player player = game.getPlayers().stream()
            .filter(pl -> pl != null && pl.getClient().getId() == move.getPlayer().getClient().getId())
            .findFirst().get();
        move.setPlayer(player);

        game.makeMove(move);

        return getMappedGameMain(game);
    }

    @PutMapping("/games/{id}/start")
    public MappingJacksonValue startGame(@PathVariable("id") Long gameId){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        game.start();

        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }



}