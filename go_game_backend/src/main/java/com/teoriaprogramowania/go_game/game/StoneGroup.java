package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class StoneGroup {
	//represents a group of stones 
	private Set<Point> stones;
	private Set<Point> breaths;
	Player owner;
	
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
		this.stones.addAll(newStones.getStones());
		this.breaths.addAll(newStones.getBreaths());

		//remove connecting point from breaths;
		this.breaths.remove(connectingPoint);
	}
	
	//when enemy player puts his stone in the neighborhood of our's stone group
	public StoneGroup removeBreath(Point enemyPoint) {
		StoneGroup newStoneGroup = new StoneGroup(this.stones, this.breaths, this.owner);
		newStoneGroup.breaths.remove(enemyPoint);
		return newStoneGroup;
	}
	
	//remove stone group and update breaths of neighbor points
	//return number of stones from this group
	public int removeStoneGroup() {
		int capturedStones = this.stones.size();
		for(Point stone : this.stones) {
			stone.setStoneGroup(null);
			Set<StoneGroup> neighbors = stone.getNeighborStoneGroups();
			for(StoneGroup neighbor : neighbors) {
				neighbor.breaths.add(stone);
			}
		}
		return capturedStones;
	}
	
}
