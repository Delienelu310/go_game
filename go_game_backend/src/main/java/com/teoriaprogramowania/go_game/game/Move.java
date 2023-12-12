package com.teoriaprogramowania.go_game.game;

import lombok.Data;

@Data
public class Move {
    
    private int x;
    private int y;

    private Boolean pass = false;
    private Color color;

    private Game game;
    
    public Move(){

    }

    public Move(int x, int y, Boolean pass, Color color){

    }
}
