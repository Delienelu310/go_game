package com.teoriaprogramowania.go_game.resources;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Client {

    private Long id;

    private String username;
    private String password;

    private Room currentRoom;

}
