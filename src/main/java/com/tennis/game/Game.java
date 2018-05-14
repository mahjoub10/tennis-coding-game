package com.tennis.game;

import com.tennis.common.ScoreHolder;
import com.tennis.common.TennisInformation;
import com.tennis.common.TennisSession;
import com.tennis.enums.GameStatus;
import com.tennis.listener.TennisListener;
import com.tennis.player.Player;

import java.util.Objects;

/**
 * Holds the current game.
 */
public class Game extends ScoreHolder {


    private Player winner ;

    private TennisListener listener;

    public Game(TennisSession tennisSession, TennisListener listener) {

        // INTI INFORMATION
        this.initGameInformation(tennisSession);
        this.listener = listener;
    }

    @Override
    public void updateScore(String id, Integer score) {
        scores.computeIfPresent(id, (k, v) -> {
            v.setScore(score);
            this.listener.displayScore(v);
            return v;
        });



    }

    /**
     * Update state.
     *
     * @param id
     * @param status
     */
    public  void setNewStatus(String id , GameStatus status) {
        scores.computeIfPresent(id, (k, v) -> {
            GameInformation g = GameInformation.class.cast(v);
            g.setGameStatus(status);
            return g;
        });
    }

    /**
     * Get current state.
     *
     * @param id
     * @return
     */
    public  GameStatus getCurrentStatus(String id){
        GameInformation tennisInformation = getTennisInformation(id);
        return  tennisInformation.getGameStatus();
    }


    /**
     * Get score.
     *
     * @param id
     * @return
     */
    public int getScore(String id) {
        GameInformation tennisInformation = getTennisInformation(id);
        return  tennisInformation.getScore();
    }

    @Override
    public void setWinner( Player player){
        this.winner = player ;
        this.listener.endGame(player);
    }

    /**
     * Get the winner.
     *
     * @return
     */
    public Player getWinner() {
        return winner;
    }


    private GameInformation getTennisInformation(String id) {
        TennisInformation tennisInformation = scores.getOrDefault(id, null);
        if(Objects.isNull(tennisInformation)){
            throw new  IllegalStateException(String.format("No player with id %s has been found",id));
        }
        return GameInformation.class.cast(tennisInformation);
    }


    private void initGameInformation(TennisSession tennisSession) {

        tennisSession.getPlayers().entrySet().forEach(k -> {
            GameInformation gameInformation = new GameInformation();
            gameInformation.setGameStatus(GameStatus.NORMAL);
            gameInformation.setScore(0);
            gameInformation.setIdPlayer(k.getKey());
            gameInformation.setName(k.getValue().getName());
            super.scores.put(k.getKey(), gameInformation);
        });
        ;

    }
}
