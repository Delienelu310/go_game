package com.teoriaprogramowania.go_game.game;

public class GoBot {
	private static final int maxDepth = 3;
	private Player botPlayer;
	
	public GoBot(Player botPlayer) {
		this.botPlayer = botPlayer;
	}
	
	public void makeMoveBot(Game game) {
		Move bestMove = minimax(game, maxDepth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
		game.makeMove(bestMove);
		
	}
	
	Move minimax(Game game, int maxDepth, boolean isMaximizingPlayer, int alpha, int beta){
		Move bestMove = null;
		
		//minimax algo with alpha beta pruning
		
		if(bestMove == null) {
			bestMove = new Move(-1, -1, MoveType.PASS, botPlayer);
		}
		return bestMove;
	}
	
}
