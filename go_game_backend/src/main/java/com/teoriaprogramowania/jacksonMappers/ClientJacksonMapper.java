package com.teoriaprogramowania.jacksonMappers;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.teoriaprogramowania.go_game.resources.Client;

@Component
public class ClientJacksonMapper {
    public ClientJacksonMapper(){

    }

    public FilterProvider getMainFilterProvider(){
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("client", SimpleBeanPropertyFilter.serializeAllExcept("currentRoom"));
        
        return filterProvider;
    }

    public MappingJacksonValue getMainFilterMapped(Client client){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(client);
        mappingJacksonValue.setFilters(getMainFilterProvider());
        return mappingJacksonValue;
    }
}
