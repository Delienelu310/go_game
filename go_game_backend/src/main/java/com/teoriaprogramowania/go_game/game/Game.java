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
    private State state;
    private Set<StoneGroup> deadStoneGroups = new HashSet<>();
    
    public Game(int size){
        this.board = new Board(size);
        this.moves = new ArrayList<>();
        this.state = State.CREATED;
    }
    
    public Game(Board board){
        this.board = board;
        this.moves = new ArrayList<>();
        this.state = State.CREATED;
    }
    
    public Game(Game game) {
    	this.moves = game.getMoves();
    	this.board = game.getBoard();
    	this.previousBoardStates = game.getPreviousBoardStates();
    	this.state = State.CREATED;
    }
    
    public void setMoves(List<Move> moves) {
    	int boardSize = this.board.getSize();
    	this.board = new Board(boardSize);
    	this.moves = new ArrayList<>();
    	this.previousBoardStates = new HashSet<>();

    	for(Move move : moves) {
    		makeMove(move);
    	}
    }
    
    private Player getLastMovePlayer() {
    	if(moves.size()%2 == 0) {
    		return this.white;
    	}
    	return this.black;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Long getId() {
    	return this.id;
    }
    
    public Board getBoard() {
    	return this.board;
    }
    
    public State getState() {
    	return this.state;
    }
    
    private Set<String> getPreviousBoardStates(){
    	return this.previousBoardStates;
    }

    public void start() {
    	this.state = State.ONGOING;
    }
    
    private void addMove(Move move){
        moves.add(move);
    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    
    public boolean hasChangedState() {
    	if(moves.size() < 1) {
    		return false;
    	}
    	
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.SURRENDER) {
    		this.state = State.FINISHED;
    		return true;
    	}
    	if(moves.get(moves.size()-1).getMoveType() == MoveType.PASS) {
    		if(moves.size() > 1);{
    			if(moves.get(moves.size()-2).getMoveType() == MoveType.PASS) {
    	    		this.state = State.NEGOTIATION;
    				return true;
    			}
        	}
    	}
    	return false;
    }
    
    public void resumeGame() {
    	if(this.state == State.NEGOTIATION) {
        	this.state = State.ONGOING;
    	}
    }
    
    public void finalizeGame() {
    	if(this.state != State.NEGOTIATION) {
    		return;
    	}
    	this.state = State.FINISHED;
    	
    	//obliczanie terytorium i wybieranie zwyciezcy
    }
    
    public void pickDeadStones(int x, int y) {
    	try {
        	Point point = this.board.getPoint(x, y);
    		if(point.isEmpty()) {
    			return;
    		}
        	deadStoneGroups.add(point.getStoneGroup());
    	} catch(OutOfBoardException e) {
    		return;
    	}
    }
        
    public void makeMove(Move move) {
    	if(simulateMove(this.board, move)) {
    		moves.add(move);
    	}
    }
    
    public boolean simulateMove(Board board, Move move) {
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	
    	Point simulatedPoint = board.getPoint(move.getX(), move.getY());
    	
    	int captives = 0;
    	
    	StoneGroup newStoneGroup = new StoneGroup(simulatedPoint, move.getPlayer());
    	Set<StoneGroup> neighbors = simulatedPoint.getNeighborStoneGroups();
    	
    	Set<StoneGroup> capturedStoneGroups = new HashSet<>();

      	//remove breath from enemy stone group and kill it if appropriate
      	for(StoneGroup neighbor : neighbors) {
    		if(!neighbor.getOwner().equals(move.getPlayer())){
    			neighbor = neighbor.removeBreath(simulatedPoint);
    			if(neighbor.getBreaths().size() == 0) {
    				capturedStoneGroups.add(neighbor);
    				captives += neighbor.removeStoneGroup(this.board);
    			}
    		}
    	}
      	
    	//merge friendly neighbor stone groups        
      	for(StoneGroup neighbor : neighbors) {
    		if(neighbor.getOwner().equals(move.getPlayer())) {
    	        newStoneGroup.joinStoneGroup(neighbor, simulatedPoint);
            }
      	}
      	for(Point stone : newStoneGroup.getStones()) {
      		stone.setStoneGroup(newStoneGroup);
      	}
      	
      	
    	
    	//suicide move check
    	if(newStoneGroup.getBreaths().size() == 0) {
    		return false;
    	}

    	//ko rule check;
    	String currentBoardState = this.board.toString();
        if(previousBoardStates.contains(currentBoardState)) {
        	return false;
        }
        
        //if ok then apply changes
        move.getPlayer().addCaptives(captives);
        
        previousBoardStates.add(currentBoardState);
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

    	BoardMemento memento = this.board.createMemento();
    	if(simulateMove(this.board, move) == false) {
    		this.board.restore(memento);
    		return false;
    	}
    	this.board.restore(memento);
    	return true;
    }
    
    public Player pickWinner(Player p1, Player p2) {
    	if(moves.get(moves.size() - 1).getMoveType() == MoveType.SURRENDER) {
    		if(this.getLastMovePlayer() == p1) {
    			return p2;
    		}
    		return p2;
    	}
//    	int p1Territory = p1.calculateTeritory();
//    	int p2Territory = p2.calculateTeritory();
    	
    	if(p1.getCaptives() > p2.getCaptives()) {
        	return p1;	
    	}
    	return p2;
    }
    
    public int getPlayerCaptives(Player player) {
    	return player.getCaptives();
    }
}
