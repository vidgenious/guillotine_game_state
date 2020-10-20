package edu.up.game_state_project;


import java.util.ArrayList;

public class GameClass{

    //instant variables
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

    // constructor to init all variables
    public GameClass() {
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


    }

    //Deep copy constructor
    public GameClass(GameClass origin) {
        this.dayNum = origin.dayNum;
        this.playerTurn = origin.playerTurn;
        this.p0Score = origin.p0Score;
        this.p1Score = origin.p1Score;
        this.lastFirstPlayer = origin.lastFirstPlayer;
        this.currFirstPlayer = origin.currFirstPlayer;
        this.turnPhase = origin.turnPhase;

        this.p1Hand = new ArrayList<>();
        for(Card c : origin.p1Hand){
            this.p1Hand.add(new Card(c));
        }


        this.p0Hand = new ArrayList<>();
        for(Card c : origin.p0Hand){
            this.p0Hand.add(new Card(c));
        }


        this.p1Field = new ArrayList<>();
        for(Card c : origin.p1Field){
            this.p1Field.add(new Card(c));
        }


        this.p0Field = new ArrayList<>();
        for(Card c : origin.p0Field){
            this.p0Field.add(new Card(c));
        }


        this.nobleLine = new ArrayList<>();
        for(Card c : origin.nobleLine){
            this.nobleLine.add(new Card(c));
        }


        this.deckDiscard = new ArrayList<>();
        for(Card c : origin.deckDiscard){
            this.deckDiscard.add(new Card(c));
        }


        this.deckAction = new ArrayList<>();
        for(Card c : origin.deckAction){
            this.deckAction.add(new Card(c));
        }


        this.deckNoble = new ArrayList<>();
        for(Card c : origin.deckNoble){
            this.deckNoble.add(new Card(c));
        }


    }
}