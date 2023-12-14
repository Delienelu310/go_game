package com.teoriaprogramowania.go_game.resources;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Client {

    private Long id;

    private ClientDetails clientDetails = new ClientDetails();

    private Room currentRoom;

}
