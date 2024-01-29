package com.teoriaprogramowania.go_game.game;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import java.util.*;

@Entity
public class Point {

	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//class represents a singular point on the board
	private int x;					//coordinate x
	private int y;					//coordinate y

	@JsonFilter("Point_board")
	@ManyToOne(cascade = CascadeType.ALL)
	private Board board;			//board to which the point belongs

	@JsonFilter("Point_stoneGroup")
	@ManyToOne(cascade = CascadeType.ALL)
	private StoneGroup stoneGroup;	//group of stones to which the point belongs

	private boolean isEmpty;		//true if the point belongs to some stone group
	
	public Point() {
	}

	public Point(int x, int y, Board board) {
		this.board = board;
		this.x = x;
		this.y = y;
		isEmpty = true;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
    
	public void setX(int x) {
    	this.x = x;
    }

	public void setY(int y) {
    	this.y = y;
    }
	
	public boolean isEmpty() {
		return this.isEmpty;
	}
	
	public StoneGroup getStoneGroup() {
		return this.stoneGroup;
	}
	
	public void setStoneGroup(StoneGroup stoneGroup) {
		this.stoneGroup = stoneGroup;
		this.isEmpty = false;
		if(stoneGroup == null) {
			this.isEmpty = true;
		}
		this.board.addPoint(this);
	}
	@JsonIgnore
	@Transient
	public Player getOwner() {
		return this.stoneGroup.getOwner();
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Point point = (Point) obj;
		return this.x == point.x && this.y == point.y;
	}
	@JsonIgnore
	public Set<StoneGroup> getNeighborStoneGroups(){
		Set<StoneGroup> neighborStoneGroups = new HashSet<StoneGroup>();
		int dx[] = {-1, 1, 0, 0};
		int dy[] = {0, 0, 1, -1};
		for(int i = 0; i < 4; ++i) {
			int newX = this.x + dx[i];
			int newY = this.y + dy[i];
			
			try {
				Point newPoint = this.board.getPoint(newX, newY);	
				//continue if point empty
				if(!newPoint.isEmpty()) {
					if(newPoint.getStoneGroup() == this.getStoneGroup()) {
						continue;
					}
					neighborStoneGroups.add(newPoint.getStoneGroup());
				}	
			} catch(OutOfBoardException e) {
				//continue if point out of board
				continue;
			}
		}
		return neighborStoneGroups;
	}
	
	//get empty neighbors
	@JsonIgnore
	public Set<Point> getEmptyNeighborPoints(){
		Set<Point> emptyNeighborPoints = new HashSet<Point>();
		int dx[] = {-1, 1, 0, 0};
		int dy[] = {0, 0, 1, -1};
		for(int i = 0; i < 4; ++i) {
			int newX = this.x + dx[i];
			int newY = this.y + dy[i];			

			try {
				//continue if point empty
				Point newPoint = this.board.getPoint(newX, newY);
				if(newPoint.isEmpty()) {
					emptyNeighborPoints.add(newPoint);
				}
			} catch(OutOfBoardException e) {
				//continue if point out of board
				continue;
			}
		}
		return emptyNeighborPoints;
	}

	public Set<Point> getEmptyNeighborPoints(Set<StoneGroup> deadStoneGroups){
		Set<Point> emptyNeighborPoints = new HashSet<Point>();
		int dx[] = {-1, 1, 0, 0};
		int dy[] = {0, 0, 1, -1};
		for(int i = 0; i < 4; ++i) {
			int newX = this.x + dx[i];
			int newY = this.y + dy[i];			

			try {
				//continue if point empty
				Point newPoint = this.board.getPoint(newX, newY);
				if(newPoint.isEmpty() || deadStoneGroups.contains(newPoint.getStoneGroup())) {
					emptyNeighborPoints.add(newPoint);
				}
			} catch(OutOfBoardException e) {
				//continue if point out of board
				continue;
			}
		}
		return emptyNeighborPoints;
	}
	
	public void removeStone() {
		if(!this.isEmpty) {
			this.stoneGroup = null;
			this.isEmpty = true;
			for(StoneGroup neighborStoneGroup : this.getNeighborStoneGroups()) {
				neighborStoneGroup.addBreath(this);
			}
			this.board.addPoint(this);
		}
	}
}
