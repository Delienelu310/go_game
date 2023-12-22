package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoneGroupTests {
	Player black = new Player(new Client());
	Player white = new Player(new Client());
	
	@Test
	public void testConstructor() {
		int size = 9;
		Board board = new Board(size);
		Point point = new Point(4, 4, board);
		StoneGroup whiteStoneGroup = new StoneGroup(point, white);
		
		Set<Point> correctStones = new HashSet<>();
		correctStones.add(point);

		Set<Point> correctBreaths = new HashSet<>();
		correctBreaths = point.getEmptyNeighborPoints();
		
		assertEquals(white, whiteStoneGroup.getOwner());
		assertEquals(correctStones, whiteStoneGroup.getStones());
		assertEquals(correctBreaths, whiteStoneGroup.getBreaths());
	}
	
	@Test
	public void testJoining() {
		int size = 13;
		Board board = new Board(size);
		
		Point point1 = new Point(4, 4, board);
		Point point2 = new Point(4, 5, board);
		
		StoneGroup whiteStoneGroup1 = new StoneGroup(point1, white);
		StoneGroup whiteStoneGroup2 = new StoneGroup(point2, white);
		
		whiteStoneGroup1.joinStoneGroup(whiteStoneGroup2, new Point(4, 5, board));
		
		Set<Point> breaths = point1.getEmptyNeighborPoints();
		breaths.addAll(point2.getEmptyNeighborPoints());
		breaths.remove(point1);
		breaths.remove(point2);
		
		Set<Point> stones = new HashSet<>();
		stones.add(point1);
		stones.add(point2);
		
		assertEquals(breaths, whiteStoneGroup1.getBreaths());
		assertEquals(stones, whiteStoneGroup1.getStones());
	}

	@Test
	void testBreathsSingleStoneOpenSpace() {
	    Board board = new Board(19);
	    Point point = new Point(10, 10, board);

	    StoneGroup stoneGroup = new StoneGroup(point, white);
	    point.setStoneGroup(stoneGroup);

	    Set<Point> breaths = stoneGroup.getBreaths();
	    assertEquals(4, breaths.size());
	}

	@Test
	void testBreathsSingleStoneEdge() {
	    Board board = new Board(19);
	    Point point = new Point(0, 10, board);

	    StoneGroup stoneGroup = new StoneGroup(point, white);
	    point.setStoneGroup(stoneGroup);

	    Set<Point> breaths = stoneGroup.getBreaths();
	    assertEquals(3, breaths.size());
	}

	@Test
	void testBreathsSingleStoneCorner() {
	    Board board = new Board(19);
	    Point point = new Point(0, 0, board); // Place the stone in the corner

	    StoneGroup stoneGroup = new StoneGroup(point, white);
	    point.setStoneGroup(stoneGroup);

	    Set<Point> breaths = stoneGroup.getBreaths();
	    assertEquals(2, breaths.size());
	}
	
	@Test
	void testBreathsNearbyEnemy() {
	    Board board = new Board(19);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);
	    bPoint.setStoneGroup(bsg);
	    
	    board.addPoint(bPoint);

	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    wPoint.setStoneGroup(wsg);
	    
	    board.addPoint(wPoint);
	    
	    Set<Point> breaths = wsg.getBreaths();
	    assertEquals(3, breaths.size());
	}
	
	@Test
	void testBreathsDifferentOrder() {
	    Board board = new Board(19);

	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    wPoint.setStoneGroup(wsg);
	    board.addPoint(wPoint);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);
	    bPoint.setStoneGroup(bsg);
	    board.addPoint(bPoint);
	    board.setPointStoneGroup(bPoint, bsg);
	    
	    Set<Point> breaths = wsg.getBreaths();
	    assertEquals(3, breaths.size());
	}
	

	@Test
	void testBreathsNearbyEnemies() {
	    Board board = new Board(19);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);
	    bPoint.setStoneGroup(bsg);
	    board.addPoint(bPoint);

	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    wPoint.setStoneGroup(wsg);
	    board.addPoint(wPoint);
	    
	    Point w2 = new Point(9, 10, board);
	    StoneGroup wsg2 = new StoneGroup(w2, white);
	    board.setPointStoneGroup(w2, wsg2);
	    
	    Set<Point> breaths = bsg.getBreaths();
	    assertEquals(2, breaths.size());
	}
	
}
