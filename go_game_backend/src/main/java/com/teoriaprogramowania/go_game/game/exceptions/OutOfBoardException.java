package com.teoriaprogramowania.go_game.game.exceptions;

public class OutOfBoardException extends RuntimeException{
	public OutOfBoardException(String message) {
    	super(message);
    }
	
	public OutOfBoardException(){
		super("Point out of board");
	}
}
