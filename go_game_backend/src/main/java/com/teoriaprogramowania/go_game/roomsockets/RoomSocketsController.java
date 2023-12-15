package com.teoriaprogramowania.go_game.roomsockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.teoriaprogramowania.go_game.game.Color;
import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.repository.interfaces.GameRepositoryInterface;

@Controller
public class RoomSocketsController {

    private GameRepositoryInterface gameRepositoryInterface;

	public RoomSocketsController(GameRepositoryInterface gameRepositoryInterface) {
        this.gameRepositoryInterface = gameRepositoryInterface;
    }


    @MessageMapping("/{game_id}/move")
	@SendTo("/game/{game_id}/move")
	public Color[][] greeting(Move move, @PathVariable("game_id") Long gameId){

        Game game = gameRepositoryInterface.retrieveGameById(gameId);
        game.addMove(move);

		return game.getField();
	}

}
