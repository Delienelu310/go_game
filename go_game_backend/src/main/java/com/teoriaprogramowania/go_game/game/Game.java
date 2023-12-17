package com.teoriaprogramowania.go_game.game;

import java.util.Arrays;
import java.util.List;

import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;
import com.teoriaprogramowania.go_game.game.Move;

import lombok.Data;

@Data
public class Game {

    /*TODO: 
        implement these methods in a way you like
        Remember to have high cohension
        introduce  new fields, new classes or event design patterns if you like
        reme
        Make tests and exceptions
    */

    private Long id;

    private Client white;
    private Client black;

    private int whitePlayerCounter;
    private int blackPlayerCounter;

    private List<Move> moves;
//    private Color[][] board;
    private Board board;
    private int size;

    private State state;
    
    private List<Stone> blackStones;
    private List<Stone> whiteStones;
    
    public Game(int size){
        this.size = size;
/*        this.board = new Color[size-1][size-1];
        for(int i = 0; i < size; ++i) {
        	Arrays.fill(this.board[i], null);
        }
 */     this.board = new Board(size);
        this.state = State.CREATED;
    }

    public void start(){
    	this.white = new Client();
    	this.black = new Client();
    	while(true) {
/*    		this.state = State.BLACKMOVES;
        	pobierz x,y albo pass albo surr
    		Move newMove = new Move(x, y, pass, Color.BLACK, surrender)
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
*/   	
    	}
    }
    
    public void addMove(Move move) throws InvalidMoveException{
        if(isMoveValid(move)) {
        	moves.add(move);
        	if(move.isPass() || move.isSurrender()) {
        		return;
        	}
            Stone newStone = new Stone(move, board);
        	board.addStone(newStone);
            addStone(newStone);
            
        } else {
        	throw new InvalidMoveException("Invalid move.");
        }
    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    
    private void addStone(Stone stone) {
    	if(stone.getColor() == Color.WHITE) {
    		whiteStones.add(stone);
    	} else if(stone.getColor() == Color.BLACK) {
    		blackStones.add(stone);
    	}
    }

    private boolean gameResolved() {
    	if (moves.get(moves.size() - 1).isSurrender()){
    		return true;
    	}
    	if(moves.get(moves.size() - 1).isPass() && moves.get(moves.size() - 2).isPass()) {
    		return true;
    	}
    	return false;
    }
    
    private boolean isMoveValid(Move move) {
    	if(move.isPass() || move.isSurrender()) {
    		return true;
    	}
    	int x = move.getX();
    	int y = move.getY();
    	Color color = move.getColor();

    	//false if field is taken
    	
    	if(this.board.getStoneColor(move.getX(), move.getY()) != null){
    		return false;
    	}
    	
    	//false if out of bounds
    	if(x >= this.size || x < 0 || y >= this.size || y < 0) {
			return false;
		}

    	//false if fields around taken with stones of opposite color

    	
    	if(stone.getBreaths() == 0) {
    		return false;
    	}
    	
    	return true;
    }
}
