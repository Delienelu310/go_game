package com.teoriaprogramowania.go_game.game;

import java.util.HashSet;
import java.util.Set;

public class Territory {
	private Set<Point> points = new HashSet<>();
	private Set<StoneGroup> neighbors = new HashSet<>();
	private Player owner;
	
	public Territory() {
		this.owner = null;
	}
	
	public Set<Point> getPoints(){
		return this.points;
	}
	
	public void addPoints(Set<Point> points) {
		this.points.addAll(points);
	}
	
	public Set<StoneGroup> getNeighborStoneGroups(){
		return this.neighbors;
	}
	
	public void addNeighborStoneGroups(Set<StoneGroup> neighborStoneGroups) {
		if(neighborStoneGroups == null) {
			return;
		}
		this.neighbors.addAll(neighborStoneGroups);
	}
	
	public void removeNeighborStoneGroups(Set<StoneGroup> neighborStoneGroups) {
		if(neighborStoneGroups == null) {
			return;
		}
		this.neighbors.removeAll(neighborStoneGroups);
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public void setOwner() {
		Set<Player> neighborStonesOwners = new HashSet<Player>();
		for(StoneGroup stoneGroup : neighbors) {
			neighborStonesOwners.add(stoneGroup.getOwner());
			if(neighborStonesOwners.size() > 1) {
				this.owner = null;
				return;
			}
		}
		if(neighborStonesOwners.size() == 1) {
			this.owner =  (Player)neighborStonesOwners.toArray()[0];
		}else {
			this.owner = null;
		}
	}
	
	public void setOwner(Player player) {
		this.owner = player; 
	}
}
