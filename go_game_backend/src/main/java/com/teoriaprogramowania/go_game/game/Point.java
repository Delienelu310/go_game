package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class Point {
	//class represents a singular point on the board
	private int x;					//coordinate x
	private int y;					//coordinate y
	private Board board;			//board to which the point belongs
	private StoneGroup stoneGroup;	//group of stones to which the point belongs
	
	public Point(int x, int y, Board board) {
		this.board = board;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public StoneGroup getStoneGroup() {
		return this.stoneGroup;
	}
	
	public void setStoneGroup(StoneGroup stoneGroup) {
		this.stoneGroup = stoneGroup;
	}
	
	public Color getColor() {
		return this.stoneGroup.getColor();
	}
	
	public Set<StoneGroup> getNeighborStoneGroups(){
		Set<StoneGroup> neighborStoneGroups = new HashSet<StoneGroup>();
		int boardSize = this.board.getSize();
		int dx[] = {-1, 1, 0, 0};
		int dy[] = {0, 0, 1, -1};
		for(int i = 0; i < 4; ++i) {
			int newX = this.x + dx[i];
			int newY = this.y + dy[i];
			//continue if point out of board
			if(newX >= boardSize || newX < 0 || newY >= boardSize || newY < 0) {
				continue;
			}
			
			//continue if point empty
			Point newPoint = board.getPoint(newX, newY);
			if(newPoint.getColor() == null) {
				continue;
			}
			
			neighborStoneGroups.add(newPoint.getStoneGroup());
		}
		return neighborStoneGroups;
	}
	
	//get empty neighbors
	public Set<Point> getEmptyNeighborPoints(){
		Set<Point> emptyNeighborStoneGroups = new HashSet<Point>();
		int boardSize = this.board.getSize();
		int dx[] = {-1, 1, 0, 0};
		int dy[] = {0, 0, 1, -1};
		for(int i = 0; i < 4; ++i) {
			int newX = this.x + dx[i];
			int newY = this.y + dy[i];
			//continue if point out of board
			if(newX >= boardSize || newX < 0 || newY >= boardSize || newY < 0) {
				continue;
			}
			
			//continue if point empty
			Point newPoint = board.getPoint(newX, newY);
			if(newPoint.getColor() == null) {
				emptyNeighborStoneGroups.add(newPoint);
			}
		}
		return emptyNeighborStoneGroups;
	}

	//get dead neighbors
	
}
