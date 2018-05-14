package com.tennis.common;

import com.tennis.game.GameInformation;
import com.tennis.player.Player;
import com.tennis.set.SetInformation;

/**
 * Basic implementation of board service.
 */
public class BoardServiceImpl implements  BoardService  {

    @Override
    public void displayScore(TennisInformation tennisInformation) {

        if(tennisInformation instanceof  GameInformation){
            this.displayGameScore(GameInformation.class.cast(tennisInformation));
        }

        if(tennisInformation instanceof  SetInformation){
            this.displaySetScore(SetInformation.class.cast(tennisInformation));
        }
    }

    @Override
    public void endGame(Player winner) {
        System.out.println("######################## GAME END ########################");
        System.out.println(" the Winner is "+ winner.getName());
        System.out.println("###########################################################");
        System.out.println("\n \n \n \n");
    }

    @Override
    public void endSet(Player winner) {
        System.out.println("######################## SET END ########################");
        System.out.println(" the Winner is "+ winner.getName());
        System.out.println("###########################################################");
        System.out.println("\n \n \n \n");
    }

    @Override
    public void endMatch(Player winner) {
        System.out.println("######################## MATCH END ########################");
        System.out.println(" the Winner is "+ winner.getName());
        System.out.println("###########################################################");
        System.out.println("\n \n \n \n");
    }


    private void displayGameScore(GameInformation gameInformation){

        System.out.println("***************************** SCORE OF THE CURRENT  GAME "+Thread.currentThread().getName()+" **************************");
        System.out.println(" the player "+gameInformation.getName()+ "   has a score : "+gameInformation.getScore());
        System.out.println("************************************************************************************");
        System.out.println("\n \n \n \n");

    }


    private void displaySetScore(SetInformation setInformation) {
        System.out.println("***************************** SCORE OF THE CURRENT  SET **************************");
        System.out.println(" the player "+setInformation.getName()+ "   has a score : "+setInformation.getScore());
        System.out.println("************************************************************************************");
        System.out.println("\n \n \n \n");
    }
}
