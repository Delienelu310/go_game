package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTests {
	private Board board;
	private Point point;
	private StoneGroup stoneGroup;
	private StoneGroup anotherStoneGroup;
	private int boardSize = 9;
	
	@Test
	public void testGetters() {
		board = new Board(boardSize);
		point = new Point(4, 4, board);
		
		assertEquals(4, point.getX());
		assertEquals(4, point.getY());
		assertNull(point.getStoneGroup());
		
		Player white = new Player(new Client());
		stoneGroup = new StoneGroup(point, white);
		point.setStoneGroup(stoneGroup);
		
		assertEquals(stoneGroup, point.getStoneGroup());
	}
	
	@Test
	public void testIsEmpty() {
		board = new Board(boardSize);
		Point newPoint = new Point(1, 1, board);
		assertTrue(newPoint.isEmpty());
		
		Client c = new Client();
		Player p = new Player(c);
		StoneGroup stoneGroup = new StoneGroup(newPoint, p);
		newPoint.setStoneGroup(stoneGroup);
		assertFalse(newPoint.isEmpty());
	}
	
	@Test
	public void testGetNeighborStoneGroups() {
		board = new Board(boardSize);
		
		Player white = new Player(new Client());
		Player black = new Player(new Client());

		point = new Point(4, 4, board);
		stoneGroup = new StoneGroup(point, white);

		Point neighborPoint = new Point(5, 4, board);
		anotherStoneGroup = new StoneGroup(point, black);
		
		point.setStoneGroup(stoneGroup);
		neighborPoint.setStoneGroup(anotherStoneGroup);

		board.addPoint(point);
		board.addPoint(neighborPoint);

		Set<StoneGroup> neighborStoneGroups = new HashSet<>();
		neighborStoneGroups.add(anotherStoneGroup);
		
		assertEquals(neighborStoneGroups, point.getNeighborStoneGroups());
	}
	
	@Test
	public void testGetEmptyNeighborPoints() {
		board = new Board(boardSize);
		point = new Point(3, 3, board);
		
		Point p1 = new Point(3, 2, board);
		Point p2 = new Point(3, 4, board);
		Point p3 = new Point(2, 3, board);
		Point p4 = new Point(4, 3, board);

		board.addPoint(point);
		board.addPoint(p1);
		board.addPoint(p2);
		board.addPoint(p3);
		board.addPoint(p4);

		Set<Point> correctEmptyNeighbors = new HashSet<>();
		correctEmptyNeighbors.add(p1);
		correctEmptyNeighbors.add(p2);
		correctEmptyNeighbors.add(p3);
		correctEmptyNeighbors.add(p4);
		
		assertEquals(correctEmptyNeighbors, point.getEmptyNeighborPoints());
	}
	
	void testEnemyNeighborsLoseBreath() {
	    // Setup
		Player player = new Player(new Client());		
		Point point = new Point(10, 10, board);
	    StoneGroup playerGroup = new StoneGroup(point, player);
	    point.setStoneGroup(playerGroup);	    
	    int initialBreaths = playerGroup.getBreaths().size();

		Player enemy = new Player(new Client());
		Point enemyPoint = new Point(10, 11, board);
	    StoneGroup enemyGroup = new StoneGroup(enemyPoint, enemy);
	    enemyPoint.setStoneGroup(enemyGroup);

	    assertEquals(initialBreaths - 1, playerGroup.getBreaths().size());
	}
}
