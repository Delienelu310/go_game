package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teoriaprogramowania.go_game.game_bots.BotClient;

public interface  BotJpa extends JpaRepository<BotClient, Long>{
    
}
