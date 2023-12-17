package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.resources.Client;

public class Player {
	private final Client client;
	private int captives;
	
	public Player(Client client) {
		this.client = client;
		this.captives = 0;
	}
}
