package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.*;


public class GoBotTests {
	Client whiteClient = new Client();
	Client blackClient = new Client();
	Client botClient = new Client();
	Player white = new Player(whiteClient);
	Player black = new Player(blackClient);
	Player botPlayer = new Player(botClient);
	
	@Test
	void testCapture() {
		Board board = new Board(9);
		Game game = new Game(board);
		
		List<Player> players = new ArrayList<>();
		players.add(black);
		players.add(botPlayer);
		game.setPlayers(players);
		
		assertEquals(game.getPlayers().size(), 2);
		
		GoBot bot = new GoBot(botPlayer);
		
		Move move = new Move(0, 0, MoveType.NORMAL, black);
		if(game.isMoveValid(move)) {
			game.makeMove(move);
		}
		
		Move botMove = bot.makeMoveBot(game);
		if(game.isMoveValid(botMove)) {
			game.makeMove(botMove);
		}
		
		move = new Move(7, 8, MoveType.NORMAL, black);
		if(game.isMoveValid(move)) {
			game.makeMove(move);
		}

		botMove = bot.makeMoveBot(game);
		if(game.isMoveValid(botMove)) {
			game.makeMove(botMove);
		}
		
		for(int i = 0; i < game.getMoves().size(); ++i) {
			System.out.println(game.getMoves().get(i).getX());
			System.out.println(game.getMoves().get(i).getY());
			System.out.println("");
		}
		

		System.out.println(botMove.getX());
		System.out.println(botMove.getY());
		System.out.println("");
	}

}