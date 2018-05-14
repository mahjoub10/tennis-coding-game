package com.tennis.game;

import com.tennis.decision.GameDecision;
import com.tennis.enums.GameStatus;
import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;

/**
 * Basic implementation for tennis game .
 */
public class GameServiceImpl implements GameService {



    private GameDecision actionDecisionManager;

    private Game game ;

    @Override
    public synchronized RefereeDecision winPointWithNormalScore(Player player) {

        String id = player.getId();

        // GET OLD SCORE
        int score = game.getScore(id);

        // GET NEW COMPUTED SCORE
        int newScore = increment(score);

        // SET THE NEW SCORE
        game.updateScore(id, newScore);

        // UPDATE SCORE
        this.updateScore(id, newScore);

        // CHECK THE DEUCE SCORE
        if (newScore == 40) {

            boolean deuceScore = actionDecisionManager.activateDeuceScore(player);
            if (deuceScore) {
                game.setNewStatus(id, GameStatus.DEUCE);
            }
            else {
                game.setNewStatus(id, GameStatus.WINNER);
            }
        }

        return actionDecisionManager.vote(player);

    }

    @Override
    public synchronized RefereeDecision winPointWithAdvantageScore(Player player) {

        boolean winner = actionDecisionManager.checkWinner(player);
        String id = player.getId();

        if(!winner){
            game.setNewStatus(id, GameStatus.DEUCE);
        }
         else {
            game.setNewStatus(id, GameStatus.WINNER);
        }

        return actionDecisionManager.vote(player);

    }

    @Override
    public synchronized   RefereeDecision winPointWithDeuceScore(Player player) {

        // CHECK ADVANTAGE CONDITIONS
        boolean advantageScore = actionDecisionManager.activateAdvantageScore(player);
        if(advantageScore){
            game.setNewStatus(player.getId(), GameStatus.ADVANTAGE);
        }

        return actionDecisionManager.vote(player);

    }


    // BASIC RULES FOR SCORE 0 -> 15 -> 30 -> 40
    private int increment(int score) {

        switch (score) {
            case 0:
                return 15;
            case 15:
                return 30;
            case 30:
                return 40;
            case  40 :
                return 40;
            default:
                return 0;
        }
    }

    public void setGameDecision(GameDecision actionDecisionManager) {
        this.actionDecisionManager = actionDecisionManager;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void updateScore(String id, int score) {
        this.game.updateScore(id, score);
    }
}
