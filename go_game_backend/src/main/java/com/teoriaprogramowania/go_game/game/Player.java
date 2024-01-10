package com.teoriaprogramowania.go_game.game;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.teoriaprogramowania.go_game.resources.Client;


import java.util.*;

public class Player {
	@JsonFilter("Player_client")
	private Client client;
//	private int captives;
	private int finalScore;

	@JsonFilter("Player_captives")
	private List<Point> captives = new ArrayList<>();
	@JsonFilter("Player_territory")
	private Territory territory;

	public Player(){
		
	}
	
	public Player(Client client) {
		this.client = client;
		this.territory = new Territory();
		this.territory.setOwner(this);
	}

	public Client getClient(){
		return this.client;
	}
	
	public List<Point> getCaptives() {
		return this.captives;
	}
	
	public void addCaptives(Set<Point> newCaptvies) {
		this.captives.addAll(newCaptvies);
	}
	
	public void removeCaptives(Set<Point> captvies) {
		this.captives.removeAll(captvies);
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
