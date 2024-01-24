package com.teoriaprogramowania.go_game.game;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;


@Entity
public class Board {

	@Id
	@GeneratedValue
	private Long boardId;

	
	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@JsonFilter("Board_board")
	@OneToMany(cascade = CascadeType.ALL)
	private BoardRow[] board;
	private int size;
	
	public Board() {
	}

	//new board initialization
	public Board(int size){
		this.size = size;
		this.board = new BoardRow[size];

        for(int i = 0; i < size; ++i) {
			board[i] = new BoardRow(size);
        	for(int j = 0; j < size; ++j) {
        		Point newPoint = new Point(i, j, this);
        		board[i].addPoint(newPoint);
        	}
		}
          
	}

	public Board(BoardRow[] board, int size) {
		this.board = board;
		this.size = size;
	}

	public BoardRow[] getBoard(){
		return board;
	}
	
	public int getSize() {
		return this.size;
	}
	
	@JsonIgnore
	@Transient
	public Point getPoint(int x, int y) throws OutOfBoardException {
		if(x < 0 || x >= this.size || y < 0 || y >= this.size) {
			throw new OutOfBoardException();
		}
		return board[x].getPoints()[y];
	}
	
	public void addPoint(Point point) {
		this.board[point.getX()].addPoint(point);
	}
	
	public void setPointStoneGroup(Point point, StoneGroup stoneGroup){
		this.board[point.getX()].getPoints()[point.getY()].setStoneGroup(stoneGroup);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StoneGroup stoneGroup = board[i].getPoints()[j].getStoneGroup();
                if (stoneGroup == null) {
                    sb.append('.');
                } else {
                    sb.append(stoneGroup.getOwner());
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
