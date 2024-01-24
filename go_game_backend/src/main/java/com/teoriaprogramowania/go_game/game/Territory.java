package com.teoriaprogramowania.go_game.game;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Territory {

	@Id
	@GeneratedValue
	private Long territoryId;
	
	
	public Long getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}

	@JsonFilter("Territory_points")
	@OneToMany
	private Set<Point> points = new HashSet<>();

	@JsonFilter("Territory_neighbours")
	@ManyToMany
	private Set<StoneGroup> neighbors = new HashSet<>();

	@JsonFilter("Territory_owner")
	@ManyToOne
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
		this.neighbors.addAll(neighborStoneGroups);
	}
	
	public void removeNeighborStoneGroups(Set<StoneGroup> neighborStoneGroups) {
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
