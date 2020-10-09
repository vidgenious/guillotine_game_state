package edu.up.game_state_project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Guillotine_Game_State {
    private ArrayList<Card> p1Hand;
    private ArrayList<Card> p1Field;
    private ArrayList<Card> p0Hand;
    private ArrayList<Card> p0Field;
    private ArrayList<Card> nobleLine;
    private int dayNum;
    private int playerTurn;
    private int p0Score;
    private int p1Score;
    private int lastFirstPlayer;
    private int currFirstPlayer;
    private ArrayList<Card> deckDiscard;
    private ArrayList<Card> deckAction;
    private ArrayList<Card> deckNoble;
    private int turnPhase;
    private String[] playerNames;
}
