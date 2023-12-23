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
    
    public Game(int size){
        this.board = new Board(size);
        this.moves = new ArrayList<>();
    }
    
    public Game(Board board){
        this.board = board;
        this.moves = new ArrayList<>();
    }
    
    public Game(Game game) {
    	this.moves = game.getMoves();
    	this.board = game.getBoard();
    	this.previousBoardStates = game.getPreviousBoardStates();
    }
    
    public void setMoves(List<Move> moves) {
    	int boardSize = this.board.getSize();
    	this.board = new Board(boardSize);
    	this.moves = new ArrayList<>();
    	this.previousBoardStates = new HashSet<>();

    	Player currentPlayer = this.black;
    	for(Move move : moves) {
    		makeMove(move, currentPlayer);
 			if(currentPlayer == this.black) {
 				currentPlayer = this.white;
 			} else {
 				currentPlayer = this.black;
 			}
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
    
    public Set<String> getPreviousBoardStates(){
    	return this.previousBoardStates;
    }

    public void start(){
    	Client whiteClient = new Client();
    	Client blackClient = new Client();
    	this.white = new Player(whiteClient);
    	this.black = new Player(blackClient);
    	
    	Player currentPlayer = black;
    	
    	 do {
  			//loop untill player makes a valid move
  			do {
	    		//create appropriate move type
	 			Move move;
	 			if(/*pass*/){
	 				move = new Move(MoveType.PASS);
	 			} else if (/*surr*/){
	 				move = new Move(MoveType.SURRENDER);
	 			} else{
	 				int x = //pobierz x
	 				int y = //pobierz y
	 				Point point = new Point(x, y, this.board);
	 				move = new Move(point);
	 			}
 			} while(!isMoveValid(move));
 			
 			if(move.getMoveType() == MoveType.SURRENDER) {
 				moves.add(move);
 				break;
 			} else if(move.getMoveType() == MoveType.PASS){
 				moves.add(move);
 			} else {
 				simulateMove(this.board, move, black);
 				moves.add(move);
 			}
 			
 			//change player
 			if(currentPlayer == black) {
 				currentPlayer = white;
 			} else {
 				currentPlayer = black;
 			}
 			
    	} while(!gameResolved());
    	
    	Player winner = pickWinner(black, white, currentPlayer);
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
    
    public void makeMove(Move move, Player player) {
    	simulateMove(this.board, move, player);
    	moves.add(move);
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
