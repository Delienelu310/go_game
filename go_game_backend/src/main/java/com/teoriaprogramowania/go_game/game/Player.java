package com.teoriaprogramowania.go_game.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.resources.Client;

import lombok.Data;

import java.util.*;

@Data
public class Player {

	private Client client;
	private int captivesNumber = 0;
	private int finalScore;

	@JsonIgnore
	private List<Point> captives = new ArrayList<>();
	@JsonIgnore
	private Territory territory;
	
	public Player(Client client) {
		this.client = client;
		this.territory = new Territory();
		this.territory.setOwner(this);
	}
	
	public List<Point> getCaptives() {
		return this.captives;
	}
	
	public void addCaptives(Set<Point> newCaptvies) {
		this.captives.addAll(newCaptvies);
		this.captivesNumber = this.captives.size();
	}
	
	public void removeCaptives(Set<Point> captvies) {
		this.captives.removeAll(captvies);
		this.captivesNumber = this.captives.size();
	}
	
	public void addTerritory(Territory territory) {
		this.territory.addPoints(territory.getPoints());
	}
	
	public Territory getTerritory() {
		return this.territory;
	}
	
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	public int getFinalScore() {
		return this.finalScore;
	}
}
