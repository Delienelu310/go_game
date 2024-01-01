package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;

public class Player {
	private final Client client;
	private int captives;
	private int finalScore;
	
	public Player(Client client) {
		this.client = client;
		this.captives = 0;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public int getCaptives() {
		return this.captives;
	}
	
	public void addCaptives(int amount) {
		this.captives += amount;
	}
	
	public void removeCaptives(int amount) {
		this.captives -= amount;
	}
	
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	public int getFinalScore(int finalScore) {
		return this.finalScore;
	}
}
