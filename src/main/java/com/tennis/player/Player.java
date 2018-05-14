package com.tennis.player;

import java.util.UUID;

/**
 * Represents a player
 */
public class Player {

    private String name ;

    private String id ;

    public Player() {

        // GENERATE UNIQUE ID FOR EACH PLAYER
        this.id = UUID.randomUUID().toString() ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
