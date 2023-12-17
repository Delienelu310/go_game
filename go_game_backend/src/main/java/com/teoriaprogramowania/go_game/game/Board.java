package com.teoriaprogramowania.go_game.game;

import java.util.Arrays;

public class Board {
	private Color[][] board;
	private int size;
	
	public Board(int size){
		this.size = size;
        this.board = new Color[size-1][size-1];
        for(int i = 0; i < size; ++i) {
        	Arrays.fill(this.board[i], null);
        }
	}

	public Board(Color[][] board, int size) {
		this.board = board;
		this.size = size;
	}
	
	public Color[][] getBoard(){
		return board;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void addStone(Stone stone) {
		this.board[stone.getX()][stone.getY()] = stone.getColor();
	}
	
	public Color getStoneColor(int x, int y){
		return board[x][y];
	}
}
