package com.teoriaprogramowania.go_game.resources;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.teoriaprogramowania.go_game.game.Game;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private RoomDetails roomDetails;

    @JsonFilter("Client")
    @ManyToOne
    private Client admin;
    
    @OneToMany
    private List<Client> participants = new ArrayList<>();

    @OneToOne
    private Game game;
    
}
