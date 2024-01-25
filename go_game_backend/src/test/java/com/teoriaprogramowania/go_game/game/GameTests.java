package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;


public class GameTests {
	Client whiteClient = new Client();
	Client blackClient = new Client();
	Player white = new Player(whiteClient);
	Player black = new Player(blackClient);
	
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
        assertTrue(game.makeMove(normalMove));

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);
        assertTrue(game.makeMove(captureMove));
	    
        //now game should remember this state of the board.
	    Move koMove = new Move(11, 10, MoveType.NORMAL, black);
        assertFalse(game.makeMove(koMove));
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

        assertTrue(game.makeMove(normalMove));

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);

        assertTrue(game.makeMove(captureMove));
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(1, 1, MoveType.NORMAL, black);

        assertTrue(game.makeMove(normalMove2));
        
	    Move normalMove3 = new Move(2, 2, MoveType.NORMAL, white);

        assertTrue(game.makeMove(normalMove3));
        
        //now try to capture
	    Move captureMove2 = new Move(11, 10, MoveType.NORMAL, black);

        assertTrue(game.makeMove(captureMove2));
        
    }
    

    @Test
    void testKoRuleValidation3() {
        Board board = new Board(19);
        Game game = new Game(board);


        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
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

        assertTrue(game.makeMove(normalMove));

	    Move captureMove = new Move(10, 10, MoveType.NORMAL, white);

        assertTrue(game.makeMove(captureMove));
        
        //instead of ko move, make 2 random moves, and then try to capture once again
	    Move normalMove2 = new Move(1, 1, MoveType.NORMAL, black);

        assertTrue(game.makeMove(normalMove2));
        
	    Move normalMove3 = new Move(2, 2, MoveType.NORMAL, white);

        assertTrue(game.makeMove(normalMove3));
        
        //now try to capture
	    Move captureMove2 = new Move(11, 10, MoveType.NORMAL, black);

        assertTrue(game.makeMove(captureMove2));
        
        //now capture for white should not pass, since ko rule applies
	    Move captureMove3 = new Move(10, 10, MoveType.NORMAL, white);

        assertFalse(game.makeMove(captureMove3));
    }
    
    @Test
    public void testTwoPasses() {
    	int boardSize = 13;
    	Game game = new Game(boardSize);
    	
    	Move pass1 = new Move(-1, -1, MoveType.PASS, white);
    	Move pass2 = new Move(-1, -1, MoveType.PASS, black);
    	
    	assertTrue(game.makeMove(pass1));
    	assertTrue(game.makeMove(pass2));
    	
    	boolean gameFinished = game.hasChangedState();
    	assertTrue(gameFinished);
    }

    @Test
    public void testSurrender() {
    	int boardSize = 13;
    	Game game = new Game(boardSize);
    	
    	Move surr = new Move(-1, -1, MoveType.SURRENDER, black);
    	
    	assertTrue(game.makeMove(surr));
    	
    	boolean gameFinished = game.hasChangedState();
    	assertTrue(gameFinished);
    }
    

    @Test
    public void testMoveOutOfBoard() {
    	Board board = new Board(19);
    	Game game = new Game(board);
    	
    	Move m1 = new Move(4, 4, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(m1));
    	
    	Move m2 = new Move(20, 20, MoveType.NORMAL, black);
    	assertFalse(game.makeMove(m2));
    }
    
    @Test
    void testRemoveStoneAddBreath() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	move = new Move(-1, -1, MoveType.PASS, black);
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
    void testJoiningStones() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
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

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	move = new Move(1, 1, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	
    	move = new Move(1, 2, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, black);
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

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	move = new Move(0, 0, MoveType.NORMAL, white);
    	game.makeMove(move);

    	sg2 = board.getPoint(1, 0).getStoneGroup();
    	assertEquals(5, sg2.getStones().size());
    	assertEquals(6, sg2.getBreaths().size());

    	
    	
    	Point emptyPoint = board.getPoint(0, 1);
    	assertEquals(null, emptyPoint.getStoneGroup());
    	assertTrue(emptyPoint.isEmpty());
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	assertFalse(game.makeMove(move));


    	emptyPoint = board.getPoint(0, 1);
    	assertEquals(null, emptyPoint.getStoneGroup());
    	assertTrue(emptyPoint.isEmpty());
    	
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

        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
    	game.makeMove(move);
    	
    	move = new Move(1, 1, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
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
    	

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	
    	move = new Move(1, 2, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	move = new Move(2, 2, MoveType.NORMAL, white);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
    	//this stone group should have 5 breaths
    	assertEquals(5, board.getPoint(2, 2).getStoneGroup().getBreaths().size());
    	
    	move = new Move(2, 0, MoveType.NORMAL, white);
    	game.makeMove(move);
    	
    	//black stones should have 1 breath before capture
    	assertEquals(1, board.getPoint(0, 0).getStoneGroup().getBreaths().size());

    	move = new Move(-1, -1, MoveType.PASS, black);
    	game.makeMove(move);
    	
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
       	assertTrue(game.makeMove(move));

    	move = new Move(-1, -1, MoveType.PASS, white);
    	game.makeMove(move);
       	
    	move = new Move(1, 1, MoveType.NORMAL, black);       	
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
    	game.makeMove(move);
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	game.makeMove(move);

    	move = new Move(-1, -1, MoveType.PASS, white);
    	game.makeMove(move);
    	
    	assertEquals(1, board.getPoint(0, 1).getStoneGroup().getBreaths().size());

    	move = new Move(0, 0, MoveType.NORMAL, black);
    	assertFalse(game.makeMove(move));


    	assertTrue(board.getPoint(0, 0).isEmpty());
    	assertEquals(1, board.getPoint(0, 1).getStoneGroup().getBreaths().size());
    	
    	assertTrue(board.getPoint(0, 0).isEmpty());
    	
    	
    }
    
    @Test
    void testGameWithKo(){
    	Board board = new Board(9);
    	Game game = new Game(board);

        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
    	Move move = new Move(0, 1, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));


    	move = new Move(3, 1, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	
    	assertEquals(1, board.getPoint(3, 1).getStoneGroup().getStones().size());
    	assertEquals(4, board.getPoint(3, 1).getStoneGroup().getBreaths().size());

    	move = new Move(1, 2, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));


    	move = new Move(2, 2, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));


    	move = new Move(1, 0, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));


    	move = new Move(2, 0, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	
    	//now check ko situation
    	
    	move = new Move(2, 1, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));

    	
    	assertEquals(1, board.getPoint(2, 1).getStoneGroup().getBreaths().size());
    	
    	//now white captures
    	Move captureMove = new Move(1, 1, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(captureMove));


    	int movesSizeBeforeWrongMove = game.getMoves().size();

    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertFalse(board.getPoint(1, 1).isEmpty());
    	
    	//now move should be impossible
    	Move wrongMove = new Move(2, 1, MoveType.NORMAL, black);
    	assertFalse(game.makeMove(wrongMove));

    	
    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	
    	assertEquals(movesSizeBeforeWrongMove, game.getMoves().size());
    	
    	//ok so make some random moves and that move should be legal again
    	
    	move = new Move(0, 8, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
    	
    	//random pass
    	move = new Move(-1, -1, MoveType.PASS, white);
    	assertTrue(game.makeMove(move));

    	assertTrue(board.getPoint(2, 1).isEmpty());
    	assertEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertFalse(board.getPoint(1, 1).isEmpty());
    	
    	//should be legal now
    	movesSizeBeforeWrongMove = game.getMoves().size();

    	assertTrue(game.makeMove(wrongMove));

    	assertFalse(board.getPoint(2, 1).isEmpty());
    	assertNotEquals(null, board.getPoint(2, 1).getStoneGroup());
    	assertTrue(board.getPoint(1, 1).isEmpty());
    	
    	assertEquals(movesSizeBeforeWrongMove + 1, game.getMoves().size());
    	
    	//now capture move for white should be illegal
    	Move wrongMove2 = new Move(1, 1, MoveType.NORMAL, white);

    	assertFalse(game.makeMove(wrongMove2));
    	
    	assertFalse(board.getPoint(2, 1).isEmpty());
    	assertTrue(board.getPoint(1, 1).isEmpty());
    	assertEquals(movesSizeBeforeWrongMove + 1, game.getMoves().size());	
    }
    
    @Test
    void testBigGroupCapture() {
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	Move move = new Move(0, 0, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(2, 0, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(0, 1, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(2, 1, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	move = new Move(1, 1, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(1, 2, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(1, 0, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(0, 2, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
    	
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
    	assertTrue(game.makeMove(move));
    	
    	move = new Move(2, 2, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
    	
    	assertEquals(9, board.getPoint(0, 2).getStoneGroup().getBreaths().size());
    	assertEquals(5, board.getPoint(0, 2).getStoneGroup().getStones().size());
    }   
    
    @Test
    void testBigGroupCaptureMiddleOfABoard() {
        Board board = new Board(9);
        Game game = new Game(board);

        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
        Move move = new Move(3, 3, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));

        move = new Move(2, 4, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
        
        move = new Move(3, 4, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));

        move = new Move(2, 3, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
        
        move = new Move(4, 3, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));

        move = new Move(5, 4, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));
        
        move = new Move(4, 4, MoveType.NORMAL, black);
    	assertTrue(game.makeMove(move));
        
        Move blackPass = new Move(-1, -1, MoveType.PASS, black);


        move = new Move(4, 5, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(blackPass));
        
        move = new Move(4, 2, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(blackPass));
        move = new Move(3, 5, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(blackPass));
        
        move = new Move(3, 2, MoveType.NORMAL, white);

    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(blackPass));
        move = new Move(5, 3, MoveType.NORMAL, white);
    	assertTrue(game.makeMove(move));


        
        assertTrue(board.getPoint(3, 3).isEmpty());
        assertTrue(board.getPoint(3, 4).isEmpty());
        assertTrue(board.getPoint(4, 3).isEmpty());
        assertTrue(board.getPoint(4, 4).isEmpty());
        
        
        
        move = new Move(3, 3, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(move));
    	
        Move whitePass = new Move(-1, -1, MoveType.PASS, white);

    	assertTrue(game.makeMove(whitePass));
        
        move = new Move(3, 4, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(whitePass));
        
        move = new Move(4, 3, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(move));

    	assertTrue(game.makeMove(whitePass));
        

        move = new Move(4, 4, MoveType.NORMAL, black);
    	assertFalse(game.makeMove(move));
        
    }
    
    @Test
    void testEstablishTerritory(){
    	Board board = new Board(9);
    	Game game = new Game(board);
    	
    	for(int i = 0; i < 9; ++i) {
    		Move blackMove = new Move(7, i, MoveType.NORMAL, black);
    		Move whiteMove = new Move(1, i, MoveType.NORMAL, white);


        	assertTrue(game.makeMove(blackMove));

        	assertTrue(game.makeMove(whiteMove));
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);
    	

    	assertTrue(game.makeMove(blackPass));

    	assertTrue(game.makeMove(whitePass));
    	
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


        	assertTrue(game.makeMove(blackMove));

        	assertTrue(game.makeMove(whiteMove));
    		
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//add stones which will be later considered dead
    	Move deadBlackMove = new Move(0, 4, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(deadBlackMove));
		
    	
    	Move deadWhiteMove = new Move(8, 4, MoveType.NORMAL, white);

    	assertTrue(game.makeMove(deadWhiteMove));

    	//check if everything went well
    	assertEquals(17, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);
    	

    	assertTrue(game.makeMove(blackPass));
    	assertTrue(game.makeMove(whitePass));
    	
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


        	assertTrue(game.makeMove(blackMove));

        	assertTrue(game.makeMove(whiteMove));
    		
    	}
    	
    	//check if everything went well
    	assertEquals(18, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//add stones which will be later considered dead
    	Move deadBlackMove = new Move(0, 4, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(deadBlackMove));
		
    	
    	Move deadWhiteMove = new Move(8, 4, MoveType.NORMAL, white);

    	assertTrue(game.makeMove(deadWhiteMove));
		

    	Move deadBlackMove2 = new Move(0, 5, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(deadBlackMove2));
		
    	
    	Move whitePass = new Move(-1, -1, MoveType.PASS, white);

    	assertTrue(game.makeMove(whitePass));
		

    	Move deadBlackMove3 = new Move(0, 6, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(deadBlackMove3));
		
    	    	

    	//check if everything went well
    	assertEquals(15, board.getPoint(1, 1).getStoneGroup().getBreaths().size());
    	assertEquals(9, board.getPoint(1, 1).getStoneGroup().getStones().size());
    	
    	//now get to negotiation stage by making to passes
    	
    	Move blackPass = new Move(-1, -1, MoveType.PASS, black);
    	whitePass = new Move(-1, -1, MoveType.PASS, white);

    	assertTrue(game.makeMove(whitePass));
		
    	assertTrue(game.makeMove(blackPass));

    	
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
    
    @Test
    void testUndoCapture() {
        Board board = new Board(9);
        Game game = new Game(board);
        
        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
        Move move = new Move(0, 0, MoveType.NORMAL, black);

    	assertTrue(game.makeMove(move));

        move = new Move(0, 1, MoveType.NORMAL, white);

    	assertTrue(game.makeMove(move));
        
        game.undo();
        
        assertEquals(1, game.getMoves().size());
        assertTrue(game.getBoard().getPoint(move.getX(), move.getY()).isEmpty());

        move = new Move(0, 1, MoveType.NORMAL, white);
        assertTrue(game.makeMove(move));
        
        move = new Move(7, 7, MoveType.NORMAL, black);
        assertTrue(game.makeMove(move));

        move = new Move(1, 0, MoveType.NORMAL, white);
        assertTrue(game.makeMove(move));

        assertTrue(game.getBoard().getPoint(0, 0).isEmpty());
        
        int oldMovesSize = game.getMoves().size();
        
        game.undo();
        
        assertEquals(oldMovesSize - 1, game.getMoves().size());
        assertFalse(game.getBoard().getPoint(0, 0).isEmpty());
        assertTrue(game.getBoard().getPoint(1, 0).isEmpty());
        assertEquals(2, game.getBoard().getPoint(0, 1).getStoneGroup().getBreaths().size());

        assertFalse(game.getBoard().getPoint(7, 7).isEmpty());
        game.undo();
        assertTrue(game.getBoard().getPoint(7, 7).isEmpty());
        
        game.undo();
        assertEquals(oldMovesSize - 3, game.getMoves().size());
    }
    
    @Test
    void testSimulation(){
    	Board board = new Board(9);
        Game game = new Game(board);
        
        List<Player> players = new ArrayList<>();
        players.add(black);
        players.add(white);
        game.setPlayers(players);
        
    }
    
}
