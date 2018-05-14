package com.tennis.decision;

import com.tennis.common.TennisSession;
import com.tennis.enums.RefereeDecision;
import com.tennis.enums.SetStatus;
import com.tennis.player.Player;
import com.tennis.referee.RefereeService;
import com.tennis.set.Set;

import java.util.Objects;

public class SetDecisionImpl implements SetDecision {

    private TennisSession session;

    // REFEREE ACTIONS
    private RefereeService refereeService ;

    private Set set ;

    public SetDecisionImpl(TennisSession session, RefereeService refereeService, Set set) {
        this.session = session;
        this.refereeService = refereeService;
        this.set = set;
    }

    @Override
    public RefereeDecision vote(Player player) {

        // CHECK THE PLAYER INSTANCE
        if (Objects.nonNull(player)) {
            SetStatus currentStatus = set.getCurrentStatus(player.getId());

            if (currentStatus == SetStatus.WINNER) {

                // DECLARE WINNER
                set.setWinner(player);

                // A WINNER HAS BEEN FOUND, THE MATCH SHOULD BE STOPPED
                refereeService.endSet(null);

                return RefereeDecision.WIN;
            }

             else if(currentStatus == SetStatus.WINNER_MATCH) {

                // DECLARE WINNER
                set.setWinner(player);

                // END THE SET AND THE MATCH
                refereeService.endSet(player);

                return RefereeDecision.WIN;
            }
            else {
                return RefereeDecision.CONTINUE;
            }
        }

        return RefereeDecision.CONTINUE;
    }



    @Override
    public boolean activateConcurrentStage(Player player) {
        if(Objects.nonNull(player)){
            // GET THE OPPONENT.
            Player opponent = session.getOpponent(player.getId());
            int score = set.getScore(opponent.getId());

            if (score == 5) {
                set.setNewStatus(opponent.getId(), SetStatus.CONCURRENT);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkWinner(Player player) {
        Player opponent = session.getOpponent(player.getId());
        SetStatus currentStatus = set.getCurrentStatus(player.getId());
        int playerScore = set.getScore(player.getId());
        int opponentScore = set.getScore(opponent.getId());

        if(currentStatus == SetStatus.NORMAL) {
            if(playerScore==6 && opponentScore < 5){
                return true;
            }
        } else if(currentStatus == SetStatus.CONCURRENT) {
            return  playerScore == 7;
        }

        return  false;
    }
}
