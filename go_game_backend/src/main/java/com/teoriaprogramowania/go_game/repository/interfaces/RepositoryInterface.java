package com.teoriaprogramowania.go_game.repository.interfaces;


public interface RepositoryInterface {
    public GameRepositoryInterface getGameRepository();
    public RoomRepositoryInterface getRoomRepository();
    public ClientRepositoryInterface getClientRepository();
    public BotRepositoryInterface getBotRepositoryInterface();
}
