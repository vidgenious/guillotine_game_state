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

    @Override
    public String toString(){
        String s = "Player Turn: " + playerTurn +"\nDay: " + dayNum + "\nP0 Score: " + p0Score +
                "\nP1 Score: " + p1Score + "\nLast First Player: " + lastFirstPlayer +
                "\nCurrent First Player: " + currFirstPlayer + "\nTurn Phase:  " + turnPhase +
                "\nP1's Hand: ";
        for(Card p :this.p1Hand){
            s = s + p.getId() + " ";
        }
        s = s + "\nP1's Field: ";
        for(Card p :this.p1Field){
            s = s + p.getId() + " ";
        }
        s = s + "\nP0's Hand: ";
        for(Card p :this.p0Hand){
            s = s + p.getId() + " ";
        }
        s = s + "\nP0's Field: ";
        for(Card p :this.p0Field){
            s = s + p.getId() + " ";
        }
        s = s + "\nNoble Line: ";
        for(Card p :this.nobleLine){
            s = s + p.getId() + " ";
        }
        s = s + "\nDiscard Pile: ";
        for(Card p :this.deckDiscard){
            s = s + p.getId() + " ";
        }
        s = s + "\nAction Card Deck: ";
        for(Card p :this.deckAction){
            s = s + p.getId() + " ";
        }
        s = s + "\nNoble Deck: ";
        for(Card p :this.deckNoble){
            s = s + p.getId() + " ";
        }
        int i = 0;
        while (playerNames[i] != null)
        {
            s = s + playerNames[i] + " ";
            i++;
        }
        return s;
    }

    public void initNobleDeck(){
        this.deckNoble.add(new Card(true, false, 4, "Archbishop"));
        this.deckNoble.add(new Card(true, false, 3, "Bad_Nun"));
        this.deckNoble.add(new Card(true, false, 3, "Baron"));
        this.deckNoble.add(new Card(true, false, 2, "Bishop"));
        this.deckNoble.add(new Card(true, true, 2, "Capt_Guard"));
        this.deckNoble.add(new Card(true, false, 5, "Cardinal"));
        this.deckNoble.add(new Card(true, false, 1, "Coiffeur"));
        this.deckNoble.add(new Card(true, false, 3, "Colonel"));
        this.deckNoble.add(new Card(true, false, 3, "Councilman"));
        this.deckNoble.add(new Card(true, true, 2, "Count"));
        this.deckNoble.add(new Card(true, true, 2, "Countess"));
        this.deckNoble.add(new Card(true, false, 3, "Duke"));
        this.deckNoble.add(new Card(true, true, 2, "Fast_Noble"));
        this.deckNoble.add(new Card(true, true, 4, "General"));
        this.deckNoble.add(new Card(true, false, 4, "Governer"));
        this.deckNoble.add(new Card(true, false, 2, "Heretic"));
        this.deckNoble.add(new Card(true, false, -3, "Hero_People"));
        this.deckNoble.add(new Card(true, true, -1, "Innocent"));
        this.deckNoble.add(new Card(true, false, 5, "King_Louis"));
        this.deckNoble.add(new Card(true, true, 2, "Lady"));
        this.deckNoble.add(new Card(true, true, 1, "Lady_Waiting"));
        this.deckNoble.add(new Card(true, false, 2, "Land_Lord"));
        this.deckNoble.add(new Card(true, false, 2, "Lieutenant1"));
        this.deckNoble.add(new Card(true, false, 2, "Lieutenant2"));
        this.deckNoble.add(new Card(true,true, 2, "Lord"));
        this.deckNoble.add(new Card(true, false, 5, "Antoinette"));
        this.deckNoble.add(new Card(true, false, -1, "Martyr1"));
        this.deckNoble.add(new Card(true, false, -1, "Martyr2"));
        this.deckNoble.add(new Card(true, false, -1, "Martyr3"));
        this.deckNoble.add(new Card(true, true, 4, "Spy"));
        this.deckNoble.add(new Card(true, false, 3, "Mayor"));
        this.deckNoble.add(new Card(true, true, 0, "Palace_Guard1"));
        this.deckNoble.add(new Card(true, true, 0, "Palace_Guard2"));
        this.deckNoble.add(new Card(true, true, 0, "Palace_Guard3"));
        this.deckNoble.add(new Card(true, true, 0, "Palace_Guard4"));
        this.deckNoble.add(new Card(true, true, 0, "Palace_Guard5"));
        this.deckNoble.add(new Card(true, false, 1, "Piss_Boy"));
        this.deckNoble.add(new Card(true, false, 4, "Regent"));
        this.deckNoble.add(new Card(true, true, 1, "Rival1"));
        this.deckNoble.add(new Card(true, true, 1, "Rival2"));
        this.deckNoble.add(new Card(true, true, 3, "Robespierre"));
        this.deckNoble.add(new Card(true, false, 1, "Cartographer"));
        this.deckNoble.add(new Card(true, false, 1, "Sheriff1"));
        this.deckNoble.add(new Card(true, false, 1, "Sheriff2"));
        this.deckNoble.add(new Card(true, false, 2, "Tax_Collector"));
        this.deckNoble.add(new Card(true, true, -2, "Clown"));
        this.deckNoble.add(new Card(true, true, 0, "Tragic_Figure"));
        this.deckNoble.add(new Card(true, true, 2, "Judge1"));
        this.deckNoble.add(new Card(true, true, 2, "Judge2"));
        this.deckNoble.add(new Card(true, false, 1, "Wealthy_Priest1"));
        this.deckNoble.add(new Card(true, false, 1, "Wealthy_Priest2"));

    }
}
