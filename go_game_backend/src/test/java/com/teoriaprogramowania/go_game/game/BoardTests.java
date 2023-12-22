package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTests {
	private Board board;
	private int boardSize1 = 9;
	private int boardSize2 = 13;
	private int boardSize3 = 19;
	
	@Test
	public void testBoardConstructor() {
		board = new Board(boardSize1);
		for(int i = 0; i < boardSize1; ++i) {
			for(int j = 0; j < boardSize1; ++j) {
				assertEquals(new Point(i, j, board), board.getPoint(i, j));
			}
		}
	}
	
	@Test
	public void testSetterGetter() {
		board = new Board(boardSize3);
		Point point = new Point(12, 15, board);
		board.addPoint(point);
		assertEquals(point, board.getPoint(12,15));
	}
	
	@Test
	public void testBoardSize() {
		board = new Board(boardSize2);
		assertEquals(boardSize2, board.getSize());
	}
	
	@Test
	public void testStoneGroupOnBoard() {
		Client c = new Client();
		Player p = new Player(c);
		board = new Board(boardSize3);
		Point point = new Point(12, 15, board);
		StoneGroup stoneGroup = new StoneGroup(point, p);
		point.setStoneGroup(stoneGroup);
		board.addPoint(point);
		
		Point check = board.getPoint(12, 15);
//		check.getStoneGroup();
		
		assertEquals(stoneGroup, check.getStoneGroup());
	}
	
	@Test
	public void testStoneGroupOnBoardDifferent() {
		Client c = new Client();
		Player p = new Player(c);
		board = new Board(boardSize3);
		Point point = new Point(12, 15, board);
		StoneGroup stoneGroup = new StoneGroup(point, p);
//		point.setStoneGroup(stoneGroup);
		board.addPoint(point);
		board.setPointStoneGroup(point, stoneGroup);
		
		Point check = board.getPoint(12, 15);
//		check.getStoneGroup();
		
		assertEquals(stoneGroup, check.getStoneGroup());
	}
}
