package com.tennis.set;

import com.tennis.decision.SetDecision;
import com.tennis.enums.RefereeDecision;
import com.tennis.enums.SetStatus;
import com.tennis.player.Player;

/**
 * Basic implementation of set service.
 */
public class SetServiceImpl implements SetService {

    private SetDecision setDecision;

    private Set set;


    @Override
    public RefereeDecision winSetWithNormalStage(Player player) {

        String id = player.getId();

        // GET CURRENT SCORE
        int score = set.getScore(id);

        // INCREMENT THE SCORE GAME.
        score = score + 1;

        // CHECK CONCURRENT STAGE
        if (score == 6) {
            boolean concurrentStage = setDecision.activateConcurrentStage(player);

            // THE FIRST PLAYER WHO REACH 7 WILL BE TH WINNER
            if (concurrentStage) {
                set.setNewStatus(id, SetStatus.CONCURRENT);
            }
        }

        // SET NEW SCORE.
        set.updateScore(id, score);

        boolean winner = setDecision.checkWinner(player);
        if (winner) {
            set.setNewStatus(id, SetStatus.WINNER);
        }

        RefereeDecision vote = setDecision.vote(player);
        return vote;

    }

    @Override
    public RefereeDecision winSetWithConcurrentStage(Player player) {

        String id = player.getId();

        // GET CURRENT SCORE
        int score = set.getScore(id);
        // SET NEW SCORE.
        set.updateScore(id, score);

        boolean winner = setDecision.checkWinner(player);
        if (winner) {
            set.setNewStatus(id, SetStatus.WINNER_MATCH);
        }

        RefereeDecision vote = setDecision.vote(player);
        return vote;

    }


    public void setSetDecision(SetDecision setDecision) {
        this.setDecision = setDecision;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
