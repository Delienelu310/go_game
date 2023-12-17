package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

@Data
public class Move {
	private Board board;			//state of board after move
    private int x;					//coordinate x
    private int y;					//coordinate y
    private Color color;			//color of player moving
    private MoveType moveType;		//type of move. Either normal move, pass or surrender
//    private final int moveID;		//used to compare state of the board in different situations

    //pass or surrender constructor
    public Move(Board board, MoveType moveType) {
    	this.board = board;
    	this.moveType = moveType;
    	
    }
    
    //normal move constructor
    public Move(Board board, int x, int y, Color color, MoveType moveType){
    	this.x = x;
    	this.y = y;
    	this.color = color;
    	this.moveType = moveType;
    }

    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public MoveType getMoveType() {
    	return this.moveType;
    }
    
    public Color getColor() {
    	return this.color;
    }
    
}
