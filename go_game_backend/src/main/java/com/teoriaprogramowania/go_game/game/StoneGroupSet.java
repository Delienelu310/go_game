package com.teoriaprogramowania.go_game.game;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class StoneGroupSet {

    @Id
    @GeneratedValue
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @Transient
    private Set<StoneGroup> set = new HashSet<>();

    public StoneGroupSet(){

    }
}
