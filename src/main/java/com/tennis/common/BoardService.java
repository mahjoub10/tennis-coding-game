package com.tennis.common;

import com.tennis.player.Player;

/**
 * Board to display scores for game, set and match.
 */
public interface BoardService {

    /**
     * Display current score.
     *
     * @param tennisInformation
     */
    void displayScore(TennisInformation tennisInformation);

    /**
     * Display the end of each game .
     *
     * @param winner
     */
    void endGame(Player winner) ;

    /**
     * Display the end of a set .
     *
     * @param winner
     */
    void endSet(Player winner) ;

    /**
     * Display the end of a match.
     *
     * @param player
     */
    void endMatch(Player player);
}
