package com.teoriaprogramowania.go_game.repository.mysqlRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.BotRepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.GameRepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.RoomRepositoryInterface;

@Repository
@Primary
public class MySqlRepository implements RepositoryInterface{

    private MySqlGameRepository gameRepository;
    private MySqlRoomRepository roomRepository;
    private MysqlClientRepository clientRepository;
    private MySqlBotRepository botRepository;


    public MySqlRepository( MySqlRoomRepository roomRepository,
            MysqlClientRepository clientRepository, 
            MySqlGameRepository gameRepository,
            MySqlBotRepository botRepository
    ) {
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
        this.clientRepository = clientRepository;
        this.botRepository = botRepository;
    }

    @Override
    public GameRepositoryInterface getGameRepository() {
        return gameRepository;
    }

    @Override
    public RoomRepositoryInterface getRoomRepository() {
        return roomRepository;
    }

    @Override
    public ClientRepositoryInterface getClientRepository() {
        return clientRepository;
    }

    @Override
    public BotRepositoryInterface getBotRepositoryInterface() {
        return botRepository;
    }
    

}
