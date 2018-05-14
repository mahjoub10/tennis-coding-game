package com.tennis.referee;

import com.tennis.common.BoardService;
import com.tennis.common.BoardServiceImpl;
import com.tennis.common.TennisSession;
import com.tennis.decision.GameDecision;
import com.tennis.decision.GameDecisionImpl;
import com.tennis.decision.SetDecision;
import com.tennis.decision.SetDecisionImpl;
import com.tennis.game.Game;
import com.tennis.game.GameLauncher;
import com.tennis.game.GameService;
import com.tennis.game.GameServiceImpl;
import com.tennis.listener.TennisListener;
import com.tennis.player.Player;
import com.tennis.set.Set;
import com.tennis.set.SetService;
import com.tennis.set.SetServiceImpl;
import com.tennis.set.SetWrapper;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO use factory DP to create objects.
 * Basic implementation of referee actions
 */
public class RefereeService implements Referee {

    // LISTENER TO TRACE GAME STATUS
    private TennisListener listener = createListener();

    //GAME  DECISION
    private GameDecision gameDecision;

    private SetDecision setDecision;

    // DEFINE FIRST PLAYER
    private Player playerOne;

    // DEFINE SECOND PLAYER
    private Player playerTwo;

    // CREATE FIRST PLAYER
    private GameLauncher gameLauncherOne;

    // CREATE SECOND PLAYER
    private GameLauncher gameLauncherTwo;

    private ExecutorService currentExecutor = createExecutor();

    private TennisSession session;

    //CURRENT SET WRAPPER
    private SetWrapper setWrapper ;



    @Override
    public void startGame() {

        // PREPARE MATCH AND SUBSCRIBERS
        Game game = this.prepareNewGame();

        // CREATE GAME DECIDER
        gameDecision = new GameDecisionImpl(this.session, game, this);

        GameService tennisGameService = createGameService(game);

        gameLauncherOne = createPlayerService(playerOne, game);
        gameLauncherTwo = createPlayerService(playerTwo, game);

        gameLauncherOne.setGameService(tennisGameService);
        gameLauncherTwo.setGameService(tennisGameService);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(gameLauncherOne);

        executorService.execute(gameLauncherTwo);

    }

    @Override
    public void startMatch() {

        // CREATE NEW SESSION
        this.prepareNewSession();

        //LAUCH SET
        this.startSet();

    }

    @Override
    public void endGame(Game game) {

        if (Objects.nonNull(currentExecutor)) {
            // END THE MATCH
            try {
                this.currentExecutor.shutdown();
                // CREATE NEW EXECUTOR
                this.currentExecutor = createExecutor();
            } catch (Exception ex) {

            }

            this.setWrapper.play(game.getWinner());

        }
    }


    @Override
    public void endMatch(Player winner) {

        // DISPLAY THE WINNER IN THE BOARD
        this.listener.endMatch(winner);

    }


    @Override
    public void startSet() {

        Set set = this.prepareNewSet();
        setDecision = new SetDecisionImpl(this.session, this, set);

         SetService setService = createSetService(set);
         setWrapper = new SetWrapper(setService, set, this);

         this.startGame();
    }

    @Override
    public void endSet(Player winner) {

        this.setWrapper = null ;

        // IF THERE' A WINNER , THE MATCH SHOULD BE ENDED
        if(Objects.nonNull(winner)){
            this.endMatch(winner);
        }
    }


    private GameLauncher createPlayerService(Player player, Game game) {

        // PREPARE COUNT DOWN LATCH
        CountDownLatch countDownLatch = new CountDownLatch(3);

        // CREATE NEW PLAYER SERVICE.
        return new GameLauncher(player, game, currentExecutor, countDownLatch);
    }

    private GameServiceImpl createGameService(Game game) {
        // CREATE GAME SERVICE AND IT'S VOTERS
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDecision(gameDecision);
        gameService.setGame(game);
        return gameService;
    }

    private SetService createSetService(Set set) {
        SetServiceImpl setService = new SetServiceImpl();
        setService.setSetDecision(setDecision);
        setService.setSet(set);
        return setService;
    }

    private ExecutorService createExecutor() {
        // CREATE TWO , ONE FOR EACH PLAYER
        return Executors.newFixedThreadPool(2);
    }


    private void prepareNewSession() {
        session = new TennisSession();
        playerOne = this.createPlayer("Amine");
        playerTwo = this.createPlayer("Karime");
        session.subscribe(playerOne);
        session.subscribe(playerTwo);

    }

    private Game prepareNewGame() {
        return new Game(this.session, this.listener);
    }


    private Player createPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        return player;
    }

    private Set prepareNewSet() {
        return new Set(this.session, this.listener);
    }


    private TennisListener createListener() {
        BoardService boardService = new BoardServiceImpl();
        return new TennisListener(boardService);

    }
}
