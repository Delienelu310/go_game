package com.teoriaprogramowania.go_game.game_bots;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.Player;

public interface Bot {
    public void setPlayer(Player player);
    public Move findBestMove(Game game);
}
