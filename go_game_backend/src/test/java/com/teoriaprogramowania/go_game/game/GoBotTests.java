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
		
		Move move = new Move(0, 1, MoveType.NORMAL, black);
		assertTrue(game.makeMove(move));
		

		Move botMove = bot.findBestMove(game);
/*
		System.out.println(botMove.getX());
		System.out.println(botMove.getY());
	*/	
		
		assertTrue(game.makeMove(botMove));
		

		move = new Move(0, 2, MoveType.NORMAL, black);
		assertTrue(game.makeMove(move));
		
		botMove = bot.findBestMove(game);
		assertTrue(game.makeMove(botMove));

		move = new Move(0, 4, MoveType.NORMAL, black);
		assertTrue(game.makeMove(move));

		botMove = bot.findBestMove(game);
		assertTrue(game.makeMove(botMove));

		move = new Move(1, 3, MoveType.NORMAL, black);
		assertTrue(game.makeMove(move));

		botMove = bot.findBestMove(game);
		assertTrue(game.makeMove(botMove));
		
		
		for(int i = 0; i < game.getMoves().size(); ++i) {
			System.out.println(game.getMoves().get(i).getX());
			System.out.println(game.getMoves().get(i).getY());
			System.out.println("");
		}
		


		/*
		for(int i = 0; i < game.getBoard().getSize(); ++i) {
			for(int j = 0; j < game.getBoard().getSize(); ++j) {
				if(!game.getBoard().getPoint(i, j).isEmpty()) {
					System.out.println(game.getBoard().getPoint(i, j).getX());
					System.out.println(game.getBoard().getPoint(i, j).getY());
					System.out.println("");
				}
			}	
		}
		
		*/
	}

}