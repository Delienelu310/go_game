package com.teoriaprogramowania.go_game.game;

import java.util.*;

import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;
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
    private Set<String> previousBoardStates = new HashSet<>();
    private int passCount;
    
    public Game(int size){
        this.board = new Board(size);
        this.moves = new ArrayList<>();
    }
    
    public Game(Board board){
        this.board = board;
        this.moves = new ArrayList<>();
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
    	
    	Player currentPlayer = black;
    	
    	 do {
 			Move move;
 			if(/*pass*/){
 				move = new Move(MoveType.PASS);
 			} else if (/*surr*/){
 				move = new Move(MoveType.SURRENDER);
 			} else{
 				int x = //pobierz x
 				int y = //pobierz y
 				Point blackPoint = new Point(x, y, this.board);
 				move = new Move(blackPoint);
 			}
 			
 			if(isMoveValid(move)){
 				if(move.getMoveType() == MoveType.SURRENDER) {
 					moves.add(move);
 					break;
 				} else if(move.getMoveType() == MoveType.PASS){
 					passCount++;
 					moves.add(move);
 					continue;
 				}
 				simulateMove(this.board, move, black);
 				moves.add(move);
 			}
    	} while(!gameResolved());
    	
    	Player winner = pickWinner(black, white);
    }
    
    public void addMove(Move move){
        moves.add(move);
    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    
    public boolean gameResolved() {
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.PASS) {
    		if(moves.size() > 1);{
    			if(moves.get(moves.size()-2).getMoveType() == MoveType.PASS) {
    				return true;
    			}
        	}
    	}
    	return false;
    }
    
    public boolean simulateMove(Board board, Move move, Player player) {
    	BoardMemento memento = board.createMemento();
    	
    	Point simulatedPoint = board.getPoint(move.getX(), move.getY());
    	StoneGroup newStoneGroup = new StoneGroup(simulatedPoint, player);
    	
    	int captives = 0;
    	
      	for(StoneGroup neighbor : simulatedPoint.getNeighborStoneGroups()) {
    		if(neighbor.getOwner().equals(player)) {
    	    	//merge friendly neighbor stone groups
                newStoneGroup.joinStoneGroup(neighbor, simulatedPoint);
            }
    		else {
    			//remove breath from enemy stone group
    			neighbor.removeBreath(simulatedPoint);
    			if(neighbor.getBreaths().size() == 0) {
    				captives = neighbor.removeStoneGroup();
    			}
    		}
    	}
    	
    	//suicide move check
    	if(newStoneGroup.getBreaths().size() == 0) {
        	board.restore(memento);
    		return false;
    	}

    	//ko rule check;
    	String currentBoardState = board.toString();
        if(previousBoardStates.contains(currentBoardState)) {
        	board.restore(memento);
            return false;
        }
        previousBoardStates.add(currentBoardState);
        player.addCaptives(captives);
    	return true;
    }
    
    public boolean isMoveValid(Move move){
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	try {
    		if(!board.getPoint(move.getX(), move.getY()).isEmpty()){
    			return false;
    		}
    	} catch(OutOfBoardException e) {
    		return false;
    	}
    	
    	Player fakePlayer = new Player(new Client());
    	if(simulateMove(this.board, move, fakePlayer) == false) {
    		return false;
    	}
    	
    	return true;
    }
    
    public Player pickWinner(Player p1, Player p2) {
    	return p1;
    }
    
    public int getPlayerCaptives(Player player) {
    	return player.getCaptives();
    }
}
