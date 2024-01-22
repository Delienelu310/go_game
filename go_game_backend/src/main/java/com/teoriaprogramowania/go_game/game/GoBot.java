package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class GoBot {
	private static final int maxDepth = 3;
	private Player botPlayer;
	
	public GoBot(Player botPlayer) {
		this.botPlayer = botPlayer;
	}
	
	public Move makeMoveBot(Game game) {
		int[] result = minimax(game, maxDepth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
		Move bestMove = null;
		
		if(result[1] == -1) {
			bestMove = new Move(-1, -1, MoveType.PASS, botPlayer);
		} else {
			bestMove = new Move(result[1], result[2], MoveType.NORMAL, botPlayer);
		}
		return bestMove;
	}
	
	private int[] minimax(Game game, int depth, boolean isMaximizingPlayer, int alpha, int beta){
		// at index 0: evaluation of the game
		// at index 1: x
		// at index 2: y
		
		if(depth == 0 || game.hasChangedState()) {
			return new int[] {evaluateGame(game), -1, -1};
		}
		
		List<Point> backupCaptives = botPlayer.getCaptives();
		
		if(isMaximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			Move bestMove = null;
			
			for(Move move : game.getValidMoves(botPlayer)) {
				Game newGame = new Game(game);
				newGame.makeMove(move);
				int eval = minimax(newGame, depth - 1, false, alpha, beta)[0];
				
				botPlayer.setCaptives(backupCaptives);

				if(eval > maxEval) {
					maxEval = eval;
					bestMove = move;
				}
				alpha = Math.max(alpha, eval);
				if(beta <= alpha) {
					break;
				}
			}
			
			if(bestMove != null){
				return new int[] {maxEval, bestMove.getX(), bestMove.getY()};
			}
			return new int[] {maxEval, -1, -1};
		} else {
			int minEval = Integer.MAX_VALUE;
			Move bestMove = null;
			
			for(Move move : game.getValidMoves(game.getOpponent(botPlayer))) {
				Game newGame = new Game(game);
				newGame.makeMove(move);
				int eval = minimax(newGame, depth - 1, true, alpha, beta)[0];
				
				botPlayer.setCaptives(backupCaptives);
				
				if(eval < minEval) {
					minEval = eval;
					bestMove = move;
				}
				
				beta = Math.min(beta, eval);
				if(beta <= alpha) {
					break;
				}
			}
			
			if(bestMove != null){
				return new int[] {minEval, bestMove.getX(), bestMove.getY()};
			}
			return new int[] {minEval, -1, -1};
		}
	}
	
	private int evaluateGame(Game game) {
		int score = 0;
		
		Player opponent = game.getOpponent(botPlayer);
		
		int myStones = botPlayer.getCaptives().size();
		int opponentStones = opponent.getCaptives().size();
		
		int myTerritory = 0;
		int opponentTerritory = 0;
		
		for(Territory territory : game.getCurrentTerritories()) {
			if(territory.getOwner() == botPlayer) {
				myTerritory += territory.getPoints().size();
			} else if(territory.getOwner() != null) {
				opponentTerritory += territory.getPoints().size();
			}
		}
		
		score += myStones;
		score += myTerritory;
		
		score -= opponentStones;
		score -= opponentTerritory;
		
		return score;
	}
	
}
