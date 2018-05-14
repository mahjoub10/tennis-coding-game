package com.tennis.game;

import com.tennis.enums.GameStatus;
import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;

import java.util.concurrent.Callable;

// CALLABLE WRAPPER
public class GameWrapper implements Callable<RefereeDecision>

{
    // CURRENT PLAYER
    private Player player ;
    private Game game ;

    private GameService gameService;

    GameWrapper(Player player, Game game, GameService tennisGameService) {
        this.player = player;
        this.gameService = tennisGameService;
        this.game = game ;
    }

    public RefereeDecision call() throws Exception {
        // THE PLAYER TRY TO WIN A POINT
        GameStatus currentStatus = game.getCurrentStatus(this.player.getId());
        return gameService.winPoint(player, currentStatus);
    }
}
