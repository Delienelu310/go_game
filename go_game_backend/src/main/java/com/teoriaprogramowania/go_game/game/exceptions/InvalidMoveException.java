package com.teoriaprogramowania.go_game.game.exceptions;

public class InvalidMoveException extends RuntimeException{	
	public InvalidMoveException(String message) {
    	super(message);
    }
    
	public InvalidMoveException() {
		super("Invalid Move");
	}
}
