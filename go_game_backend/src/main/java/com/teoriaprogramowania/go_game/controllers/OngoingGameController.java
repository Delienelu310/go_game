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
import com.teoriaprogramowania.go_game.game.BoardRow;
import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.MoveType;
import com.teoriaprogramowania.go_game.game.Player;
import com.teoriaprogramowania.go_game.game.Point;
import com.teoriaprogramowania.go_game.game.State;
import com.teoriaprogramowania.go_game.game_bots.BotClient;
import com.teoriaprogramowania.go_game.game_bots.GoBot;
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
    public MappingJacksonValue setPlayer(
        @PathVariable("id") Long gameId, 
        @PathVariable("client_id") Long clientId, 
        @PathVariable("position") Integer position
    ){
        Client client = clientId == -1 ? null : repositoryInterface.getClientRepository().retrieveClientById(clientId);
        Player player = clientId == -1 ? null : new Player(client);

        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        
        for(int i = 0; i < game.getPlayers().size(); i++){
            if(game.getPlayers().get(i).getClient() != null && game.getPlayers().get(i).getClient().getId() == clientId){
                game.getPlayers().set(i, null);
            }
        }
        while(game.getPlayers().size() < game.getPlayersCount() && game.getPlayers().size() <= position + 1){
            game.getPlayers().add(null);
        }

        game.getPlayers().set(position, player);
        
        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }

    @PutMapping("/games/{id}/add/bot/{type}/{position}")
    public MappingJacksonValue setBot(
        @PathVariable("id") Long gameId, 
        @PathVariable("type") Integer type, 
        @PathVariable("position") Integer position
    ){
        

        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        
        BotClient botClient = new BotClient();
        switch (type) {
            case -1:
                botClient = null;
            case 1:
                botClient.setBot(new GoBot());
                break;
            default:
                throw new RuntimeException("Unexpected bot type");
        }

        repositoryInterface.getBotRepositoryInterface().addBotClient(botClient);

        Player player = new Player(botClient);

        while(game.getPlayers().size() < game.getPlayersCount() && game.getPlayers().size() <= position + 1){
            game.getPlayers().add(null);
        }

        game.getPlayers().set(position, player);
        
        repositoryInterface.getGameRepository().updateGame(game);

        return getMappedGameMain(game);
    }

    private void notifyBot(Game game){
        Player playerMoving = game.getPlayers().get( (game.getPlayers().indexOf(game.getCurrentPlayer()) + 1) % game.getPlayersCount() );
        Boolean isBot = playerMoving.getClient() instanceof BotClient;
        if(! isBot) return;

        BotClient botClient = (BotClient) playerMoving.getClient();

        botClient.getBot().setBotPlayer(playerMoving);
        Move bestMove = botClient.getBot().findBestMove(game);

        Boolean moveResult = game.makeMove(bestMove);
        if(!moveResult) throw new RuntimeException("Move is invalid");
        game.hasChangedState();
        if(game.getState() == State.FINISHED) game.setScore(game.getPlayers().get(0), game.getPlayers().get(1));
        else if(game.getState() == State.NEGOTIATION) pickAllDeadStoneGroups(game, playerMoving);

    }

    private void pickAllDeadStoneGroups(Game game, Player player){
        for(BoardRow boardRow : game.getBoard().getBoard()){
            for(Point point : boardRow.getPoints()){
                if(point.getOwner() != player){
                    game.pickDeadStoneGroup(point.getX(), point.getY());
                }
            }
        }
        game.agreeToFinalize(player);
    }
    
    @PutMapping("/games/{id}/start")
    public MappingJacksonValue startGame(@PathVariable("id") Long gameId){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        game.start();
        game.setCurrentPlayer(game.getPlayers().get(0));

        notifyBot(game);

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
        

        Boolean moveResult = game.makeMove(move);
        if(!moveResult) throw new RuntimeException("Move is invalid");
        game.hasChangedState();
        if(game.getState() == State.FINISHED) game.setScore(game.getPlayers().get(0), game.getPlayers().get(1));

        notifyBot(game);

        repositoryInterface.getGameRepository().updateGame(game);
        return getMappedGameMain(game);
    }


    @PutMapping("/games/{id}/toggle/stonegroup")
    public MappingJacksonValue toggleStoneGroup(@PathVariable("id") Long gameId, @RequestBody Move move){
        Game game = repositoryInterface.getGameRepository().retrieveGameById(gameId);
        Player player = game.getPlayers().stream()
            .filter(pl -> pl != null && pl.getClient().getId() == move.getPlayer().getClient().getId())
            .findFirst().get();


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
        if(game.bothPlayersAgreed()){
            game.finalizeGame();
            game.setScore(game.getPlayers().get(0), game.getPlayers().get(1));
        }
        
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
