package com.teoriaprogramowania.go_game.game;
import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

public class Stone {
	int x;
	int y;
	Color color;
	Board board;
	private int breaths;
	private boolean alive;
	
	public Stone(Move move, Board board){
		this.x = move.getX();
		this.y = move.getY();
		this.color = move.getColor();
		this.board = board;

		this.breaths = 4;
		if(this.x == 0 || this.x == board.getSize() - 1) {
			--breaths;
		}
		if(this.y == 0 || this.y == board.getSize() - 1) {
			--breaths;
		}
		updateBreaths(board);
	}
	
	public void updateBreaths(Board board) {
		//set breaths based on stones around it
		if(this.x != board.getSize() - 1) {
			if(board.getStoneColor(this.x+1, this.y) == this.color) {
				breaths--;
			}
		}
		if(this.y != board.getSize() - 1) {
			if(board.getStoneColor(this.x, this.y+1) == this.color) {
				breaths--;
			}
		}
		if(this.x != 0) {
			if(board.getStoneColor(this.x-1, this.y) == this.color) {
				breaths--;
			}
		}
		if(this.y != 0) {
			if(board.getStoneColor(this.x, this.y-1) == this.color) {
				breaths--;
			}
		}
	}
	
	public int getBreaths() {
		return this.breaths;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
