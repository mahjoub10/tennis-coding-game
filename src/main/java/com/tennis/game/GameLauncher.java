package com.tennis.game;

import com.tennis.enums.RefereeDecision;
import com.tennis.player.Player;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Player service runnable.
 */
public class GameLauncher implements  Runnable{

    // CURRENT PLAYER
    private Player player  ;

    private GameService gameService;

    private Game game ;

    // USED TO SYNCHONISE WIT OTHER THREAD, SO ALL THREAD WILL START IN THE SAME TIME
    private CountDownLatch countDownLatch ;

    // COMMON TASK EXECUTOR CREATED BY THE REFEREE
    private ExecutorService executorService ;

    public GameLauncher(Player player, Game game , ExecutorService executorService , CountDownLatch countDownLatch) {
        this.player = player;
        this.countDownLatch = countDownLatch;
        this.executorService = executorService ;
        this.game = game ;
    }

    @Override
    public void run()  {

        // CREATE PLAYER WRAPPER
        GameWrapper gameWrapper = new GameWrapper(player, game, gameService);

        // LAUNCH A TRY
        RefereeDecision refereeDecision = getMatchStatus(gameWrapper);

        // WHILE THE PLAYER IS NOT A WINNER, HE CAN CONTINUE TO PLAY
        while (Objects.nonNull(refereeDecision) && refereeDecision != RefereeDecision.WIN){
            refereeDecision = getMatchStatus(gameWrapper);
        }

        // THREAD SYNCHRONIZE
        countDownLatch.countDown();
    }

    // SEND WRAPPER CALLABLE TO TASK EXECUTOR, SO THE PLAYER CAN PLAY
    private RefereeDecision getMatchStatus(GameWrapper gameWrapper) {

        try {
            Future<RefereeDecision> result = executorService.submit(gameWrapper);
            return result.get();
        } catch (Exception e) { // GENERAL EXCEPTION IS CAUGHT, BE CAUSE THE REFEREE CAN STOP THE GAME BY ENDING ALL THE CURRENT THREADS.
           // THIS IS NORMAL BEHAVIOR.
        }

        return null;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public Player getPlayer() {
        return player;
    }
}
