package com.teoriaprogramowania.go_game.game;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import lombok.Data;

@Data
public class Game {
	//class Game is handling rules of the game
	
	private Long id;

    private Board board;
    private State state;
    private List<Move> moves = new ArrayList<>();
    private Set<String> previousBoardStates = new HashSet<>();
    private Set<StoneGroup> deadStoneGroups = new HashSet<>();
	@JsonIgnore
    private Set<Territory> territories = new HashSet<>();
    private Set<Player> agreed = new HashSet<>();
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    
    public Game() {
	}

	public Game(int size){
        this.board = new Board(size);
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
    
    
    private Set<String> getPreviousBoardStates(){
    	return this.previousBoardStates;
    }

    public void start() {
    	this.state = State.ONGOING;
    }
    
    public void addMove(Move move){
        moves.add(move);
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
    
    public void makeMove(Move move) {
    	simulateMove(this.board, move);
    	this.moves.add(move);
    	
    	String currentBoardState = this.board.toString();
        previousBoardStates.add(currentBoardState);

    	currentPlayer = move.getPlayer();
    }
    
    public boolean simulateMove(Board board, Move move) {
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER) {
    		return true;
    	}
    	
    	Point simulatedPoint = board.getPoint(move.getX(), move.getY());
    	
    	StoneGroup newStoneGroup = new StoneGroup(simulatedPoint, move.getPlayer());
    	
    	Set<Point> capturedStones = new HashSet<>();
    	Set<StoneGroup> capturedStoneGroups = new HashSet<>();

    	Set<StoneGroup> neighbors = simulatedPoint.getNeighborStoneGroups();
    	//remove breath from enemy stone group and kill it if appropriate
      	for(StoneGroup neighbor : neighbors) {
    		if(!neighbor.getOwner().equals(move.getPlayer())){
    			neighbor = neighbor.removeBreath(simulatedPoint);
    			if(neighbor.getBreaths().size() == 0) {
    				capturedStoneGroups.add(neighbor);
    				capturedStones.addAll(neighbor.removeStoneGroup(this.board));
    			}
    		}
    	}
      	
    	//merge friendly neighbor stone groups        
      	for(StoneGroup neighbor : neighbors) {
    		if(neighbor.getOwner().equals(move.getPlayer())) {
    	        newStoneGroup.joinStoneGroup(neighbor, simulatedPoint);
            }
      	}
    	
    	//suicide move check
    	if(newStoneGroup.getBreaths().size() == 0) {
    		this.board.setPointStoneGroup(simulatedPoint, null);
    		return false;
    	}

    	//ko rule check;
    	String currentBoardState = this.board.toString();
        if(previousBoardStates.contains(currentBoardState)) {
        	for(StoneGroup sg : capturedStoneGroups) {
        		for(Point stone : sg.getStones()) {
        			this.board.setPointStoneGroup(stone, sg);
        		}
        	}
        	return false;
        }
        
        //if ok then apply changes
      	for(Point stone : newStoneGroup.getStones()) {
      		stone.setStoneGroup(newStoneGroup);
      	}
        move.getPlayer().addCaptives(capturedStones);
        
        return true;
    }
    
    public boolean isMoveValid(Move move){
    	if(move.getPlayer() == currentPlayer) {
    		return false;
    	}
    	
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

    	if(simulateMove(this.board, move) == false) {
    		this.board.setPointStoneGroup(new Point(move.getX(), move.getY(), this.board), null);
    		return false;
    	}

    	this.board.setPointStoneGroup(new Point(move.getX(), move.getY(), this.board), null);
    	return true;
    }

    public void pickDeadStoneGroup(int x, int y) {
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
    
    public void removeDeadStoneGroup(int x, int y) {
    	try {
        	Point point = this.board.getPoint(x, y);
    		if(point.isEmpty()) {
    			return;
    		}
    		StoneGroup toBeRemoved = point.getStoneGroup();
        	if(this.deadStoneGroups.contains(toBeRemoved)) {
        		deadStoneGroups.remove(toBeRemoved);
        	}
    	} catch(OutOfBoardException e) {
    		return;
    	}
    }
    
    private Set<Territory> establishTerritories(Set<StoneGroup> deadStoneGroups){
		Set<Point> potentialTerritory = new HashSet<Point>();
		Set<Territory> territories = new HashSet<Territory>();
		Territory newTerritory;
		
		Point point;
		for(int i = 0; i < this.board.getSize(); ++i) {
			for(int j = 0; j < this.board.getSize(); ++j) {
				point = this.board.getPoint(i, j);
				if(point.isEmpty() || deadStoneGroups.contains(point.getStoneGroup())) {
					potentialTerritory.add(point);
				}
			}
		}
		
		while(potentialTerritory.size() > 0) {
			point = potentialTerritory.iterator().next();
			newTerritory = new Territory();

			Set<Point> expandedTerritory = new HashSet<>();
			Set<Point> newTerritoryPoints = new HashSet<>();
			expandedTerritory.add(point);
			
			int prev_size;
			int curr_size;
			do {
				prev_size = expandedTerritory.size();
				newTerritoryPoints.clear();
				for(Point deadOrEmptyPoint : new HashSet<>(expandedTerritory)) {
					newTerritoryPoints.addAll(deadOrEmptyPoint.getEmptyNeighborPoints(deadStoneGroups));
				}
				expandedTerritory.addAll(newTerritoryPoints);
				curr_size = expandedTerritory.size();
			} while(curr_size > prev_size);
			
			newTerritory.addPoints(expandedTerritory);
			for(Point emptyOrDeadPoint : expandedTerritory) {
				newTerritory.addNeighborStoneGroups(emptyOrDeadPoint.getNeighborStoneGroups());
			}
			
			newTerritory.removeNeighborStoneGroups(deadStoneGroups);
			potentialTerritory.removeAll(expandedTerritory);
			territories.add(newTerritory);
		}
		
		for(Territory territory : territories) {
			territory.setOwner();
		}
		
		return territories;
 	}
    
    public void resumeGame() {
    	this.deadStoneGroups = null;
    	this.agreed.clear();
    	if(this.state == State.NEGOTIATION) {
        	this.state = State.ONGOING;
    	}
    }
    
    public void finalizeGame() {
    	if(this.agreed.size() != 2) {
    		return;
    	}
    	if(this.state != State.NEGOTIATION) {
    		return;
    	}
    	territories = establishTerritories(deadStoneGroups);
    	this.state = State.FINISHED;
    }
    
    public void agreeToFinalize(Player p) {
    	this.agreed.add(p);
    }
    
    public boolean bothPlayersAgreed() {
    	return this.agreed.size() == 2;
    }
    
    public void setScore(Player p1, Player p2) {
    	for(Territory territory : territories) {
    		if(territory.getOwner() == null) {
    			continue;
    		}
    		if(territory.getOwner().equals(p1)) {
    			for(StoneGroup deadStoneGroup : deadStoneGroups) {
    				if(territory.getPoints().containsAll(deadStoneGroup.getStones())) {
    					p1.addCaptives(deadStoneGroup.getStones());
    				}
    			}
    			p1.addTerritory(territory);
    		}
    		else if (territory.getOwner().equals(p2)) {
    			for(StoneGroup deadStoneGroup : deadStoneGroups) {
    				if(territory.getPoints().containsAll(deadStoneGroup.getStones())) {
    					p2.addCaptives(deadStoneGroup.getStones());
    				}
    			}
    			p2.addTerritory(territory);
    		}
    	}
    	
    	int p1Score = p1.getTerritory().getPoints().size() - p2.getCaptives().size();
    	if(p1Score < 0) {
    		p1Score = 0;
    	}
    	
    	int p2Score = p2.getTerritory().getPoints().size() - p1.getCaptives().size();
    	if(p2Score < 0) {
    		p2Score = 0;
    	}
    	
    	p1.setFinalScore(p1Score);
    	p2.setFinalScore(p2Score);
    }
    
    public int getPlayerScore(Player player) {
    	return player.getFinalScore();
    }
}
