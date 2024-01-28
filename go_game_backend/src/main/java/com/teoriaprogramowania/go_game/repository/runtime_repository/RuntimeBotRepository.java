package com.teoriaprogramowania.go_game.repository.runtime_repository;

import org.springframework.stereotype.Component;

import com.teoriaprogramowania.go_game.game_bots.BotClient;
import com.teoriaprogramowania.go_game.repository.interfaces.BotRepositoryInterface;

@Component
public class RuntimeBotRepository implements BotRepositoryInterface{

    @Override
    public BotClient addBotClient(BotClient botClient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBotClient'");
    }
    
}
