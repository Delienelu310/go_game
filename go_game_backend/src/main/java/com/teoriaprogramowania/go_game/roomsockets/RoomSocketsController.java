package com.teoriaprogramowania.go_game.roomsockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teoriaprogramowania.go_game.GoGameApplication;
import com.teoriaprogramowania.go_game.jacksonMappers.JacksonMapperCollection;
import com.teoriaprogramowania.go_game.repository.interfaces.RepositoryInterface;
import com.teoriaprogramowania.go_game.resources.Room;

@Controller
public class RoomSocketsController {

    private Logger logger = LoggerFactory.getLogger(GoGameApplication.class);

    private RepositoryInterface repositoryInterface;
    private JacksonMapperCollection jacksonMapper;

    public RoomSocketsController(RepositoryInterface repositoryInterface, JacksonMapperCollection jacksonMapper) {
        this.repositoryInterface = repositoryInterface;
        this.jacksonMapper = jacksonMapper;
    }


    @MessageMapping("/{room_id}/refresh")
    @SendTo("/game/{room_id}")
    public String refresh(@DestinationVariable("room_id") Long roomId){

        Room room = repositoryInterface.getRoomRepository().retrieveRoomById(roomId);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setFilterProvider(jacksonMapper.getGameJacksonMapper().getMainFilterProvider());

        
        try{
            return objectMapper.writeValueAsString(room);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

        // return "{}";

    }

}
