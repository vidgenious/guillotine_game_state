package edu.up.game_state_project;

import android.graphics.Canvas;

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
    public boolean actionCardPlayed;

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

    // constructor to init all variables
    public Guillotine_Game_State() {
        this.dayNum = 0;
        this.playerTurn = 0;
        this.p0Score = 0;
        this.p1Score = 0;
        this.lastFirstPlayer = 0;
        this.currFirstPlayer = 0;
        this.turnPhase = 0;
        this.playerNames = new String[]{};
        this.p1Hand = new ArrayList<Card>();
        this.p0Hand = new ArrayList<Card>();
        this.p1Field = new ArrayList<Card>();
        this.p0Field = new ArrayList<Card>();
        this.nobleLine = new ArrayList<Card>();
        this.deckDiscard = new ArrayList<Card>();
        this.deckAction = new ArrayList<Card>();
        this.deckNoble = new ArrayList<Card>();
        this.actionCardPlayed = false;


    }

    //Deep copy constructor
    public Guillotine_Game_State(Guillotine_Game_State origin) {
        this.dayNum = origin.dayNum;
        this.playerTurn = origin.playerTurn;
        this.p0Score = origin.p0Score;
        this.p1Score = origin.p1Score;
        this.lastFirstPlayer = origin.lastFirstPlayer;
        this.currFirstPlayer = origin.currFirstPlayer;
        this.turnPhase = origin.turnPhase;

        this.p1Hand = new ArrayList<Card>();
        for(Card c : origin.p1Hand){
            this.p1Hand.add(c);
        }


        this.p0Hand = new ArrayList<Card>();
        for(Card c : origin.p0Hand){
            this.p0Hand.add(c);
        }


        this.p1Field = new ArrayList<Card>();
        for(Card c : origin.p1Field){
            this.p1Field.add(c);
        }


        this.p0Field = new ArrayList<>();
        for(Card c : origin.p0Field){
            this.p0Field.add(c);
        }

        this.nobleLine = new ArrayList<>();
        for(Card c : origin.nobleLine){
            this.nobleLine.add(c);
        }


        this.deckDiscard = new ArrayList<>();
        for(Card c : origin.deckDiscard){
            this.deckDiscard.add(c);
        }


        this.deckAction = new ArrayList<>();
        for(Card c : origin.deckAction){
            this.deckAction.add(c);
        }


        this.deckNoble = new ArrayList<>();
        for(Card c : origin.deckNoble){
            this.deckNoble.add(c);
        }


    }
}
