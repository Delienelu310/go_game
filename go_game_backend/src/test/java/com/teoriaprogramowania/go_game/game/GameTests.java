package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.*;


public class GameTests {
	Client whiteClient = new Client();
	Client blackClient = new Client();
	Player white = new Player(whiteClient);
	Player black = new Player(blackClient);
	
	@Test
    void testNormalMoveValidation() {
        Board board = new Board(19);
        Game game = new Game(board);
        Move normalMove = new Move(10, 10, MoveType.NORMAL, white);

        boolean result = game.simulateMove(board, normalMove);

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
	    
        Move suicideMove = new Move(10, 10, MoveType.NORMAL, white);
        
        boolean result = game.simulateMove(board, suicideMove);

        assertFalse(result, "Suicide move should be invalid.");
    }

    @Test
    void testCapturingMoveValidation() {
        Board board = new Board(19);
        Game game = new Game(board);
        

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

        Move capturingMove = new Move(10, 11, MoveType.NORMAL, white);
        boolean result = game.simulateMove(board, capturingMove);

        // Verify
        assertTrue(result);
    }

    @Test
    void testCaptureMoveRuleValidation() {
        Board board = new Board(19);
        Game game = new Game(board);

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
	    
        Move captureMove = new Move(10, 10, MoveType.NORMAL, white);
	    
        boolean result = game.simulateMove(board, captureMove);

        assertTrue(result);
    }

    @Test
    void testKoRuleValidation() {
        Board board = new Board(19);
        Game game = new Game(board);

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
	    
	    Move normalMove = new Move(11, 10 ,MoveType.NORMAL, black);
	    boolean result = game.simulateMove(board, normalMove);
        assertTrue(result);
        game.makeMove(normalMove);

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);
	    boolean captureResult = game.simulateMove(board, captureMove);
        assertTrue(captureResult);
        game.makeMove(captureMove);
	    
        //now game should remember this state of the board.
	    Move koMove = new Move(11, 10, MoveType.NORMAL, black);
	    boolean koResult = game.simulateMove(board, koMove);
        assertFalse(koResult);
    }
    
    @Test
    void testKoRuleValidation2() {
        Board board = new Board(19);
        Game game = new Game(board);

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
	    
	    Move normalMove = new Move(11, 10, MoveType.NORMAL, black);
	    boolean result = game.simulateMove(board, normalMove);
        assertTrue(result);
        game.makeMove(normalMove);

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);
	    boolean captureResult = game.simulateMove(board, captureMove);
        assertTrue(captureResult);
        game.makeMove(captureMove);
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(1, 1, MoveType.NORMAL, black);
	    boolean result2 = game.simulateMove(board, normalMove2);
        assertTrue(result2);
        game.makeMove(normalMove2);
        
	    Move normalMove3 = new Move(2, 2, MoveType.NORMAL, white);
	    boolean result3 = game.simulateMove(board, normalMove3);
        assertTrue(result3);
        game.makeMove(normalMove3);
        
        //now try to capture
	    Move captureMove2 = new Move(11, 10, MoveType.NORMAL, black);
	    boolean captureResult2 = game.simulateMove(board, captureMove2);
        assertTrue(captureResult2);
        
    }
    

    @Test
    void testKoRuleValidation3() {
        Board board = new Board(19);
        Game game = new Game(board);

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
	    
	    Move normalMove = new Move(11, 10, MoveType.NORMAL, black);
	    boolean result = game.simulateMove(board, normalMove);
        assertTrue(result);
        game.makeMove(normalMove);

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);
	    boolean captureResult = game.simulateMove(board, captureMove);
        assertTrue(captureResult);
        game.makeMove(captureMove);
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(1, 1, MoveType.NORMAL, black);
	    boolean result2 = game.simulateMove(board, normalMove2);
        assertTrue(result2);
        game.makeMove(normalMove2);
        
	    Move normalMove3 = new Move(2, 2, MoveType.NORMAL, white);
	    boolean result3 = game.simulateMove(board, normalMove3);
        assertTrue(result3);
        game.makeMove(normalMove3);
        
        //now try to capture
	    Move captureMove2 = new Move(11, 10, MoveType.NORMAL, black);
	    boolean captureResult2 = game.simulateMove(board, captureMove2);
        assertTrue(captureResult2);
        game.makeMove(captureMove2);
        
        //now capture for white should not pass, since ko rule applies
	    Move captureMove3 = new Move(10, 10, MoveType.NORMAL, white);
	    boolean captureResult3 = game.simulateMove(board, captureMove3);
        assertFalse(captureResult3);
    }
    
    @Test
    public void testTwoPasses() {
    	int boardSize = 13;
    	Game game = new Game(boardSize);
    	
    	Move pass1 = new Move(-1, -1, MoveType.PASS, white);
    	Move pass2 = new Move(-1, -1, MoveType.PASS, black);
    	
    	boolean validation1 = game.isMoveValid(pass1);
    	assertTrue(validation1);

    	boolean validation2 = game.isMoveValid(pass2);
    	assertTrue(validation2);
    	
    	game.makeMove(pass1);
    	game.makeMove(pass2);
    	
    	boolean gameFinished = game.hasChangedState();
    	assertTrue(gameFinished);
    }

    @Test
    public void testSurrender() {
    	int boardSize = 13;
    	Game game = new Game(boardSize);
    	
    	Move surr = new Move(-1, -1, MoveType.SURRENDER, black);
    	
    	boolean validation1 = game.isMoveValid(surr);
    	assertTrue(validation1);
    	
    	game.makeMove(surr);
    	
    	boolean gameFinished = game.hasChangedState();
    	assertTrue(gameFinished);
    }
    

    @Test
    public void testMoveOutOfBoard() {
    	Board board = new Board(19);
    	Game game = new Game(board);
    	
    	Move m1 = new Move(4, 4, MoveType.NORMAL, white);
    	boolean v1 = game.isMoveValid(m1);
    	assertTrue(v1);
    	
    	Move m2 = new Move(20, 20, MoveType.NORMAL, black);
    	boolean v2 = game.isMoveValid(m2);
    	assertFalse(v2);
    }
    
    @Test
    void testRemoveStoneAddBreath() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(1, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	StoneGroup sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(null, sg1);
    	assertTrue(board.getPoint(0, 0).isEmpty());
    	
    	sg1 = board.getPoint(0, 1).getStoneGroup();
    	assertEquals(3, sg1.getBreaths().size());
    	assertEquals(1, sg1.getStones().size());    	
    
    	sg1 = board.getPoint(1, 0).getStoneGroup();
    	assertEquals(3, sg1.getBreaths().size());
    	assertEquals(1, sg1.getStones().size());   
    }
    

    @Test
    void testRemoveStoneAddBreathBiggerGroup() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(1, 0, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(1, 1, MoveType.NORMAL, black);
    	game.makeMove(move);

    	
    	
    	move = new Move(2, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(2, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(2, 2, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(1, 2, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(0, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	move = new Move(0, 0, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(1, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(1, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	StoneGroup sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(9, sg1.getStones().size());    
    	assertEquals(6, sg1.getBreaths().size());
    }
    
    @Test
    void testJoiningStones() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	StoneGroup sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(1, sg1.getStones().size());
    	assertEquals(2, sg1.getBreaths().size());
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(2, sg1.getStones().size());
    	assertEquals(3, sg1.getBreaths().size());
    	
    	move = new Move(1, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(2, sg1.getStones().size());
    	assertEquals(2, sg1.getBreaths().size());

    	StoneGroup sg2 = board.getPoint(1, 0).getStoneGroup();
    	assertEquals(1, sg2.getStones().size());
    	assertEquals(2, sg2.getBreaths().size());
    	
    	move = new Move(1, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(1, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(2, sg1.getStones().size());
    	assertEquals(1, sg1.getBreaths().size());

    	sg2 = board.getPoint(1, 0).getStoneGroup();
    	assertEquals(3, sg2.getStones().size());
    	assertEquals(5, sg2.getBreaths().size());

    	move = new Move(0, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	sg1 = board.getPoint(0, 0).getStoneGroup();
    	assertEquals(sg1, null);
    	sg1 = board.getPoint(0, 1).getStoneGroup();
    	assertEquals(sg1, null);
    	
    	sg2 = board.getPoint(1, 0).getStoneGroup();
    	
    	assertEquals(4, sg2.getStones().size());
    	assertEquals(7, sg2.getBreaths().size());

    	move = new Move(0, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	sg2 = board.getPoint(1, 0).getStoneGroup();
    	assertEquals(5, sg2.getStones().size());
    	assertEquals(6, sg2.getBreaths().size());

    	
    	
    	Point emptyPoint = board.getPoint(0, 1);
    	assertEquals(null, emptyPoint.getStoneGroup());
    	assertTrue(emptyPoint.isEmpty());
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	assertFalse(game.isMoveValid(move));

    	emptyPoint = board.getPoint(0, 1);
    	assertEquals(null, emptyPoint.getStoneGroup());
    	assertTrue(emptyPoint.isEmpty());
    	
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	emptyPoint = board.getPoint(0, 1);
    	assertEquals(null, emptyPoint.getStoneGroup());
    	assertTrue(emptyPoint.isEmpty());
    }
    
    @Test
    void testGame() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Player white = new Player(new Client());
    	Player black = new Player(new Client());
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);
    	move = new Move(1, 1, MoveType.NORMAL, black);
    	game.makeMove(move);
    	move = new Move(1, 0, MoveType.NORMAL, black);
    	game.makeMove(move);

    	for(int k = 0; k < 2; ++k) {
    		for(int l = 0; l < 2; ++l) {
    			//each black stone should belong to the same stone group
    			assertEquals(board.getPoint(k, l).getStoneGroup(), board.getPoint(0, 0).getStoneGroup());
    		}
    	}
    	
    	//black stones should have 4 breaths
    	assertEquals(4, board.getPoint(0, 0).getStoneGroup().getBreaths().size());
    	
    	move = new Move(0, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	move = new Move(1, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	move = new Move(2, 2, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	//this stone group should have 5 breaths
    	assertEquals(5, board.getPoint(2, 2).getStoneGroup().getBreaths().size());
    	
    	move = new Move(2, 0, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	//black stones should have 1 breath before capture
    	assertEquals(1, board.getPoint(0, 0).getStoneGroup().getBreaths().size());
    	
    	move = new Move(2, 1, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	
    	int i = 0;
    	int j = 2;
    	for(i = 0; i < 3; ++i) {
    		//each white stone should belong to the white group
        	assertEquals(board.getPoint(i, j).getStoneGroup(), board.getPoint(j, 0).getStoneGroup());    		
        	assertEquals(board.getPoint(i, j).getStoneGroup(), board.getPoint(j, 1).getStoneGroup());  		
        	assertEquals(board.getPoint(i, j).getStoneGroup(), board.getPoint(j, 2).getStoneGroup());
    	}

    	//white player should have 4 captives after capture
    	assertEquals(4, white.getCaptives().size());
    	
       	for(int k = 0; k < 2; ++k) {
    		for(int l = 0; l < 2; ++l) {
    			//points where black stones were now should be empty
    			assertTrue(board.getPoint(k, l).isEmpty());
    			assertEquals(null, board.getPoint(k, l).getStoneGroup());
    		}
    	}

    	//white stone group should regain breaths after capturing black stones
    	assertEquals(9, board.getPoint(2, 1).getStoneGroup().getBreaths().size());
       	
       	//now moving into that empty space should be legal
       	move = new Move(1, 0, MoveType.NORMAL, black);       	
       	assertTrue(game.isMoveValid(move));
    	game.makeMove(move);
       	
    	move = new Move(1, 1, MoveType.NORMAL, black);       	
    	game.makeMove(move);

    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	assertEquals(1, board.getPoint(0, 1).getStoneGroup().getBreaths().size());

    	move = new Move(0, 0, MoveType.NORMAL, black);
    	assertFalse(game.isMoveValid(move));
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	assertTrue(board.getPoint(0, 0).isEmpty());
    	assertEquals(1, board.getPoint(0, 1).getStoneGroup().getBreaths().size());
    	
    	assertTrue(board.getPoint(0, 0).isEmpty());
    	
    	
    }
    
    @Test
    void testGameWithKo(){
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 1, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(3, 1, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	assertEquals(1, board.getPoint(3, 1).getStoneGroup().getStones().size());
    	assertEquals(4, board.getPoint(3, 1).getStoneGroup().getBreaths().size());

    	move = new Move(1, 2, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(2, 2, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(1, 0, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(2, 0, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	//now check ko situation
    	
    	move = new Move(2, 1, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	assertEquals(1, board.getPoint(2, 1).getStoneGroup().getBreaths().size());
    	
    	//now white captures
    	Move captureMove = new Move(1, 1, MoveType.NORMAL, white);
     	if(game.isMoveValid(captureMove)) {
    		game.makeMove(captureMove);
    	}

    	int movesSizeBeforeWrongMove = game.getMoves().size();

    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertFalse(board.getPoint(1, 1).isEmpty());
    	
    	//now move should be impossible
    	Move wrongMove = new Move(2, 1, MoveType.NORMAL, black);
    	assertFalse(game.isMoveValid(wrongMove));
    	if(game.isMoveValid(wrongMove)) {
    		game.makeMove(wrongMove);
    	}
    	
    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	
    	assertEquals(movesSizeBeforeWrongMove, game.getMoves().size());
    	
    	//ok so make some random moves and that move should be legal again
    	
    	move = new Move(0, 8, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	//random pass
    	move = new Move(-1, -1, MoveType.PASS, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertFalse(board.getPoint(1, 1).isEmpty());
    	
    	//should be legal now
    	movesSizeBeforeWrongMove = game.getMoves().size();
    	if(game.isMoveValid(wrongMove)) {
    		game.makeMove(wrongMove);
    	}

    	assertFalse(board.getPoint(2, 1).isEmpty());
    	assertNotEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertTrue(board.getPoint(1, 1).isEmpty());
    	
    	assertEquals(movesSizeBeforeWrongMove + 1, game.getMoves().size());
    	
    	//now capture move for white should be illegal
    	Move wrongMove2 = new Move(1, 1, MoveType.NORMAL, white);
    	if(game.isMoveValid(wrongMove2)) {
    		System.out.println("cos poszlo zle");
    		game.makeMove(wrongMove2);
    	}
    	
    	assertFalse(board.getPoint(2, 1).isEmpty());
    	assertTrue(board.getPoint(1, 1).isEmpty());
    	assertEquals(movesSizeBeforeWrongMove + 1, game.getMoves().size());	
    }
    
    @Test
    void testBigGroupCapture() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(2, 0, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(2, 1, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(1, 1, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(1, 2, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	move = new Move(1, 0, MoveType.NORMAL, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(0, 2, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	assertTrue(board.getPoint(0, 0).isEmpty());
    	assertTrue(board.getPoint(0, 1).isEmpty());
    	assertTrue(board.getPoint(1, 1).isEmpty());
    	assertTrue(board.getPoint(1, 0).isEmpty());
    	
    	assertEquals(5, board.getPoint(2, 0).getStoneGroup().getBreaths().size());
    	assertEquals(2, board.getPoint(2, 0).getStoneGroup().getStones().size());

    	assertEquals(5, board.getPoint(0, 2).getStoneGroup().getBreaths().size());
    	assertEquals(2, board.getPoint(0, 2).getStoneGroup().getStones().size());
    	
    	assertEquals(board.getPoint(0, 2).getStoneGroup(), board.getPoint(1, 2).getStoneGroup());
    	
    	//does joining work?
    	
    	move = new Move(-1, -1, MoveType.PASS, black);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}
    	
    	move = new Move(2, 2, MoveType.NORMAL, white);
    	if(game.isMoveValid(move)) {
    		game.makeMove(move);
    	}

    	assertEquals(9, board.getPoint(0, 2).getStoneGroup().getBreaths().size());
    	assertEquals(5, board.getPoint(0, 2).getStoneGroup().getStones().size());
    }   
    
    @Test
    void testBigGroupCaptureMiddleOfABoard() {
        Board board = new Board(9);
        Game game = new Game(board);
        
        Move move = new Move(3, 3, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }

        move = new Move(2, 4, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        
        move = new Move(3, 4, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }

        move = new Move(2, 3, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        
        move = new Move(4, 3, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }

        move = new Move(5, 4, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        
        move = new Move(4, 4, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        
        Move blackPass = new Move(-1, -1, MoveType.PASS, black);


        move = new Move(4, 5, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        
        if(game.isMoveValid(blackPass)) {
        	game.makeMove(blackPass);
        }
        
        move = new Move(4, 2, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        if(game.isMoveValid(blackPass)) {
        	game.makeMove(blackPass);
        }
        
        move = new Move(3, 5, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        if(game.isMoveValid(blackPass)) {
        	game.makeMove(blackPass);
        }
        
        move = new Move(3, 2, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        if(game.isMoveValid(blackPass)) {
        	game.makeMove(blackPass);
        }
        
        move = new Move(5, 3, MoveType.NORMAL, white);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }


        
        assertTrue(board.getPoint(3, 3).isEmpty());
        assertTrue(board.getPoint(3, 4).isEmpty());
        assertTrue(board.getPoint(4, 3).isEmpty());
        assertTrue(board.getPoint(4, 4).isEmpty());
        
        
        
        move = new Move(3, 3, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        Move whitePass = new Move(-1, -1, MoveType.PASS, white);
        if(game.isMoveValid(whitePass)) {
            game.makeMove(move);
        }
        
        move = new Move(3, 4, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        if(game.isMoveValid(whitePass)) {
            game.makeMove(move);
        }
        
        move = new Move(4, 3, MoveType.NORMAL, black);
        if(game.isMoveValid(move)) {
            game.makeMove(move);
        }
        if(game.isMoveValid(whitePass)) {
            game.makeMove(move);
        }
        

        move = new Move(4, 4, MoveType.NORMAL, black);
        assertFalse(game.isMoveValid(move));
        
    }
    
    @Test
    void testEstablishTerritory(){
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	for(int i = 0; i < 9; ++i) {
    		Move blackMove = new Move(7, i, MoveType.NORMAL, black);
    		Move whiteMove = new Move(1, i, MoveType.NORMAL, white);

    		if(game.isMoveValid(blackMove )) {
    			game.makeMove(blackMove );
    		}
    		
    		if(game.isMoveValid(whiteMove )) {
    			game.makeMove(whiteMove );
    		}
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);
    	
    	if(game.isMoveValid(blackPass)) {
    		game.makeMove(blackPass);
    	}
    	if(game.isMoveValid(whitePass)) {
    		game.makeMove(whitePass);
    	}
    	
    	assertTrue(game.hasChangedState());
    	
    	assertEquals(State.NEGOTIATION, game.getState());

    	game.agreeToFinalize(black);
    	game.agreeToFinalize(white);
    	
    	if(game.bothPlayersAgreed()) {
    		game.finalizeGame();
    	}
    	
    	game.setScore(white, black);
    	assertEquals(9, game.getPlayerScore(black));
    	assertEquals(9, game.getPlayerScore(white));
    }
    
    @Test
    void testDeadStonesEstablishTerritory() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	for(int i = 0; i < 9; ++i) {
    		Move blackMove = new Move(7, i, MoveType.NORMAL, black);
    		Move whiteMove = new Move(1, i, MoveType.NORMAL, white);

    		if(game.isMoveValid(blackMove )) {
    			game.makeMove(blackMove );
    		}
    		
    		if(game.isMoveValid(whiteMove )) {
    			game.makeMove(whiteMove );
    		}
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//add stones which will be later considered dead
    	Move deadBlackMove = new Move(0, 4, MoveType.NORMAL, black);
    	if(game.isMoveValid(deadBlackMove)) {
    		game.makeMove(deadBlackMove);
    	}
    	
    	Move deadWhiteMove = new Move(8, 4, MoveType.NORMAL, white);
    	if(game.isMoveValid(deadWhiteMove)) {
    		game.makeMove(deadWhiteMove);
    	}

    	//check if everything went well
    	assertEquals(17, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);
    	
    	if(game.isMoveValid(blackPass)) {
    		game.makeMove(blackPass);
    	}
    	if(game.isMoveValid(whitePass)) {
    		game.makeMove(whitePass);
    	}
    	
    	//game should be in negotiation state
    	assertTrue(game.hasChangedState());
    	assertEquals(State.NEGOTIATION, game.getState());
    	
    	//pick dead stone groups
    	game.pickDeadStoneGroup(8, 4);
    	
    	game.agreeToFinalize(black);
    	game.agreeToFinalize(white);
    	
    	//now one territory should be corrupted with enemy stone not considered dead, so 4 territories
    	if(game.bothPlayersAgreed()) {
    		game.finalizeGame();
    	}
    	
    	game.setScore(white, black);
    	assertEquals(9, game.getPlayerScore(black));
    	assertEquals(0, game.getPlayerScore(white));
    }
    
    @Test
    void testDeadStonesBiggerGroupEstablishTerritory() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	for(int i = 0; i < 9; ++i) {
    		Move blackMove = new Move(7, i, MoveType.NORMAL, black);
    		Move whiteMove = new Move(1, i, MoveType.NORMAL, white);

    		if(game.isMoveValid(blackMove )) {
    			game.makeMove(blackMove );
    		}
    		
    		if(game.isMoveValid(whiteMove )) {
    			game.makeMove(whiteMove );
    		}
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//add stones which will be later considered dead
    	Move deadBlackMove = new Move(0, 4, MoveType.NORMAL, black);
    	if(game.isMoveValid(deadBlackMove)) {
    		game.makeMove(deadBlackMove);
    	}
    	
    	Move deadWhiteMove = new Move(8, 4, MoveType.NORMAL, white);
    	if(game.isMoveValid(deadWhiteMove)) {
    		game.makeMove(deadWhiteMove);
    	}

    	Move deadBlackMove2 = new Move(0, 5, MoveType.NORMAL, black);
    	if(game.isMoveValid(deadBlackMove2)) {
    		game.makeMove(deadBlackMove2);
    	}
    	
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);
    	if(game.isMoveValid(whitePass)) {
    		game.makeMove(whitePass);
    	}

    	Move deadBlackMove3 = new Move(0, 6, MoveType.NORMAL, black);
    	if(game.isMoveValid(deadBlackMove3)) {
    		game.makeMove(deadBlackMove3);
    	}
    	    	

    	//check if everything went well
    	assertEquals(15, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	whitePass = new Move(-1, -1, MoveType.PASS, white);

    	if(game.isMoveValid(whitePass)) {
    		game.makeMove(whitePass);
    	}
    	if(game.isMoveValid(blackPass)) {
    		game.makeMove(blackPass);
    	}
    	
    	//game should be in negotiation state
    	assertTrue(game.hasChangedState());
    	assertEquals(State.NEGOTIATION, game.getState());
    	
    	//pick dead stone groups
    	game.pickDeadStoneGroup(8, 4);
    	
    	//adding same player 2 times shouldnt change anything
    	game.agreeToFinalize(black);
    	game.agreeToFinalize(black);
    	game.agreeToFinalize(white);
    	
    	//now one territory should be corrupted with enemy stone not considered dead, so 4 territories
    	if(game.bothPlayersAgreed()) {
    		game.finalizeGame();
    	}
    	
    	game.setScore(white, black);
    	assertEquals(9, game.getPlayerScore(black));
    	assertEquals(0, game.getPlayerScore(white));
    }
}
