package com.tennis.decision;

import com.tennis.player.Player;

/**
 * Common action to decide game
 */
public interface GameDecision extends  ActionDecisionManager {

    /**
     * Activate deuce score
     * @param player
     * @return
     */
    boolean activateDeuceScore(Player player);

    /**
     * Activate advantage score.
     * @param player
     * @return
     */
    boolean activateAdvantageScore(Player player);

    /**
     * CHeck if the current player is th winner.
     *
     * @param player
     * @return
     */
    boolean checkWinner(Player player) ;
}
