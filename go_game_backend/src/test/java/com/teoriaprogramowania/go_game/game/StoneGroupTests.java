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

}
