package com.teoriaprogramowania.jacksonMappers;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JacksonMapperCollection {
    private GameJacksonMapper gameJacksonMapper;
    private ClientJacksonMapper clientJacksonMapper;
}
