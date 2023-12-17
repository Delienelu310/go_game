package com.teoriaprogramowania.go_game.game;

import java.util.Arrays;

public class Board {
	private Point[][] board;
	private final int size;
	
	//new board initialization
	public Board(int size){
		this.size = size;
        this.board = new Point[size][size];
        for(int i = 0; i < size; ++i) {
        	Arrays.fill(this.board[i], null);
        }
	}

	public Board(Point[][] board, int size) {
		this.board = board;
		this.size = size;
	}
	
	public Point[][] getBoard(){
		return board;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public Point getPoint(int x, int y) {
		return board[x][y];
	}
	
	public void addPoint(Point point) {
		this.board[point.getX()][point.getY()] = point;
	}
}
