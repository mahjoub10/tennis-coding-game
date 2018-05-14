package com.tennis.set;

import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;
import com.tennis.referee.RefereeService;

/**
 * Repsents a set launcher.
 */
public class SetWrapper {


    private SetService setService;

    private Set set;

    private RefereeService refereeService;

    public SetWrapper(SetService setService, Set set, RefereeService refereeService) {
        this.setService = setService;
        this.set = set;
        this.refereeService = refereeService;
    }

    public void play(Player player)  {

        RefereeDecision refereeDecision = setService.winSet(player, set.getCurrentStatus(player.getId()));

        if (refereeDecision == RefereeDecision.CONTINUE) {
            refereeService.startGame();
        }

    }
}

