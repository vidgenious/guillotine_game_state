package edu.up.game_state_project;

public class Card {
    //Instance variables for a card object
    public boolean isNoble;
    public boolean hasEffect;
    public int points;
    public int id;

    //Constructor for Card object
    public Card(boolean isNoble, boolean hadEffect, int points, int id){
        this.isNoble = isNoble;
        this.hasEffect = hadEffect;
        this.points = points;
        this.id = id;
    }
}
