package com.teoriaprogramowania.go_game.game;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.game.exceptions.OutOfBoardException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BoardRow> board;
	private int size;
	
	public Board() {
	}

	//new board initialization
	public Board(int size){
		this.size = size;
		this.board = new ArrayList<BoardRow>();

        for(int i = 0; i < size; ++i) {
			board.add(i, new BoardRow(size));
        	for(int j = 0; j < size; ++j) {
        		Point newPoint = new Point(i, j, this);
        		board.get(i).getPoints().add(newPoint);
        	}
		}
          
	}

	public Board(List<BoardRow> board, int size) {
		this.board = board;
		this.size = size;
	}

	public Board(Board board) {
		this.size = board.getSize();
		this.board = new ArrayList<BoardRow>();
		
		for(int i = 0; i < this.size; ++i) {
			this.board.add(i, new BoardRow(size));
			for(int j = 0; j < this.size; ++j) {
				Point oldPoint = board.getPoint(i, j);
				Point newPoint = new Point(oldPoint.getX(), oldPoint.getY(), this);
				
				
				newPoint.setStoneGroup(oldPoint.getStoneGroup());
				this.board.get(i).getPoints().add(newPoint);
			}
		}
		
	}
	
	public List<BoardRow> getBoard(){
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
		return board.get(x).getPoints().get(y);
	}
	
	public void addPoint(Point point) {
		this.board.get(point.getX()).addPoint(point);
	}
	
	public void setPointStoneGroup(Point point, StoneGroup stoneGroup){
		this.board.get(point.getX()).getPoints().get(point.getY()).setStoneGroup(stoneGroup);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StoneGroup stoneGroup = board.get(i).getPoints().get(j).getStoneGroup();
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
