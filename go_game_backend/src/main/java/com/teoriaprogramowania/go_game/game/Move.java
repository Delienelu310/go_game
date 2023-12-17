package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

@Data
public class Move {
    
    private int x;
    private int y;
    private Boolean pass = false;
    private Color color;
    boolean surrender;
    public Move(){

    }

    public Move(int x, int y, Boolean pass, Color color, boolean surrender){
    	this.x = x;
    	this.y = y;
    	this.pass = pass;
    	this.color = color;
    	this.surrender = surrender;
    }

    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }


    public boolean isPass() {
    	return this.pass;
    }
    
    public Color getColor() {
    	return this.color;
    }
    
    public boolean isSurrender() {
    	return this.surrender;
    }
}
