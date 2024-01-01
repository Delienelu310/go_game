package com.teoriaprogramowania.go_game.game;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import java.util.Arrays;

public class Board {
	private Point[][] board;
	private final int size;
	
	//new board initialization
	public Board(int size){
		this.size = size;
        this.board = new Point[size][size];

        for(int i = 0; i < size; ++i) {
        	for(int j = 0; j < size; ++j) {
        		Point newPoint = new Point(i, j, this);
        		board[i][j] = newPoint;
        	}
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
	
	public Point getPoint(int x, int y) throws OutOfBoardException {
		if(x < 0 || x >= this.size || y < 0 || y >= this.size) {
			throw new OutOfBoardException();
		}
		return board[x][y];
	}
	
	public void addPoint(Point point) {
		this.board[point.getX()][point.getY()] = point;
	}
	
	public void setPointStoneGroup(Point point, StoneGroup stoneGroup){
		this.board[point.getX()][point.getY()].setStoneGroup(stoneGroup);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StoneGroup stoneGroup = board[i][j].getStoneGroup();
                if (stoneGroup == null) {
                    sb.append('.');
                } else {
                    sb.append(stoneGroup.getOwner());
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
