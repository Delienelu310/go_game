package com.teoriaprogramowania.go_game.game;

import java.util.List;

import com.teoriaprogramowania.go_game.resources.Client;
import com.teoriaprogramowania.go_game.game.exceptions.InvalidMoveException;

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
    private Color[][] field; 
    private int size;

    private State state = State.CREATED;

    public Game(){

    }

    public Game(int size){
        this.size = size;
        field = new Color[size][size];
    }

    public void addMove(Move move) throws InvalidMoveException{
        if(move.getPass()) return;
        field[move.getX()][move.getY()] = move.getColor();
    }

    public void start(){

    }

    
}
