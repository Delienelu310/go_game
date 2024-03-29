package com.teoriaprogramowania.go_game.game_bots;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teoriaprogramowania.go_game.resources.Client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BotClient extends Client{

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Bot bot;

    public BotClient() {
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
