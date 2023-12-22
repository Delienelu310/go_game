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
        Move normalMove = new Move(new Point(10, 10, board));

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

	    p1.setStoneGroup(new StoneGroup(p1, black));
	    p2.setStoneGroup(new StoneGroup(p2, black));
	    p3.setStoneGroup(new StoneGroup(p3, black));
	    p4.setStoneGroup(new StoneGroup(p4, black));
	    
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    board.addPoint(p4);
	    

        Game game = new Game(board);
	    
        Move suicideMove = new Move(new Point(10, 10, board));
        
        boolean result = game.simulateMove(board, suicideMove, white);

        assertFalse(result, "Suicide move should be invalid.");
    }

    @Test
    void testCapturingMoveValidation() {
        Board board = new Board(19);
        Game game = new Game(19);
        

        //board.getPoint(10, 10).setStoneGroup(new StoneGroup(new Point(10, 10, board), black));
        Point pointToCapture = new Point(10, 10, board);
        pointToCapture.setStoneGroup(new StoneGroup(pointToCapture, black));
        
        /*
        board.getPoint(9, 10).setStoneGroup(new StoneGroup(new Point(9, 10, board), white));
        board.getPoint(11, 10).setStoneGroup(new StoneGroup(new Point(11, 10, board), white));
        board.getPoint(10, 9).setStoneGroup(new StoneGroup(new Point(10, 9, board), white));
*/
        Point p1 = new Point(9, 10, board);
        p1.setStoneGroup(new StoneGroup(p1, white));

        Point p2 = new Point(11, 10, board);
        p2.setStoneGroup(new StoneGroup(p2, white));

        Point p3 = new Point(10, 9, board);
        p3.setStoneGroup(new StoneGroup(p3, white));

        Move capturingMove = new Move(new Point(10, 11, board));
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
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    board.addPoint(p4);
	    
        Move captureMove = new Move(new Point(10, 10, board));
	    
        boolean result = game.simulateMove(board, captureMove, white);

        assertTrue(result);
    }

    @Test
    void testKoRuleValidation() {
        Board board = new Board(19);
        Game game = new Game(19);

        Point p5 = new Point(12, 10, board);
	    Point p6 = new Point(11, 11, board);
	    Point p7 = new Point(11, 9, board);
	    StoneGroup stoneGroup5 = new StoneGroup(p5, white);
	    StoneGroup stoneGroup6 = new StoneGroup(p6, white);
	    StoneGroup stoneGroup7 = new StoneGroup(p7, white);
	    board.addPoint(p5);
	    board.addPoint(p6);
	    board.addPoint(p7);
	    
        Point p1 = new Point(9, 10, board);
	    Point p2 = new Point(10, 11, board);
	    Point p3 = new Point(10, 9, board);
	    StoneGroup stoneGroup1 = new StoneGroup(p1, black);
	    StoneGroup stoneGroup2 = new StoneGroup(p2, black);
	    StoneGroup stoneGroup3 = new StoneGroup(p3, black);
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    
	    Move normalMove = new Move(new Point(11, 10, board));
	    boolean result = game.simulateMove(board, normalMove, black);
        assertTrue(result);

	    Move captureMove = new Move(new Point(10, 10, board));
	    boolean captureResult = game.simulateMove(board, captureMove, white);
        assertTrue(captureResult);
	    
        //now game should remember this state of the board.
	    Move koMove = new Move(new Point(11, 10, board));
	    boolean koResult = game.simulateMove(board, koMove, black);
        assertFalse(koResult);
    }
    
    @Test
    void testKoRuleValidation2() {
        Board board = new Board(19);
        Game game = new Game(19);

        Point p5 = new Point(12, 10, board);
	    Point p6 = new Point(11, 11, board);
	    Point p7 = new Point(11, 9, board);
	    StoneGroup stoneGroup5 = new StoneGroup(p5, white);
	    StoneGroup stoneGroup6 = new StoneGroup(p6, white);
	    StoneGroup stoneGroup7 = new StoneGroup(p7, white);
	    board.addPoint(p5);
	    board.addPoint(p6);
	    board.addPoint(p7);
	    
        Point p1 = new Point(9, 10, board);
	    Point p2 = new Point(10, 11, board);
	    Point p3 = new Point(10, 9, board);
	    StoneGroup stoneGroup1 = new StoneGroup(p1, black);
	    StoneGroup stoneGroup2 = new StoneGroup(p2, black);
	    StoneGroup stoneGroup3 = new StoneGroup(p3, black);
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    
	    Move normalMove = new Move(new Point(11, 10, board));
	    boolean result = game.simulateMove(board, normalMove, black);
        assertTrue(result);

	    Move captureMove = new Move(new Point(10, 10, board));
	    boolean captureResult = game.simulateMove(board, captureMove, white);
        assertTrue(captureResult);
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(new Point(1, 1, board));
	    boolean result2 = game.simulateMove(board, normalMove2, black);
        assertTrue(result2);
        
	    Move normalMove3 = new Move(new Point(2, 2, board));
	    boolean result3 = game.simulateMove(board, normalMove3, white);
        assertTrue(result3);
        
        //now try to capture
	    Move captureMove2 = new Move(new Point(11, 10, board));
	    boolean captureResult2 = game.simulateMove(board, captureMove2, black);
        assertTrue(captureResult2);
        
    }
    

    @Test
    void testKoRuleValidation3() {
        Board board = new Board(19);
        Game game = new Game(19);

        Point p5 = new Point(12, 10, board);
	    Point p6 = new Point(11, 11, board);
	    Point p7 = new Point(11, 9, board);
	    StoneGroup stoneGroup5 = new StoneGroup(p5, white);
	    StoneGroup stoneGroup6 = new StoneGroup(p6, white);
	    StoneGroup stoneGroup7 = new StoneGroup(p7, white);
	    board.addPoint(p5);
	    board.addPoint(p6);
	    board.addPoint(p7);
	    
        Point p1 = new Point(9, 10, board);
	    Point p2 = new Point(10, 11, board);
	    Point p3 = new Point(10, 9, board);
	    StoneGroup stoneGroup1 = new StoneGroup(p1, black);
	    StoneGroup stoneGroup2 = new StoneGroup(p2, black);
	    StoneGroup stoneGroup3 = new StoneGroup(p3, black);
	    board.addPoint(p1);
	    board.addPoint(p2);
	    board.addPoint(p3);
	    
	    Move normalMove = new Move(new Point(11, 10, board));
	    boolean result = game.simulateMove(board, normalMove, black);
        assertTrue(result);

	    Move captureMove = new Move(new Point(10, 10, board));
	    boolean captureResult = game.simulateMove(board, captureMove, white);
        assertTrue(captureResult);
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(new Point(1, 1, board));
	    boolean result2 = game.simulateMove(board, normalMove2, black);
        assertTrue(result2);
        
	    Move normalMove3 = new Move(new Point(2, 2, board));
	    boolean result3 = game.simulateMove(board, normalMove3, white);
        assertTrue(result3);
        
        //now try to capture
	    Move captureMove2 = new Move(new Point(11, 10, board));
	    boolean captureResult2 = game.simulateMove(board, captureMove2, black);
        assertTrue(captureResult2);
        
        //now capture for white should not pass, since ko rule applies
	    Move captureMove3 = new Move(new Point(10, 10, board));
	    boolean captureResult3 = game.simulateMove(board, captureMove3, white);
        assertFalse(captureResult3);
    }
}
