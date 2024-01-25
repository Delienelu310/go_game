package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class GoBot {
    private Player botPlayer;
    private static final int MAX_DEPTH = 2;

    public GoBot(Player botPlayer) {
        this.botPlayer = botPlayer;
    }

    public Move findBestMove(Game game) {
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        
        Game gameCopy = new Game(game);
        
        int boardSize = game.getBoard().getSize();
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                Move move = new Move(i, j, MoveType.NORMAL, botPlayer);
                if (gameCopy.makeMove(move)) {
                    int moveValue = minimax(gameCopy, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                	
                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        bestMove = move;
                    }

                    gameCopy.undo();
                }
            }
        }
        if(bestMove == null) {
        	return new Move(-1, -1, MoveType.PASS, botPlayer);
        }
        return bestMove;
    }

    private int minimax(Game game, int depth, int alpha, int beta, boolean isMaximizing) {
        if (depth == 0 || game.hasChangedState()) {
            return evaluateBoard(game);
        }
        
        int boardSize = game.getBoard().getSize();
        
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < boardSize; ++i) {
                for (int j = 0; j < boardSize; ++j) {
                    Move move = new Move(i, j, MoveType.NORMAL, botPlayer);
                    if (game.makeMove(move)) {
                        int eval = minimax(game, depth - 1, alpha, beta, false);
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        game.undo();
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < boardSize; ++i) {
                for (int j = 0; j < boardSize; ++j) {
                    Move move = new Move(i, j, MoveType.NORMAL, game.getOpponent(botPlayer));
                    if (game.makeMove(move)) {
                        int eval = minimax(game, depth - 1, alpha, beta, true);
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        game.undo();
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    private int evaluateBoard(Game game) {
        int score = 0;

        score += evaluateTerritory(game);
  
        score += evaluateStoneGroups(game);

        return score;
    }

    private int evaluateTerritory(Game game) {
        int territoryScore = 0;

        Set<Territory> territories = game.getCurrentTerritories();
        for (Territory territory : territories) {
            if (territory.getOwner() == botPlayer) {
                territoryScore += territory.getPoints().size();
            } else if (territory.getOwner() == game.getOpponent(botPlayer)) {
                territoryScore -= territory.getPoints().size();
            }
        }

        return territoryScore;
    }

    private int evaluateStoneGroups(Game game) {
        int stoneGroupScore = 0;

        for (int i = 0; i < game.getBoard().getSize(); ++i) {
            for (int j = 0; j < game.getBoard().getSize(); ++j) {
                Point point = game.getBoard().getPoint(i, j);
                if (!point.isEmpty()) {
                    StoneGroup group = point.getStoneGroup();
                    if (group.getOwner() == botPlayer) {
                        stoneGroupScore += group.getBreaths().size();
                    } else if (group.getOwner() == game.getOpponent(botPlayer)) {
                        stoneGroupScore -= group.getBreaths().size();
                    }
                }
            }
        }

        return stoneGroupScore;
    }
}
