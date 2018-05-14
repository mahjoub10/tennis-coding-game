package com.tennis.decision;

import com.tennis.common.TennisSession;
import com.tennis.enums.GameStatus;
import com.tennis.enums.RefereeDecision;
import com.tennis.game.Game;
import com.tennis.player.Player;
import com.tennis.referee.RefereeService;

import java.util.Objects;

/**
 * Basic implementation of game decision
 */
public class GameDecisionImpl implements GameDecision {



    private TennisSession tennisSession ;

    // REFEREE ACTIONS
    private RefereeService refereeService ;

    private Game game ;


    /**
     * Custom constructor.
     *
     * @param tennisSession
     * @param game
     * @param refereeService
     */
    public GameDecisionImpl(TennisSession tennisSession, Game game, RefereeService refereeService) {
        this.tennisSession = tennisSession;
        this.refereeService = refereeService;
        this.game = game ;
    }

    @Override
    public RefereeDecision vote(Player player) {

        // CHECK THE PLAYER INSTANCE
        if (Objects.nonNull(player)) {
            GameStatus currentStatus = game.getCurrentStatus(player.getId());

            // CHECK IF THE PLAYER  HAS WON.
            if (currentStatus == GameStatus.WINNER) {

                // SET WINNER
                game.setWinner(player);

                // A WINNER HAS BEEN FOUND, THE MATCH SHOULD BE STOPPED
                refereeService.endGame(game);

                return RefereeDecision.WIN;
            } else {
                return RefereeDecision.CONTINUE;
            }
        }

        return RefereeDecision.CONTINUE;


    }

    @Override
    public boolean activateDeuceScore(Player player) {

        if(Objects.nonNull(player)){

            // GET THE OPPONENT.
            Player opponent = tennisSession.getOpponent(player.getId());

            // GET CURRENT SCORE
            int currentScore = game.getScore(player.getId());

            // IF THE OPPONENT SCORE HAS REACHED 40, THE DEUCE STAGE SHOULD BE ACTIVATED.
            if (currentScore == 40) {
                game.setNewStatus(opponent.getId(), GameStatus.DEUCE);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean activateAdvantageScore(Player player) {

        // GET OPPONENT
        Player opponent = tennisSession.getOpponent(player.getId());

        // GET CURRENT STATUS
        GameStatus opponentStatus = game.getCurrentStatus(opponent.getId());

        return opponentStatus != GameStatus.ADVANTAGE;
    }

    @Override
    public boolean checkWinner(Player player) {
        // GET THE OPPONENT
        Player opponent = tennisSession.getOpponent(player.getId());

        // GET CURRENT STATUS
        GameStatus opponentStatus = game.getCurrentStatus(opponent.getId());

        // IF THE OPPONENT HAS ADVANTAGE  , WE SHOULD GET BACK BOTH SCORE TO DEUCE
        if (opponentStatus == GameStatus.ADVANTAGE) {
            game.setNewStatus(opponent.getId(), GameStatus.DEUCE);
            return false;

        } else {
            return true;
        }
    }




}
