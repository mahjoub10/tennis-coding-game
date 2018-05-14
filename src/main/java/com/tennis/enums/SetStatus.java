package com.tennis.enums;

/**
 * Set status.
 */
public enum SetStatus {


    /**
     * NORMAL SCORE STAGE
     */
    NORMAL,

    /**
     * CONCURRENT STAGE, WHEN THE A PLAYER HAS 6 SCORE AND THE OTHER HAS 5.
     */
    CONCURRENT,

    /**
     * WIN THE SET
     */
    WINNER,

    // WIN THE CONCURRENT STAGE -> WIN THE MATCH
    WINNER_MATCH;
}
