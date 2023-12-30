package com.teoriaprogramowania.go_game.game;

import java.util.Objects;

import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

@Data
public class Move {
	private int x;
	private int y;
    private MoveType moveType;		//type of move. Either normal move, pass or surrender
    private Player player;
 
    public Move(int x, int y, MoveType moveType, Player player) {
    	this.x = x;
    	this.y = y;
    	this.moveType = moveType;
    	this.player = player;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public void setX(int x) {
    	this.x = x;
    }

    public void setY(int y) {
    	this.y = y;
    }
    
    public MoveType getMoveType() {
    	return this.moveType;
    }
    
    public Player getPlayer() {
    	return this.player;
    }

    @Override 
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Move move = (Move) obj;
		return this.x == move.x && this.y == move.y;
	}
}
