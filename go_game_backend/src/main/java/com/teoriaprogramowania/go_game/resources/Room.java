package com.teoriaprogramowania.go_game.resources;

import java.util.List;

import com.teoriaprogramowania.go_game.game.Game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {

    private Long id;
    private RoomDetails roomDetails;

    private Client admin;
    private List<Client> participants;

    private Client whitePlayer;
    private Client blackPlayer;

    private Game game;
    
}
