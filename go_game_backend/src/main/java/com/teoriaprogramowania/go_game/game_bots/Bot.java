package com.teoriaprogramowania.go_game.game_bots;

import com.teoriaprogramowania.go_game.game.Game;
import com.teoriaprogramowania.go_game.game.Move;
import com.teoriaprogramowania.go_game.game.Player;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Entity
public abstract class Bot {


    @Id
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    abstract public Player getBotPlayer();
    abstract public void setBotPlayer(Player player);
    abstract public Move findBestMove(Game game);

}
