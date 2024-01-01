package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class StoneGroup {
	//represents a group of stones 
	private Set<Point> stones;
	private Set<Point> breaths;
	private Player owner;
	
	//group of stones constructor
	public StoneGroup(Set<Point> stones, Set<Point> breaths, Player owner) {
		this.stones = stones;
		this.breaths = breaths;
		this.owner = owner;
	}
	
	//singular stone constructor
	public StoneGroup(Point point, Player owner) {
		this.stones = new HashSet<Point>();
		stones.add(point);
		this.owner = owner;
		this.breaths = new HashSet<Point>(point.getEmptyNeighborPoints());
		point.setStoneGroup(this);
	}
	
	//copy constructor
	public StoneGroup(StoneGroup stoneGroup) {
		this.stones = stoneGroup.getStones();
		this.breaths = stoneGroup.getBreaths();
		this.owner = stoneGroup.getOwner();
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public Set<Point> getBreaths(){
		return this.breaths;
	}
	
	public Set<Point> getStones(){
		return this.stones;
	}
	
	//connect stone chains
	public void joinStoneGroup(StoneGroup newStones, Point connectingPoint) {
		this.stones.addAll(newStones.stones);
		this.breaths.addAll(newStones.breaths);
		this.breaths.remove(connectingPoint);
	}
	
	public StoneGroup removeBreath(Point enemyPoint) {
		StoneGroup newStoneGroup = new StoneGroup(this.stones, this.breaths, this.owner);
		newStoneGroup.breaths.remove(enemyPoint);
		return newStoneGroup;
	}
	
	public void addBreath(Point breath) {
		this.breaths.add(breath);
	}
	
	//remove stone group and update breaths of neighbor stone groups
	//return number of stones from this group
	public int removeStoneGroup(Board board) {
		int capturedStones = this.stones.size();
		for(Point stone : this.stones) {
			Point actualPoint = board.getPoint(stone.getX(), stone.getY());
			actualPoint.removeStone();
		}
		return capturedStones;
	}
}
