package com.teoriaprogramowania.go_game.game;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.GoGameApplication;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@JsonFilter("Game")
@Entity
public class Game {

	@Transient
	@JsonIgnore
	Logger logger = LoggerFactory.getLogger(GoGameApplication.class);

	@Id
	@GeneratedValue
	private Long id;

	@JsonFilter("Game_board")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @Transient
	private Board board;	
	
    private State state;	

	@JsonFilter("Game_moves")	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Move> moves = new ArrayList<>();


	@JsonFilter("Game_previousBoardStates")
	@ElementCollection
	@Column(columnDefinition = "TEXT")
    private List<String> previousBoardStates = new LinkedList<>(); 


	@JsonFilter("Game_deadStoneGroups")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StoneGroup> deadStoneGroups = new HashSet<>(); 


	@JsonFilter("Game_territories")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Territory> territories = new HashSet<>();


	@JsonFilter("Game_players")
	@OneToMany(fetch = FetchType.EAGER)
    private Set<Player> agreed = new HashSet<>();


	private Integer playersCount = 2;


	@JsonFilter("Game_players")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>(2);

	@JsonFilter("Game_players")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Player currentPlayer;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @Transient
    private Set<StoneGroup> lastCaptured = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @Transient
    private List<StoneGroupSet> capturedStonesStack = new LinkedList<>();
    
	
	public Integer getPlayersCount(){
		return playersCount;
	}
	public void setPlayersCount(int playersCount){
		this.playersCount = playersCount;
	}
    
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
    	this.moves = new ArrayList<>(game.getMoves());
    	int boardSize = game.getBoard().getSize();
    	this.board = new Board(boardSize);
    	for(Move move : this.moves) {
    		simulateMove(this.board, move);
    	}
    	this.lastCaptured = game.getLastCapturedStoneGroup();
    	this.previousBoardStates = new LinkedList<>(game.getPreviousBoardStates());
    	this.state = game.getState();
    	this.players = new ArrayList<>(game.getPlayers());
    	this.currentPlayer = game.getCurrentPlayer();
    }
    
    public void setMoves(List<Move> moves) {
    	int boardSize = this.board.getSize();
    	this.board = new Board(boardSize);
    	this.moves = new ArrayList<>();
    	this.previousBoardStates = new LinkedList<>();

    	for(Move move : moves) {
    		makeMove(move);
    	}
    }
    
    public void undo() {
    	if(this.moves.isEmpty()) {
    		return;
    	}

    	Move lastMove = this.moves.get(this.moves.size() - 1);
    	
    	if(lastMove.getMoveType() == MoveType.NORMAL) {
    		Point lastPoint = this.board.getPoint(lastMove.getX(), lastMove.getY());
    		
    		StoneGroup lastStoneGroup = lastPoint.getStoneGroup();

        	this.previousBoardStates.remove(this.board.toString());
        	
    		if(lastStoneGroup.getStones().size() == 1) {
    			lastStoneGroup.removeStoneGroup(board);
    		} else {
    			lastPoint.removeStone();
    		}
    		
        	StoneGroupSet last = capturedStonesStack.remove(0);
        	if(last != null) {
        		for(StoneGroup captured : last.getSet()) {
	        		for(Point stone : captured.getStones()) {
	        			/*
	        			Move move = new Move(stone.getX(), stone.getY(), MoveType.NORMAL, getOpponent(lastMove.getPlayer()));
	        			simulateMove(this.board, move);
	        			*/
	        			
	        			
	        			Set<StoneGroup> neighbors = stone.getNeighborStoneGroups();
	        	    	//remove breath from enemy stone group and kill it if appropriate
	        	      	for(StoneGroup neighbor : neighbors) {
	        	    		if(neighbor.getOwner().equals(lastMove.getPlayer())){
	        	    			neighbor = neighbor.removeBreath(stone);
	        	    		}
	        	    	}
	        	      	for(StoneGroup neighbor : neighbors) {
	        	    		if(!neighbor.getOwner().equals(lastMove.getPlayer())) {
	        	    	        captured.joinStoneGroup(neighbor, stone);
	        	            }
	        	      	}
	        	      	for(Point reStone : captured.getStones()) {
	        	      		reStone.setStoneGroup(captured);
	        	      	}
	        		}
	        		lastMove.getPlayer().removeCaptives(captured.getStones());
        		}
        		lastCaptured.clear();
        	}	
    	} 
    	
    	this.moves.remove(this.moves.size() - 1);
    	this.currentPlayer = getOpponent(currentPlayer);
    }
    
    List<Move> getPossibleMoves(){
    	int boardSize = this.board.getSize();
    	List<Move> possibleMoves = new ArrayList<>();
    	
    	for(int x = 0; x < boardSize; ++x) {
    		for(int y = 0; y < boardSize; ++y) {
    			try {
    				Point point = this.board.getPoint(x, y);
    				if(point.isEmpty()) {
    					
    				}
    			} catch(OutOfBoardException e){
    				continue;
    			}
    			
    		}
    	}
    	
    	return possibleMoves;
    }
    
    Set<StoneGroup> getLastCapturedStoneGroup(){
    	return this.lastCaptured;
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
    
    
    private List<String> getPreviousBoardStates(){
    	return this.previousBoardStates;
    }

    public void start() {
    	this.state = State.ONGOING;
    }
    
    public Player getOpponent(Player player) {
    	if(this.players.get(0).equals(player)) {
    		return players.get(1);
    	}
    	return players.get(0);
    }
    
    public Player getCurrentPlayer() {
    	return this.currentPlayer;
    }
    
    public void addMove(Move move){
        moves.add(move);
    }
    
    public List<Move> getMoves(){
    	return this.moves;
    }
    
    public List<Player> getPlayers(){
    	return this.players;
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
    
    public boolean makeMove(Move move) {
    	if(simulateMove(this.board, move)) {
        	this.moves.add(move);
        	
        	if(lastCaptured.isEmpty()) {
          		this.capturedStonesStack.add(0, null);        		
        	} else {
            	Set<StoneGroup> capturedCopy = new HashSet<>(lastCaptured);
				StoneGroupSet stoneGroupSet = new StoneGroupSet();
				stoneGroupSet.setSet(capturedCopy);
           		this.capturedStonesStack.add(0, stoneGroupSet);        		
        	}
     	
        	String currentBoardState = this.board.toString();
            previousBoardStates.add(currentBoardState);

        	currentPlayer = move.getPlayer();
        	
        	return true;
    	}
    	return false;
    }
    
    private boolean simulateMove(Board board, Move move) {

		logger.info("State 0");
    	if(move.getPlayer() == currentPlayer) {

    		System.out.println("player problems");
    		return false;
    	}

		logger.info("State 1");
    	
    	if(move.getMoveType() == MoveType.PASS || move.getMoveType() == MoveType.SURRENDER || move.getMoveType() == MoveType.RESUMEGAME) {
            if(!lastCaptured.isEmpty()) {
            	lastCaptured.clear();
            }
    		return true;
    	}

		logger.info("State 2");

    	Point simulatedPoint;
    	try {
    		simulatedPoint = board.getPoint(move.getX(), move.getY());
    		if(!board.getPoint(move.getX(), move.getY()).isEmpty()){
    			return false;
    		}
    	} catch(OutOfBoardException e) {
    		return false;
    	}

		logger.info("State 3");

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

		logger.info("State 4");
      	
    	//merge friendly neighbor stone groups        
      	for(StoneGroup neighbor : neighbors) {
    		if(neighbor.getOwner().equals(move.getPlayer())) {
    	        newStoneGroup.joinStoneGroup(neighbor, simulatedPoint);
            }
      	}
    	
		  logger.info("State 5");

    	//suicide move check
    	if(newStoneGroup.getBreaths().size() == 0) {
    		this.board.setPointStoneGroup(simulatedPoint, null);
    		return false;
    	}

		logger.info("State 6");

    	//ko rule check;
    	String currentBoardState = this.board.toString();
		logger.info("\n" + currentBoardState);
		logger.info("\n" + ( previousBoardStates.size() >= 2 ? previousBoardStates.get(previousBoardStates.size() -  2) : ""));

        if(previousBoardStates.contains(currentBoardState) && 
			previousBoardStates.size() >= 2 &&
			previousBoardStates.get(previousBoardStates.size() -  2).equals(currentBoardState)
		) {
        	for(StoneGroup sg : capturedStoneGroups) {
        		for(Point stone : sg.getStones()) {
        			this.board.setPointStoneGroup(stone, sg);
        		}
        	}
        	simulatedPoint.setStoneGroup(null);
        	return false;
        }
		logger.info("State 7");

        //if ok then apply changes
      	for(Point stone : newStoneGroup.getStones()) {
      		stone.setStoneGroup(newStoneGroup);
      	}
        move.getPlayer().addCaptives(capturedStones);
        
        if(!lastCaptured.isEmpty()) {
        	lastCaptured.clear();
        }
        lastCaptured.addAll(capturedStoneGroups);

		logger.info("State 8");

        return true;
    }
	
	public void toggleDeadStoneGroup(int x, int y, Player player) {
		try {
        	Point point = this.board.getPoint(x, y);
			if(player != point.getStoneGroup().getOwner()){
				throw new RuntimeException("Invalid player");
			}
    		if(point.isEmpty()) {
    			deadStoneGroups.remove(point.getStoneGroup());
    		}else{
				deadStoneGroups.add(point.getStoneGroup());
			}
        	
    	} catch(OutOfBoardException e) {
    		return;
    	}
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
    
    public Set<Territory> getCurrentTerritories(){
    	return establishTerritories(null);
    }
    
    private Set<Territory> establishTerritories(Set<StoneGroup> deadStoneGroups){
		Set<Point> potentialTerritory = new HashSet<Point>();
		Set<Territory> territories = new HashSet<Territory>();
		Territory newTerritory;
		
		Point point;
		
		if(deadStoneGroups != null) {	
			for(int i = 0; i < this.board.getSize(); ++i) {
				for(int j = 0; j < this.board.getSize(); ++j) {
					point = this.board.getPoint(i, j);
					if(point.isEmpty() || deadStoneGroups.contains(point.getStoneGroup())) {
						potentialTerritory.add(point);
					}
				}
			}
		} else {	
			for(int i = 0; i < this.board.getSize(); ++i) {
				for(int j = 0; j < this.board.getSize(); ++j) {
					point = this.board.getPoint(i, j);
					if(point.isEmpty()) {
						potentialTerritory.add(point);
					}
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
					if(deadStoneGroups != null) {
						newTerritoryPoints.addAll(deadOrEmptyPoint.getEmptyNeighborPoints(deadStoneGroups));
					} else {
						newTerritoryPoints.addAll(deadOrEmptyPoint.getEmptyNeighborPoints());
					}
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
    	this.deadStoneGroups = new HashSet<>(); 
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
    	this.territories = establishTerritories(deadStoneGroups);
    	this.state = State.FINISHED;
    }
    
    public void agreeToFinalize(Player p) {
    	this.agreed.add(p);
    }

	public void disagreeToFinalize(Player p){
		if(this.agreed.contains(p)){
			this.agreed.remove(p);
		}
	}

	public void toggleAgreedToFinalize(Player p){
		if(this.agreed.contains(p)){
			this.agreed.remove(p);
		}else{
			this.agreed.add(p);
		}
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
    
	@JsonIgnore
    public int getPlayerScore(Player player) {
    	return player.getFinalScore();
    }
	
}