package com.tennis;

import com.tennis.referee.Referee;
import com.tennis.referee.RefereeService;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // THE REFEREE IS THE RESPONSIBLE OF STARTING ANS ENDING THE SESSION.
        Referee referee = new RefereeService();

        //START MATCH.
        referee.startMatch();

    }
}
