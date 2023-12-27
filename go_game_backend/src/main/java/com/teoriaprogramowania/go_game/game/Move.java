package com.teoriaprogramowania.go_game.game;

import java.util.Objects;

import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

import lombok.Data;

@Data
public class Move {
    private Point point;			//added point
    private MoveType moveType;		//type of move. Either normal move, pass or surrender
//    private final int moveID;		//used to compare state of the board in different situations
    
    //pass or surrender constructor
    public Move(MoveType moveType) {
    	this.moveType = moveType;
    	
    }
    
    //normal move constructor
    public Move(Point point){
    	this.point = point;
    	this.moveType = MoveType.NORMAL;
    }
    
    public int getX() {
    	return this.point.getX();
    }
    
    public int getY() {
    	return this.point.getY();
    }
    
    public void setX(int x) {
    	this.point.setX(x);
    }

    public void setY(int y) {
    	this.point.setY(y);
    }
    
    public MoveType getMoveType() {
    	return this.moveType;
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
		return this.point == move.point;
	}
}
