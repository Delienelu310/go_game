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
	//			assertNull(board.getPoint(i, j));
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
	
	
	
	
	
}
