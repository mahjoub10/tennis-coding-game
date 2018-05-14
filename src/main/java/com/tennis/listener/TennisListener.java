package com.tennis.listener;

import com.tennis.common.BoardService;
import com.tennis.common.TennisInformation;
import com.tennis.player.Player;

/**
 * Listener to trace the steps of a match
 */
public class TennisListener {


    private BoardService boardService;

    /**
     * Custom constructor
     * @param boardService
     */
    public TennisListener(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * Display current score.
     *
     * @param tennisInformation
     */
    public void displayScore(TennisInformation tennisInformation) {
        this.boardService.displayScore(tennisInformation);
    }

    /**
     * Display the end of the game.
     *
     * @param winner
     */
    public  void endGame(Player winner) {

        this.boardService.endGame(winner);
    }

    /**
     * Display the end of the set.
     *
     * @param winner
     */
    public  void endSet(Player winner) {
        this.boardService.endSet(winner);
    }

    /**
     * Display the end of the match.
     *
     * @param winner
     */
    public  void endMatch(Player winner) {
        this.boardService.endMatch(winner);
    }
}
