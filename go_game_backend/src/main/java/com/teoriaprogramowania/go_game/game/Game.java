package com.teoriaprogramowania.go_game.game;

import java.util.Arrays;
import java.util.List;

import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;
import com.teoriaprogramowania.go_game.game.Move;

import lombok.Data;

@Data
public class Game {
	//class Game is handling rules of the game
	
	private Long id;

    private Player white;
    private Player black;

    private List<Move> moves;
    private Board board;

    private State state;
        
    public Game(int size){
        this.board = new Board(size);
        this.state = State.CREATED;
    }

    public void start(){
    	Client whiteClient = new Client();
    	Client blackClient = new Client();
    	this.white = new Player(whiteClient);
    	this.black = new Player(blackClient);
    	
    	while(true) {
/*    		this.state = State.BLACKMOVES;
        	pobierz x,y albo pass albo surr
    		Move newMove = new Move(x, y, moveType, Color.BLACK, surrender)
    		try{
    			addMove(move);
    		} catch(InvalidMoveException){
    			handle exception
    		}
    		
    		if(gameResolved){
    			break;
    		}
    		
    		this.state = State.WHITEMOVES;
    		pobierz x,y albo pass
    		Move newMove = new Move(x, y, pass, Color.WHITE, surrender)
    		try{
    			addMove(move);
    		} catch(InvalidMoveException){
    			handle exception
    		}
    		
    		if(gameResolved){
    			break;
    		}	
    	}
    	this.state = FINISHED
*/    	
    }
    
    public void addMove(Move move) throws InvalidMoveException{
        if(isMoveValid(move)) {
        	moves.add(move);
        } else {
        	throw new InvalidMoveException("Invalid move.");
        }
    }
    
    public void makeMove(Move move, Board board) {

    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    

    private boolean gameResolved() {
    	return true;
    }
    
    private boolean isMoveValid(Move move) {
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	//...
    	
    	return true;
    }
}
