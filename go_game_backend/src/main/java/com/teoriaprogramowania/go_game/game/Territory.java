package com.teoriaprogramowania.go_game.game;

import java.util.HashSet;
import java.util.Set;

public class Territory {
	private final Set<Point> points = new HashSet<>();
	private final Set<StoneGroup> neighbors = new HashSet<>();
	private Player owner;
	
	public Territory() {
		this.owner = null;
	}
	
	public Set<Point> getPoints(){
		return this.points;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public Player setOwner() {
		Set<Player> neighborStonesOwners = new HashSet<Player>();
		for(StoneGroup stoneGroup : neighbors) {
			neighborStonesOwners.add(stoneGroup.getOwner());
			if(neighborStonesOwners.size() > 1) {
				return null;
			}
		}
		if(neighborStonesOwners.size() == 1) {
			return (Player)neighborStonesOwners.toArray()[0];
		}
		return null;
	}
	
	public Set<Territory> getTerritories(Game game, Set<StoneGroup> deadStoneGroups){
		Board board = game.getBoard();
		int boardSize = board.getSize();
		Set<Point> potentialTerritory = new HashSet<Point>();
		Set<Territory> territories;
		Territory newTerritory;
		
		Point point;
		for(int i = 0; i < boardSize; ++i) {
			for(int j = 0; j < boardSize; ++j) {
				point = board.getPoint(i, j);
				if(point.isEmpty() || deadStoneGroups.contains(point.getStoneGroup())) {
					potentialTerritory.add(point);
				}
			}
		}
		
		while(potentialTerritory.size() > 0) {
			point = potentialTerritory.iterator().next();
			newTerritory = new Territory();
			
		}
 	}
}
