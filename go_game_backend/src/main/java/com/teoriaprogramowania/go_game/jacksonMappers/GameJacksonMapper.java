package com.teoriaprogramowania.go_game.jacksonMappers;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Component
public class GameJacksonMapper {
    
    public GameJacksonMapper(){

    }

    public FilterProvider getMainFilterProvider(){
        
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("Game", SimpleBeanPropertyFilter.serializeAllExcept("previousBoardStates"))
            .addFilter("Game_board", SimpleBeanPropertyFilter.serializeAll())
            .addFilter("Board_board", SimpleBeanPropertyFilter.serializeAllExcept("board"))
            .addFilter("Point_stoneGroup", SimpleBeanPropertyFilter.serializeAllExcept("stones", "breaths"))
            .addFilter("StoneGroup_owner", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("StoneGroup_breaths", SimpleBeanPropertyFilter.filterOutAll())
            .addFilter("Player_client", SimpleBeanPropertyFilter.serializeAllExcept())
            .addFilter("Game_moves", SimpleBeanPropertyFilter.serializeAll())
            .addFilter("Move_player", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("Game_deadStoneGroups", SimpleBeanPropertyFilter.serializeAllExcept("breaths"))
            .addFilter("StoneGroup_stones", SimpleBeanPropertyFilter.serializeAllExcept("board", "stoneGroup"))
            .addFilter("Game_territories", SimpleBeanPropertyFilter.serializeAllExcept("neighbors"))
            .addFilter("Territory_points", SimpleBeanPropertyFilter.serializeAllExcept("board", "stoneGroup"))
            .addFilter("Territory_owner", SimpleBeanPropertyFilter.serializeAllExcept("captives", "territory"))
            .addFilter("Game_players", SimpleBeanPropertyFilter.serializeAllExcept( "territory"))
            .addFilter("Player_captives", SimpleBeanPropertyFilter.filterOutAll())
            .addFilter("Client", SimpleBeanPropertyFilter.serializeAll())
        ;
        return filterProvider;
    }

}
