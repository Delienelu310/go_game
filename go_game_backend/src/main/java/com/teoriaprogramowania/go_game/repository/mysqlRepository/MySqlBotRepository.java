package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teoriaprogramowania.go_game.game_bots.BotClient;
import com.teoriaprogramowania.go_game.repository.interfaces.BotRepositoryInterface;

@Component
public class MySqlBotRepository implements BotRepositoryInterface{

    @Autowired
    private BotJpa botJpa;

    @Override
    public BotClient addBotClient(BotClient botClient) {
        return botJpa.save(botClient);
    }
    
}
