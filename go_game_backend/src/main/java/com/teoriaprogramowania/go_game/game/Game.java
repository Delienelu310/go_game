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

    private Client white;
    private Client black;

    private int whitePlayerCounter;
    private int blackPlayerCounter;

    private List<Move> moves;
    private Color[][] field; 

    public Game(){

    }

    public void addMove() throws InvalidMoveException{

    }

    public void start(){

    }

    
}
