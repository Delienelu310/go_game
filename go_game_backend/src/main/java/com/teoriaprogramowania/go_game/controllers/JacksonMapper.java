package com.teoriaprogramowania.go_game.controllers;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Component
public class JacksonMapper {
    
    public JacksonMapper(){

    }

    public FilterProvider getRoomMainFilterProvider(){
        
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("Game", SimpleBeanPropertyFilter.serializeAllExcept("previousBoardStates"))
            .addFilter("Game_board", SimpleBeanPropertyFilter.serializeAll())
            .addFilter("Board_board", SimpleBeanPropertyFilter.serializeAllExcept("board"))
            .addFilter("Point_stoneGroup", SimpleBeanPropertyFilter.serializeAllExcept("stones", "breaths"))
            .addFilter("StoneGroup_owner", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("Player_client", SimpleBeanPropertyFilter.serializeAll())
            .addFilter("Game_moves", SimpleBeanPropertyFilter.serializeAll())
            .addFilter("Move_player", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("Game_deadStoneGroups", SimpleBeanPropertyFilter.serializeAllExcept("breaths"))
            .addFilter("StoneGroup_stones", SimpleBeanPropertyFilter.serializeAllExcept("board", "stoneGroup"))
            .addFilter("Game_territories", SimpleBeanPropertyFilter.serializeAllExcept("neighbors"))
            .addFilter("Territory_points", SimpleBeanPropertyFilter.serializeAllExcept("board", "stoneGroup"))
            .addFilter("Territory_owner", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("Game_players", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            
        ;
        return filterProvider;
    }

}
