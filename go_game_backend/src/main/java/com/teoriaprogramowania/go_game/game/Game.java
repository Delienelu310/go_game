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

        
    public Game(int size){
        this.board = new Board(size);
    }
    
    public Game(Board board){
        this.board = board;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Long getId() {
    	return this.id;
    }

    public void start(){
    	Client whiteClient = new Client();
    	Client blackClient = new Client();
    	this.white = new Player(whiteClient);
    	this.black = new Player(blackClient);
    	
    	while(true) {
/*      	pobierz x,y albo pass albo surr
    		
    		//MOZE BUILDER DO MOVE
    		    		
    		try{
    			addMove(move);
    		} catch(InvalidMoveException){
    			handle exception
    		}
    		
    		if(gameResolved){
    			break;
    		}
    		
    		pobierz x,y albo pass
    		Move newMove = new Move()
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
    
    public void addMove(Move move, Player player) throws InvalidMoveException{
        if(isMoveValid(move, player)) {
        	moves.add(move);
        } else {
        	throw new InvalidMoveException("Invalid move.");
        }
    }
    
    public void makeMove(Move move, Board board, Player player) {
    	if(!isMoveValid(move, player)) {
    		throw new InvalidMoveException();
    	}
    	
    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    

    private boolean gameResolved() {
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.PASS) {
        	if(moves.get(moves.size()-2).getMoveType() == MoveType.PASS) {
        		return true;
        	}
    	}
    	return false;
    }
    public boolean simulateMove(Board tempBoard, Move move, Player player) {
    	Point simulatedPoint = tempBoard.getPoint(move.getX(), move.getY());
    	simulatedPoint.setStoneGroup(new StoneGroup(simulatedPoint, player));
    	
    	StoneGroup newStoneGroup = new StoneGroup(simulatedPoint, player);
    	for(StoneGroup neighbor : simulatedPoint.getNeighborStoneGroups()) {
    		if(neighbor.getOwner().equals(player)) {
    	    	//merge friendly neighbor stone groups
                newStoneGroup.joinStoneGroup(neighbor, simulatedPoint);
            }
    		else {
    			//remove breath from enemy stone group
    			neighbor.removeBreath(simulatedPoint);
    			if(neighbor.getBreaths().size() == 0) {
    				player.addCaptives(neighbor.removeStoneGroup());
    			}
    		}
    	}
    	
    	if(newStoneGroup.getBreaths().size() == 0) {
    		return false;
    	}
    	return true;
    }
    
    private boolean isMoveValid(Move move, Player player) {
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	if(!board.getPoint(move.getX(), move.getY()).isEmpty()){
    		return false;
    	}
    	
    	//ko
    	if(moves.size() > 1 && moves.get(moves.size() - 2).equals(move)) {
    		return false;
    	}
    	
    	Board tempBoard = new Board(board.getBoard(), board.getSize());
    	if(simulateMove(tempBoard, move, player) == false) {
    		return false;
    	}
    	
    	return true;
    }
}
