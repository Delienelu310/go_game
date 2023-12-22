package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameTests {
	Client whiteClient = new Client();
	Client blackClient = new Client();
	Player white = new Player(whiteClient);
	Player black = new Player(blackClient);
	
	@Test
    void testNormalMoveValidation() {
        Board board = new Board(19);
        Game game = new Game(19);
        Move normalMove = new Move(board, new Point(10, 10, board));

        boolean result = game.simulateMove(board, normalMove, white);

        assertTrue(result, "Normal move should be valid.");
    }

    @Test
    void testSuicideMoveValidation() {
        Board board = new Board(19);
        
        Point p1 = new Point(9, 10, board);
	    Point p2 = new Point(11, 10, board);
	    Point p3 = new Point(10, 9, board);
	    Point p4 = new Point(10, 11, board);

	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    board.addPoint(p4);
	    
	    p1.setStoneGroup(new StoneGroup(p1, black));
	    p2.setStoneGroup(new StoneGroup(p2, black));
	    p3.setStoneGroup(new StoneGroup(p3, black));
	    p4.setStoneGroup(new StoneGroup(p4, black));

        Game game = new Game(board);
	    
        Move suicideMove = new Move(board, new Point(10, 10, board));
        
        boolean result = game.simulateMove(board, suicideMove, white);

        assertFalse(result, "Suicide move should be invalid.");
    }

    @Test
    void testCapturingMoveValidation() {
        Board board = new Board(19);
        Game game = new Game(19);
        
        Move capturingMove = new Move(board, new Point(10, 11, board));

        board.getPoint(10, 10).setStoneGroup(new StoneGroup(new Point(10, 10, board), black));

        board.getPoint(9, 10).setStoneGroup(new StoneGroup(new Point(9, 10, board), white));
        board.getPoint(11, 10).setStoneGroup(new StoneGroup(new Point(11, 10, board), white));
        board.getPoint(10, 9).setStoneGroup(new StoneGroup(new Point(10, 9, board), white));

        boolean result = game.simulateMove(board, capturingMove, white);

        // Verify
        assertTrue(result);
    }

    @Test
    void testCaptureMoveRuleValidation() {
        Board board = new Board(19);
        Game game = new Game(19);

        Point p5 = new Point(12, 10, board);
	    Point p6 = new Point(11, 11, board);
	    Point p7 = new Point(11, 9, board);
	    
	    StoneGroup stoneGroup5 = new StoneGroup(p5, white);
	    StoneGroup stoneGroup6 = new StoneGroup(p6, white);
	    StoneGroup stoneGroup7 = new StoneGroup(p7, white);

	    p5.setStoneGroup(stoneGroup5);
	    p6.setStoneGroup(stoneGroup6);
	    p7.setStoneGroup(stoneGroup7);

	    board.addPoint(p5);
	    board.addPoint(p6);
	    board.addPoint(p7);

	    
	    
        Point p1 = new Point(9, 10, board);
	    Point p2 = new Point(11, 10, board);
	    Point p3 = new Point(10, 9, board);
	    Point p4 = new Point(10, 11, board);

	    StoneGroup stoneGroup1 = new StoneGroup(p1, black);
	    StoneGroup stoneGroup2 = new StoneGroup(p2, black);
	    StoneGroup stoneGroup3 = new StoneGroup(p3, black);
	    StoneGroup stoneGroup4 = new StoneGroup(p4, black);

	    p1.setStoneGroup(stoneGroup1);
	    p2.setStoneGroup(stoneGroup2);
	    p3.setStoneGroup(stoneGroup3);
	    p4.setStoneGroup(stoneGroup4);
	    
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    board.addPoint(p4);
	    
        Move captureMove = new Move(board, new Point(10, 10, board));
	    
        boolean result = game.simulateMove(board, captureMove, white);

        assertTrue(result);
        
        //TODO: not finished
    }

}
