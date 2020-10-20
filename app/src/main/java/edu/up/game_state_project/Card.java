package edu.up.game_state_project;

/**
 * This class is very bare bones and not in its final state. It only has the information
 * necessary for part d
 */
public class Card {
    //Instance variables for a card object
    public boolean isNoble;
    public boolean hasEffect;
    public int points;
    public String id;

    //Constructor for Card object
    public Card(boolean isNoble, boolean hasEffect, int points, String id){
        this.isNoble = isNoble;
        this.hasEffect = hasEffect;
        this.points = points;
        this.id = id;
    }

    public String getId(){ return this.id;}
}
