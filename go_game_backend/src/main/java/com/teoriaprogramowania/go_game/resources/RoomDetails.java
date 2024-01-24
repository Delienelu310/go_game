package com.teoriaprogramowania.go_game.resources;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RoomDetails {
    private String title;
    private String description;
}
