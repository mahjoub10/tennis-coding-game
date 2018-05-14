package com.tennis.common;

import com.tennis.player.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class ScoreHolder {

    protected Map<String, TennisInformation> scores = new HashMap<>();


    /**
     * Default constructor.
     */
    public ScoreHolder() {
    }


    protected abstract void updateScore(String id, Integer score);


    protected abstract int getScore(String id);

    protected  abstract  void setWinner(Player player);
}
