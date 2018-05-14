package com.tennis.game;

import com.tennis.common.TennisInformation;
import com.tennis.enums.GameStatus;

public class GameInformation extends TennisInformation {

    private GameStatus gameStatus ;

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
