package com.teoriaprogramowania.go_game.resources;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonFilter("Client")
public class Client {

    private Long id;

    private ClientDetails clientDetails = new ClientDetails();

    private Boolean isInRoom;

}
