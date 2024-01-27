package com.teoriaprogramowania.go_game.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teoriaprogramowania.go_game.GoGameApplication;
import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.MoveType;
import com.teoriaprogramowania.go_game.game.Player;
import com.teoriaprogramowania.go_game.jacksonMappers.JacksonMapperCollection;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Client;

@RestController
public class OngoingGameController {
    private RepositoryInterface repositoryInterface;
    private JacksonMapperCollection jacksonMapper;

    private Logger logger = LoggerFactory.getLogger(GoGameApplication.class);

    public OngoingGameController(RepositoryInterface repositoryInterface, JacksonMapperCollection jacksonMapper){
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }

    private MappingJacksonValue getMappedGameMain(Game game){
        MappingJacksonValue gameJackson = new MappingJacksonValue(game);
        gameJackson.setFilters(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());
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
        Player player = clientId == -1 ? new Player() : new Player(client);

        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        
        for(int i = 0; i < game.getPlayers().size(); i++){
            if(game.getPlayers().get(i).getClient() != null && game.getPlayers().get(i).getClient().getId() == clientId){
                game.getPlayers().set(i, new Player());
            }
        }
        while(game.getPlayers().size() < game.getPlayersCount() && game.getPlayers().size() <= position + 1){
            game.getPlayers().add(new Player());
        }

        game.getPlayers().set(position, player);
        
        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }
    
    @PutMapping("/games/{id}/start")
    public MappingJacksonValue startGame(@PathVariable("id") Long gameId){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        game.start();

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
        

        logger.info("Doing smth");
        logger.info(Integer.toString(move.getX()));
        logger.info(Integer.toString(move.getY()));
        Boolean moveResult = game.makeMove(move);
        if(!moveResult) throw new RuntimeException("Move is invalid");
        game.hasChangedState();
        logger.info("Doing smth 2");

        repositoryInterface.getGameRepository().updateGame(game);
        return getMappedGameMain(game);
    }


    @PutMapping("/games/{id}/toggle/stonegroup")
    public MappingJacksonValue toggleStoneGroup(@PathVariable("id") Long gameId, @RequestBody Move move){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        Player player = game.getPlayers().stream()
            .filter(pl -> pl != null && pl.getClient().getId() == move.getPlayer().getClient().getId())
            .findFirst().get();

        logger.info(Integer.toString(move.getX()));
        logger.info(Integer.toString(move.getY()));

        game.toggleDeadStoneGroup(move.getX(), move.getY(), player);

        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }

    @PutMapping("/games/{id}/toggle/agreed_to_finalize/{clientId}")
    public MappingJacksonValue toggleAgreedToFinalize(@PathVariable("id") Long gameId, @PathVariable("clientId") Long clientId){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        Player player = game.getPlayers().stream()
            .filter(pl -> pl != null && pl.getClient().getId() == clientId)
            .findFirst().get();

        game.toggleAgreedToFinalize(player);
        game.finalizeGame();

        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }

    @PutMapping("/games/{id}/resume_game/{clientId}")
    public MappingJacksonValue resumeGame(@PathVariable("id") Long gameId, @PathVariable("clientId") Long clientId){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        Player player = game.getPlayers().stream()
            .filter(pl -> pl != null && pl.getClient().getId() == clientId)
            .findFirst().get();

        Move resumeMove = new Move(0, 0, MoveType.RESUMEGAME, player);
        game.makeMove(resumeMove);
        game.resumeGame();

        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }


}