package com.teoriaprogramowania.go_game.resources;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ClientDetails {
    private String username;
    private String password;
}
