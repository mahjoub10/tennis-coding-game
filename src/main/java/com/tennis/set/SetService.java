package com.tennis.set;

import com.tennis.enums.RefereeDecision;
import com.tennis.enums.SetStatus;
import com.tennis.player.Player;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Set  actions
 */
public interface SetService {

    default RefereeDecision winSet(Player player, SetStatus status) {
        // ONLY ONE PLAYER CAN PLAY , THE OTHER SHOULD WAIT TILL THE LOCK IS RELEASED
        ReadWriteLock lock = new ReentrantReadWriteLock(true);
        // ACQUIRE LOCK
        Lock readLock = lock.readLock();
        RefereeDecision decision = RefereeDecision.CONTINUE;

        try {
            // ACQUIRE LOCK
            readLock.lock();

            switch (status) {

                case NORMAL:
                    decision = winSetWithNormalStage(player);
                    break;
                case CONCURRENT:
                    decision = winSetWithConcurrentStage(player);
                    break;
            }
        } finally {

            // RELEASE LOCK
            readLock.unlock();

            return decision;
        }
    }


    /**
     * Try to win withing normal stage.
     *
     * @param player
     * @return
     */
    RefereeDecision winSetWithNormalStage(Player player);

    /**
     * Try to win withing concurrent stage.
     *
     * @param player
     * @return
     */
    RefereeDecision winSetWithConcurrentStage(Player player);
}
