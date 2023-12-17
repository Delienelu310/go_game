package com.teoriaprogramowania.go_game.game;

import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

@Data
public class Move {
	private Board board;			//state of board after move
    private Point point;			//added point
    private MoveType moveType;		//type of move. Either normal move, pass or surrender
//    private final int moveID;		//used to compare state of the board in different situations

    //pass or surrender constructor
    public Move(Board board, MoveType moveType) {
    	this.board = board;
    	this.moveType = moveType;
    	
    }
    
    //normal move constructor
    public Move(Board board, Point point, MoveType moveType){
    	this.point = point;
    	this.moveType = moveType;
    }

    public int getX() {
    	return this.point.getX();
    }
    
    public int getY() {
    	return this.point.getY();
    }
    
    public MoveType getMoveType() {
    	return this.moveType;
    }
}
