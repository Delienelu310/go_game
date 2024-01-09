package com.teoriaprogramowania.go_game.jacksonMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JacksonMapperCollection {
    @Autowired
    private GameJacksonMapper gameJacksonMapper;
    @Autowired
    private ClientJacksonMapper clientJacksonMapper;

    public JacksonMapperCollection(){

    }
}
