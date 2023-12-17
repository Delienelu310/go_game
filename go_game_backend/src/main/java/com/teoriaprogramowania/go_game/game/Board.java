package com.teoriaprogramowania.go_game.game;

import java.util.Arrays;

public class Board {
	private Point[][] board;
	private int size;
	
	public Board(int size){
		this.size = size;
        this.board = new Point[size-1][size-1];
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
	/*
	public void addStone(Stone stone) {
		this.board[stone.getX()][stone.getY()] = stone.getColor();
	}
	*/
	public Color getPointColor(int x, int y){
		return board[x][y].getColor();
	}
	
	public Point getPoint(int x, int y) {
		return board[x][y];
	}
}
