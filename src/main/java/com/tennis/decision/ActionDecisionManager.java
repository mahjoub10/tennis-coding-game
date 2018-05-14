package com.tennis.decision;

import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;

/**
 * Common interface for all voters .
 */
public interface ActionDecisionManager {

    RefereeDecision vote(Player player) ;


}
