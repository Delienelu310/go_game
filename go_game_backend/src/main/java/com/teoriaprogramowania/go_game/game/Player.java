package com.teoriaprogramowania.go_game.game;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.teoriaprogramowania.go_game.resources.Client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.util.*;

@Entity
public class Player {

	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonFilter("Player_client")
	@ManyToOne
	private Client client;
//	private int captives;
	private int finalScore;

	@JsonFilter("Player_captives")
	@ManyToMany
	private List<Point> captives = new ArrayList<>();
	
	@JsonFilter("Player_territory")
	@OneToOne(cascade = CascadeType.ALL)
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
