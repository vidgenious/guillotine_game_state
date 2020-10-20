package edu.up.game_state_project;

import java.util.*;
public class GameActions {
    private ArrayList<Card> tempList;
    private boolean decksShuffled = false;
    Card temp;
    Guillotine_Game_State gameState;

    /**
     * Basic Actions
     * @return
     * https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
     * ^^source for arraylist shuffle
     * https://www.geeksforgeeks.org/collections-reverse-java-examples/
     * ^^reverse
     */

    public GameActions(){
        gameState = new Guillotine_Game_State();
    }

    //checks if you can start the game and starts it
    public boolean startGame(){
        shuffleDecks();
        dealStartingGameCards();
        return true;
      //  setPlayerTurn();


    }
    //lets you quit the game
    public boolean quitGame(){
        if(gameState.dayNum == 3 && gameState.nobleLine.size() == 0){
            if(gameState.p0Score > gameState.p1Score){
                System.out.println("Player "+ gameState.playerNames[0]+ " wins with "+ gameState.p0Score + " points!");

            }
            else if(gameState.p0Score < gameState.p1Score){
                System.out.println("Player "+ gameState.playerNames[1]+ " wins with "+ gameState.p1Score + " points!");
            }
            else{
                System.out.println("All players tied with " +gameState.p1Score + " points!");
            }
            return true;
        }
        return false;
    }

    //checks if decks have already been shuffled, then shuffles if false
    public boolean shuffleDecks(){
        if(decksShuffled){
            return false;
        }
        else{
            //code to shuffle all decks
            Collections.shuffle(gameState.deckAction);
            Collections.shuffle(gameState.deckNoble);
            decksShuffled = true;
            return true;
        }
    }
    //deals all cards needed to be dealt at start of game
    public boolean dealStartingGameCards(){
        //if it is first turn of first day
        if(gameState.dayNum == 1 && gameState.turnPhase == 1){
                for(int i = 0; i < 5; i++) {
                    dealActionCard(gameState.p0Hand);
                    dealActionCard(gameState.p1Hand);
                    dealNoble();
                }

            return true;
        }
        else{
            dealNoble();
            return true;

        }



    }
    //deals action cards at end of turn
    public boolean dealActionCard(ArrayList hand){
            hand.add(gameState.deckAction.get(0));
            gameState.deckAction.remove(0);
            return true;
        //basically how ever the action cards are stored, the player will have their action
        //cards gain the first action card of the actioncard deck.
    }
    //gets the noble from noble line and gives it to person
    public boolean getNoble(ArrayList field){
        field.add(gameState.nobleLine.get(0));
        gameState.nobleLine.remove(0);
        return true;
        //same as dealAction card, just give the player the first card in the noble array
    }
    //enlarges card user picks
    //no idea how to implement this
    //not needed for now
    /**
    public boolean enlargeCard(){
        //no idea
        return false;
    }
     */
    //lets user play actioncard
    public boolean playAction(ArrayList hand, int loc){
        this.temp = (Card) hand.get(loc);
        //acknowledgeCardAbility(this.temp);
        return true;
    }
    //lets user skip action card play
    public boolean skipAction(){
        if(gameState.turnPhase == 0){
            gameState.turnPhase++;
            return true;

        }

        return false;
    }
    //sees what the card is and it's ability then plays it
    //not needed for now
    /**
    public boolean acknowledgeCardAbility(Card card){
        return false;
    }
     */

    //ends player turn
    public boolean endTurn(){
        if(gameState.playerTurn == 0){
            gameState.playerTurn++;
            return true;
        }
        else{
            gameState.playerTurn--;
            return true;
        }
    }

    //ends day when no nobles exist
    public boolean endDay(){
        if(gameState.dayNum == 3){
            quitGame();
            return true;
        }
        else{
            gameState.dayNum++;
            dealNoble();
            return true;
        }
        //end the day

    }

    /**
     * Action Card Actions
     */
        public boolean placeNoble( ArrayList playerField){
            //check if card is first noble, then check if player exists
             //add card to player's hand
            playerField.add(gameState.nobleLine.get(0));
            gameState.nobleLine.remove(0);
                //remove card from noble line
                return true;

        }
        public boolean moveNoble(int nobleCardLocation, int newLocation){
            tempList.add(gameState.nobleLine.get(nobleCardLocation));
            gameState.nobleLine.set(nobleCardLocation, gameState.nobleLine.get(newLocation));
            gameState.nobleLine.set(newLocation, tempList.get(0));
            tempList.remove(0);

            //code to remove card from array of cards, then make everycard's location inbewteen nobleCardLocation and newLocation have their
            //location +1, then put the card in newLocation
            return true;
        }

        //removes the desired card from enemy player and puts it in the player who called this method
        public boolean takeNoble(ArrayList taker, ArrayList victim, Card desiredcard){
            if(victim.contains(desiredcard)){
                victim.remove(desiredcard);
                taker.add(desiredcard);
                return true;
            }
            return false;
        }

        //checks if player has the card, if they do, it removes the card
        public boolean discardNoble(ArrayList hand, Card card){
            if(hand.contains(card) ){
                hand.remove(card);
                return true;
            }
            return false;
        }
        //basically just draws a card from noble deck and puts it in the players hand
        //cant be fully implemented now
    /**
        public boolean drawNoble(ArrayList playerField){
            playerField.add(gameState.deckNoble.get(0));
            gameState.deckNoble.remove(0);
            return true;
        }
    */
        public boolean rearrangeLine(){
            Collections.shuffle(gameState.nobleLine);
            return true;
        }
        public boolean rearrangeFives(){
            if(gameState.nobleLine.size() > 5){
               //again idk what system we are using so finding the first 5 cards will be different for lists or arrays
                for(int i = 0; i < 5; i++) {
                    tempList.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
                Collections.shuffle(tempList);
                for(int k = 0; k < 5; k++){
                    gameState.nobleLine.add(tempList.get(0));
                    tempList.remove(0);
                }
                return true;
            }
            return false;
        }
        public boolean tradeHands(ArrayList p1, ArrayList p2){
            tempList = p1;
            p1 = p2;
            p2 = tempList;
            tempList = null;
            return true;
        }

        //requires drawing, so this will not be functional as is
    /**
        public boolean chooseCard(ArrayList Phand, ArrayList enemyhand, Card card){
            if(enemyhand.contains(card)){
                //lets user see enemy hand, chosen card then gets added to user's hand and removed from enemy hand
                enemyhand.remove(card);
                Phand.add(card);
                return true;
            }
            return false;
        }
*/

            //cant do right now
    /**
        public boolean topThreeCards(ArrayList user){
            if(!gameState.deckNoble.isEmpty()){
                //grab top three cards of noble deck, enlarge one card ,let user switch between the cards
                //then if user picks one, remove that one from list, delarge cards, and give that card to user
                //noidea
                return true;
            }
            return false;
        }
     */
        public boolean rearrangeFirstFour(){
            if(gameState.nobleLine.size() > 5){
                //again idk what system we are using so finding the first 5 cards will be different for lists or arrays
                for(int i = 0; i < 4; i++) {
                    tempList.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
                Collections.shuffle(tempList);
                for(int k = 0; k < 4; k++){
                    gameState.nobleLine.add(tempList.get(0));
                    tempList.remove(0);
                }
                return true;
            }
            return false;
        }
        //cant do right now
    /**
        public boolean takeDiscardCard(ArrayList user){
            if(!gameState.deckDiscard.isEmpty()){
                //enlarge one card, let user switch inebtween which one is enlarged and they can click a button to get that card
                //noidea
                return true;
            }
            return false;
        }
     */
        public boolean reverseLineOrder(){
            if(!gameState.nobleLine.isEmpty()){
                //for command that goes through the list/array and swaps the first and last posoitions, stops when one counter reaches halfway
                Collections.reverse(gameState.nobleLine);
                return true;
            }
            return false;
        }
    /**
     * Noble Card Actions
     */
        public boolean dealNoble(){
            for(int i = 0; i < 12; i++){
                gameState.nobleLine.add(gameState.deckNoble.get(0));
                gameState.deckNoble.remove(0);
            }
            return true;
            //idk what this one does
        }
        public boolean discardActionCard(ArrayList hand){
            if(!hand.isEmpty()){
                //discard random actioncard from player
                hand.remove((Math.random()+hand.size()));
                return true;
            }
            return false;
        }
        public boolean discardRemainingNobles(){
            if(!gameState.nobleLine.isEmpty()){
                //go through nobleline list and discard each one
                for(int i = 0; i < gameState.nobleLine.size(); i++){
                    gameState.nobleLine.remove(0);
                }
                return true;
            }
            return false;
        }
        //cant do rn

        public boolean placeClown(ArrayList reciever){
            for(int i = 0; i < gameState.deckNoble.size()) {
                if(gameState.deckNoble.get(i).getId().equals("Clown")) {
                    reciever.add(gameState.deckNoble.get(i));
                    return true;
                }
            }
            return false;

        }

}
