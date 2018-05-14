package com.tennis.referee;

import com.tennis.game.Game;
import com.tennis.player.Player;

// REFEREE ACTIONS
public interface Referee {


    /**
     * START NEW GAME.
     */
    void startGame() ;

    /**
     * START NEW MATCH.
     */
    void startMatch() ;

    /**
     * END THE CURRENT MATCH .
     */
    void endMatch(Player winner) ;

    /**
     * END THE CURRENT GAME.
     */
    void endGame(Game game) ;

    /**
     * START NEW SET
     */
    void startSet() ;

    /**
     * END SET.
     */
    void endSet(Player winner);
}
