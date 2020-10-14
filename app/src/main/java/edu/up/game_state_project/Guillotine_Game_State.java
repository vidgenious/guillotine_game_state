package edu.up.game_state_project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Guillotine_Game_State {
    public ArrayList<Card> p1Hand;
    public ArrayList<Card> p1Field;
    public ArrayList<Card> p0Hand;
    public ArrayList<Card> p0Field;
    public ArrayList<Card> nobleLine;
    public int dayNum;
    public int playerTurn;
    public int p0Score;
    public int p1Score;
    public int lastFirstPlayer;
    public int currFirstPlayer;
    public ArrayList<Card> deckDiscard;
    public ArrayList<Card> deckAction;
    public ArrayList<Card> deckNoble;
    public int turnPhase;
    public String[] playerNames;
}
