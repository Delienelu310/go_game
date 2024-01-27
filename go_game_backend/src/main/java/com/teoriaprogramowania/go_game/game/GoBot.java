package com.teoriaprogramowania.go_game.game;

import java.util.*;

public class GoBot {
    private Player botPlayer;
    private static final int MAX_DEPTH = 3;

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
                if (gameCopy .makeMove(move)) {
                    int moveValue = minimax(gameCopy , MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    
                    gameCopy.undo();
                    
                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        bestMove = move;
                    }
                }
            }
        }
        
        if(bestMove == null) {
        	return new Move(-1, -1, MoveType.PASS, botPlayer);
        }
        return bestMove;
    }
    
    private int minimax(Game game, int depth, int alpha, int beta, boolean isMaximizing) {
    	if(depth == 0 || game.hasChangedState()) {
    		return evaluateBoard(game);
    	}
        
    	int boardSize = game.getBoard().getSize();
    	if(isMaximizing) {
        	int bestVal = Integer.MIN_VALUE;
        	
    		for(int i = 0; i < boardSize; ++i) {
    			for(int j = 0; j < boardSize; ++j) {
    				Move move = new Move(i, j, MoveType.NORMAL, botPlayer); 
    				if(game.makeMove(move)) {
    					bestVal = Math.max(bestVal, minimax(game, depth - 1, alpha, beta, false));
    					game.undo();
    					
        				alpha = Math.max(alpha, bestVal);
                        if (beta <= alpha) {
                            break;
                        }
    				}
    			}
    		}

    		return bestVal;
    	} else {
        	int bestVal = Integer.MAX_VALUE;
        	
    		for(int i = 0; i < boardSize; ++i) {
        		for(int j = 0; j < boardSize; ++j) {
       				Move move = new Move(i, j, MoveType.NORMAL, game.getOpponent(botPlayer)); 
       				if(game.makeMove(move)) {
    					bestVal = Math.min(bestVal, minimax(game, depth - 1, alpha, beta, true));
    					game.undo();
           				
    					beta = Math.min(beta, bestVal);
                        if (beta <= alpha) {
                            break;
                        }
        			}

        		}
       		}
    		
    		return bestVal;
    	}
    }

    private int evaluateBoard(Game game) {
        int score = 0;
/*
        score += evaluateTerritory(game);
  */
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
        Set<StoneGroup> stoneGroups = new HashSet<>();
        
        for (int i = 0; i < game.getBoard().getSize(); ++i) {
            for (int j = 0; j < game.getBoard().getSize(); ++j) {
                Point point = game.getBoard().getPoint(i, j);
                if (!point.isEmpty()) {
                    stoneGroups.add(point.getStoneGroup());
                }
            }
        }
        
        for(StoneGroup group : stoneGroups) {
        	if (group.getOwner() == botPlayer) {
                stoneGroupScore += group.getBreaths().size();
                stoneGroupScore += group.getStones().size() * 4;
            } else if (group.getOwner() == game.getOpponent(botPlayer)) {
                stoneGroupScore -= group.getBreaths().size();
                stoneGroupScore -= group.getStones().size() * 2;
            }
        }

        return stoneGroupScore;
    }
}
