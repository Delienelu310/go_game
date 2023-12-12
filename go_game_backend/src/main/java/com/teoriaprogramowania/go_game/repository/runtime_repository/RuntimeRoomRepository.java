package com.teoriaprogramowania.go_game.repository.runtime_repository;

import org.springframework.stereotype.Repository;

import com.teoriaprogramowania.go_game.repository.interfaces.ClientRepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.GameRepositoryInterface;
import com.teoriaprogramowania.go_game.repository.interfaces.RoomRepositoryInterface;

@Repository
public class RuntimeRoomRepository implements RoomRepositoryInterface{

    private RuntimeGameRepository gameRepository; 
    private RuntimeClientRepository clientRepository;
    private RuntimeRoomRepository roomRepository;

    public RuntimeRoomRepository(
        RuntimeGameRepository gameRepository, 
        RuntimeClientRepository clientRepository,
        RuntimeRoomRepository roomRepository
    ){
        this.gameRepository = gameRepository;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
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
    
}
