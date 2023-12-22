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
		assertEquals(4, whiteStoneGroup.getBreaths().size());
		assertEquals(1, whiteStoneGroup.getStones().size());
	}
	
	@Test
	public void testSameOwner() {
		Board board = new Board(13);
		
		Point p1 = new Point(3, 3, board);
		StoneGroup sg1 = new StoneGroup(p1, white);

		Point p2 = new Point(3, 4, board);
		StoneGroup sg2 = new StoneGroup(p2, white);
		
		assertEquals(sg1.getOwner(), sg2.getOwner());
	}
	
	@Test
	public void testDifferentOwner() {
		Board board = new Board(13);
		
		Point p1 = new Point(3, 3, board);
		StoneGroup sg1 = new StoneGroup(p1, white);

		Point p2 = new Point(3, 4, board);
		StoneGroup sg2 = new StoneGroup(p2, black);
		
		assertNotEquals(sg1.getOwner(), sg2.getOwner());
	}
	
	@Test
	void testJoinStoneGroup() {
	    Board board = new Board(19);
	    
	    // Create two points and their respective stone groups
	    Point point1 = new Point(10, 10, board);
	    board.addPoint(point1);
	    StoneGroup group1 = new StoneGroup(point1, white);
	    //point1.setStoneGroup(group1);
	    
	    Point point2 = new Point(10, 11, board);
	    board.addPoint(point2);
	    StoneGroup group2 = new StoneGroup(point2, white);
	    //point2.setStoneGroup(group2);

	    Set<Point> initialBreathsGroup1 = group1.getBreaths(); 										//should be 3
	    Set<Point> initialBreathsGroup2 = group2.getBreaths(); 										//should be 3
	    
	    
	    int expectedTotalStones = group1.getStones().size() + group2.getStones().size(); 			//2
	    int expectedTotalBreaths = initialBreathsGroup1.size() + initialBreathsGroup2.size();		//should be 6

	    group1.joinStoneGroup(group2, point2);

	    assertEquals(expectedTotalStones, group1.getStones().size());
	    assertEquals(expectedTotalBreaths, group1.getBreaths().size());
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
	void testRemoveBreath(){
		Board board = new Board(9);
		
		Point wPoint = new Point(3, 3, board);
		StoneGroup wsg = new StoneGroup(wPoint, white);
		board.addPoint(wPoint);
		
		int initialWhiteBreaths = wsg.getBreaths().size();
		
		Point bPoint = new Point(4, 3, board);
		StoneGroup bsg = new StoneGroup(bPoint, black);
		board.addPoint(bPoint);
		
		wsg.removeBreath(bPoint);
		
		assertEquals(initialWhiteBreaths - 1, wsg.getBreaths().size());
		assertEquals(3, bsg.getBreaths().size());
	}
	
	@Test
	void testBreathsNearbyEnemy() {
	    Board board = new Board(19);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);

	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    
	    Set<Point> breaths = wsg.getBreaths();
	    assertEquals(3, breaths.size());
	}
	
	@Test
	void testBreathsDifferentOrder() {
	    Board board = new Board(19);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);
	    
	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    
	    bsg.removeBreath(wPoint);

	    Set<Point> blackBreaths = bsg.getBreaths();
	    assertEquals(3, blackBreaths.size());
	
	    Set<Point> whiteBreaths = wsg.getBreaths();
	    assertEquals(3, whiteBreaths.size());
	
	}
	

	@Test
	void testBreathsNearbyEnemies() {
	    Board board = new Board(19);
	    
	    Point bPoint = new Point(10, 10, board);
	    StoneGroup bsg = new StoneGroup(bPoint, black);
	    board.addPoint(bPoint);

	    Point wPoint = new Point(11, 10, board);
	    StoneGroup wsg = new StoneGroup(wPoint, white);
	    board.addPoint(wPoint);
	    
	    Point w2 = new Point(9, 10, board);
	    StoneGroup wsg2 = new StoneGroup(w2, white);
	    board.addPoint(w2);

	    bsg.removeBreath(wPoint);
	    bsg.removeBreath(w2);
	    
	    Set<Point> breaths = bsg.getBreaths();
	    assertEquals(2, breaths.size());
	    assertEquals(3, wsg.getBreaths().size());
	    assertEquals(3, wsg2.getBreaths().size());
	}

	@Test
	void testBigGroup() {
	    Board board = new Board(19);
	    
	    Point b1 = new Point(10, 10, board);
	    StoneGroup bs1 = new StoneGroup(b1, black);
	    b1.setStoneGroup(bs1);
	    
	    Point b2 = board.getPoint(11, 10);
	    StoneGroup bs2 = new StoneGroup(b2, black);
	    
	    bs1.joinStoneGroup(bs2, b2);
	    
	    assertEquals(6, bs1.getBreaths().size());
	    
	    Point b3 = board.getPoint(11, 11);
	    StoneGroup bs3 = new StoneGroup(b3, black);
	    bs1.joinStoneGroup(bs3, b3);

	    assertEquals(7, bs1.getBreaths().size());
	    
	    Point b4 = board.getPoint(10, 11);
	    StoneGroup bs4 = new StoneGroup(b4, black);
	    bs1.joinStoneGroup(bs4, b4);

	    assertEquals(8, bs1.getBreaths().size());
	    
	}
	
}
