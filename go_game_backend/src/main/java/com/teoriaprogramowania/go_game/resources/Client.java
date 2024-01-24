package com.teoriaprogramowania.go_game.resources;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonFilter("Client")
@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;
 
    @Embedded
    private ClientDetails clientDetails = new ClientDetails();

    private Boolean isInRoom = false;

}
