package com.tennis.game;

import com.tennis.enums.GameStatus;
import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Common game interface.
 */
public interface GameService {

    default RefereeDecision winPoint(Player player, GameStatus status) {

        // ONLY ONE PLAYER CAN PLAY , THE OTHER SHOULD WAIT TILL THE LOCK IS RELEASED
        //Lock lock = new ReentrantLock();
        Lock lock = new ReentrantLock();

        RefereeDecision decision = RefereeDecision.CONTINUE;

        // ACQUIRE LOCK
        lock.lock();
        try {


            switch (status) {
                case NORMAL:
                    decision = winPointWithNormalScore(player);
                    break;
                case ADVANTAGE:
                    decision = winPointWithAdvantageScore(player);
                    break;
                case DEUCE:
                    decision = winPointWithDeuceScore(player);
                    break;
            }
        } finally {
            // RELEASE LOCK
            lock.unlock();
            return decision;
        }


    }

    /**
     * Win point withing normal stage.
     *
     * @param player
     * @return
     */
    RefereeDecision winPointWithNormalScore(Player player);

    /**
     * Win point withing advantage stage.
     *
     * @param player
     * @return
     */
    RefereeDecision winPointWithAdvantageScore(Player player);

    /**
     * Win point withing deuce score.
     *
     * @param player
     * @return
     */
    RefereeDecision winPointWithDeuceScore(Player player);
}
