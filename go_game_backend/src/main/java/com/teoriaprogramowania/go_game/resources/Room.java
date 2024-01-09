package com.teoriaprogramowania.go_game.resources;

import java.util.List;
import java.util.ArrayList;

import com.teoriaprogramowania.go_game.game.Game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {

    private Long id;
    private RoomDetails roomDetails;

    private Client admin;
    private List<Client> participants = new ArrayList<>();

    private Game game;
    
}
