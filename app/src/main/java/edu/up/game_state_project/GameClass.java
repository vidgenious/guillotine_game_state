package edu.up.game_state_project;

public class GameClass {

    //instant variables
    private int pTurn;
    private int pCurrScore;
    //TODO add more instant variables

    // constructor to init all variables
    public GameClass(int pTurn, int pCurrScore) {
        this.pCurrScore = 0;
        this.pTurn = 0;
        //TODO add more instant variables
    }

    //copy constructor
    public GameClass(GameClass origin) {
        this.pTurn = origin.pTurn;
        this.pCurrScore = origin.pCurrScore;
        //TODO add more instant variables
    }
}