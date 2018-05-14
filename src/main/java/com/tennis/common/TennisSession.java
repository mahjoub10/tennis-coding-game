package com.tennis.common;

import com.tennis.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the context of current tennis session
 */
public class TennisSession {

    // IT'S BASICALLY A LIST OF TWO PLAYERS
    private  Map<String, Player> players = new HashMap<>();


    // EACH PLAYER HAS TO SUBSCRIBE, IN ORDER TO PARTICIPATE IN A GAME
    public void subscribe(Player player) {

        // ACCEPT ONLY TWO PLAYER .
        if (players.size() < 2) {
            players.put(player.getId(), player);
        }

    }

    // GET THE TARGET PLAYER USING HIS ID
    public Player getOpponent(String id) {

        return players/**/
                .entrySet()/**/
                .stream() /**/
                .filter(m -> !m.getKey().equals(id))/**/
                .findFirst() /**/
                .map(Map.Entry::getValue) /**/
                .orElseThrow(() -> new IllegalArgumentException(String.format("No opponent  player with id : '%s' has been found "
                        , id)));
    }

    public Map<String, Player> getPlayers() {
        return players;
    }
}
