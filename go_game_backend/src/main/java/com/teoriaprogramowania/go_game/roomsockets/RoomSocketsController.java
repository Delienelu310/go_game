package com.teoriaprogramowania.go_game.roomsockets;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Room;

@Controller
public class RoomSocketsController {

    private RepositoryInterface repositoryInterface;

    public RoomSocketsController(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }


    @MessageMapping("/{room_id}/move")
	@SendTo("/game/{room_id}/move")
	public Room greeting(@Payload Move move, @DestinationVariable("room_id") Long roomId){

        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);
        Game game = room.getGame();
        game.addMove(move);

        repositoryInterface.getRoomRepository().updateRoom(room);
        repositoryInterface.getGameRepository().updateGame(game);

		return room;
	}

}
