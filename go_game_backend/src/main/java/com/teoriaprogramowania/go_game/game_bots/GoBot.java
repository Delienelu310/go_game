package com.teoriaprogramowania.go_game.game_bots;

import java.util.*;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.MoveType;
import com.teoriaprogramowania.go_game.game.Player;
import com.teoriaprogramowania.go_game.game.Point;
import com.teoriaprogramowania.go_game.game.StoneGroup;
import com.teoriaprogramowania.go_game.game.Territory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("GoBot")
public class GoBot extends Bot{

    @Transient
    private Player botPlayer;
    
    @Transient
    private static final int MAX_DEPTH = 3;
    
    public Player getBotPlayer() {
        return botPlayer;
    }

    public void setBotPlayer(Player botPlayer) {
        this.botPlayer = botPlayer;
    }

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
                    int moveValue = minimax(gameCopy , MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    
//					int moveValue = minimaxIterative(gameCopy);
                    gameCopy.undo();
                    
                    if (moveValue > bestValue && moveValue > 0) {
                        bestValue = moveValue;
                        bestMove = move;
                    }
                }
            }
        }
        
        if(bestValue <= 0) {
/*        	if(bestMove == null) {
        		return new Move(-1, -1, MoveType.SURRENDER, botPlayer);
        	}
      		return new Move(-1, -1, MoveType.SURRENDER, botPlayer);
*/
      		return new Move(-1, -1, MoveType.SURRENDER, botPlayer);
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
    

    private int minimaxIterative(Game game) {
        
    	int alpha = Integer.MIN_VALUE;
    	int beta = Integer.MAX_VALUE;
    	
    	int boardSize = game.getBoard().getSize();
        int bestValMin = Integer.MAX_VALUE;
        	
    	for(int i = 0; i < boardSize; ++i) {
        	for(int j = 0; j < boardSize; ++j) {
       			Move move = new Move(i, j, MoveType.NORMAL, game.getOpponent(botPlayer)); 
       			if(game.makeMove(move)) {
       	        	int bestValMax = Integer.MIN_VALUE;
       	        	
       				for(int x = 0; x < boardSize; ++x) {
       					for(int y = 0; y < boardSize; ++y) {
       						Move move2 = new Move(x, y, MoveType.NORMAL, botPlayer);
       						if(game.makeMove(move2)) {
       							bestValMax = Math.max(bestValMax, evaluateBoard(game));
       							game.undo();
       							
       							alpha = Math.max(alpha, bestValMax);
       							if(beta <= alpha) {
       								break;
       							}
       						}
       					}
						if(beta <= alpha) {
   							break;
   						}
       				}
       				
       				bestValMin = Math.min(bestValMin, bestValMax);
    				game.undo();
           				
    				beta = Math.min(beta, bestValMin);
    				if (beta <= alpha) {
    					break;
    				}
       			}
        	}
			if (beta <= alpha) {
				break;
			}
       	}
    		
    	return bestValMin;
    }

    private int evaluateBoard(Game game) {
        int score = 0;

//        score += evaluateTerritory(game);
  
        score += evaluateStoneGroups(game);
        
        score += botPlayer.getCaptives().size();
        score -= game.getOpponent(botPlayer).getCaptives().size();
        
        return score;
    }

    private int evaluateTerritory(Game game) {
        int territoryScore = 0;

        Set<Territory> territories = game.getCurrentTerritories();
        for (Territory territory : territories) {
            if (territory.getOwner() == botPlayer) {
                territoryScore += territory.getPoints().size() * 10;
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
                stoneGroupScore += group.getStones().size();
            } else if (group.getOwner() == game.getOpponent(botPlayer)) {
                stoneGroupScore -= group.getBreaths().size() / 2;
                stoneGroupScore -= group.getStones().size() / 2;
            }
        }

        return stoneGroupScore;
    }

    

}
