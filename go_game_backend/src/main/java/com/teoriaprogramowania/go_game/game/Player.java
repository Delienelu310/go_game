package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;

import java.util.*;

public class Player {
	private final Client client;
//	private int captives;
	private int finalScore;
	private List<Point> captives = new ArrayList<>();
	private Territory territory;
	
	public Player(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
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
