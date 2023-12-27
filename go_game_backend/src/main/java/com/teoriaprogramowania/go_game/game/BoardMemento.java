package com.teoriaprogramowania.go_game.game;

public class BoardMemento {
    private Point[][] state;

    public BoardMemento(Point[][] state) {
        this.state = new Point[state.length][];
        for (int i = 0; i < state.length; i++) {
            this.state[i] = state[i].clone();
        }
    }

    public Point[][] getState() {
        return state;
    }
}
