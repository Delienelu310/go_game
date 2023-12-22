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
		point = new Point(4, 4, board);
		board.addPoint(point);
		
		Point neighborPoint = new Point(5, 4, board);
		board.addPoint(neighborPoint);
		neighborPoint.setStoneGroup(anotherStoneGroup);
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
	

	
	
}
