package com.tennis.common;

/**
 * Super class for objects that encapsulate the information about game, set and match
 */
public abstract class TennisInformation {

    private  String idPlayer ;

    private   int score ;

    private String name;

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
