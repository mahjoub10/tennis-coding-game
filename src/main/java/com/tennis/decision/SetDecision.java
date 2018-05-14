package com.tennis.decision;

import com.tennis.player.Player;

/**
 * Basic impelemtation od set decision
 */
public interface SetDecision extends ActionDecisionManager {


    /**
     * CHECK WHETHER THE CONCURRENT STAGE SHOULD BE ACTIVATED.
     * @param player
     * @return
     */
    boolean activateConcurrentStage(Player player);

    /**
     * CHECK WHETHER THE PLAYER IS A WINNER.
     * @param player
     */
    boolean checkWinner(Player player) ;
}
