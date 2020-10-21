package edu.up.game_state_project;

import android.service.autofill.AutofillService;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.*;

/**
 * @author William Cloutier
 * @author Moses Karemera
 * @author Maxwell McAtee
 */

public class GameActions {
    private ArrayList<Card> tempList;
    private boolean decksShuffled = false;
    Card temp;
    Guillotine_Game_State gameState;
    Listener list;
    TextView output;
    private boolean gameStart = false;
    private boolean shuffle0 = false;
    private boolean shuffle1 = false;
    private boolean FS0 = false;
    private boolean FS1 = false;
    private boolean noAction = false;
    private boolean scarletInPlay = false;

    //point vars
    private boolean p0Count = false;
    private boolean p1Count = false;
    private boolean p0Countess = false;
    private boolean p1Countess = false;
    private int p0PalaceGuard = 0;
    private int p1PalaceGuard = 0;


    /**
     * Basic Actions
     * @return
     */
    /**
     * External Citation
     * Date:    10/18/20
     * Problem: Wanted to manipulate array lists using less code
     * Resource:    https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
     * Solution: I used the API for Collections to manipulate arraylists
     */



    public GameActions(Guillotine_Game_State state, Listener list, TextView output){
        gameState = state;
        this.list = list;
        this.output = output;
    }

    public Guillotine_Game_State GameState(){
        return gameState;
    }

    //checks if you can start the game and starts it
    public boolean startGame(){
        this.gameStart = true;
        shuffleDecks();
        dealStartingGameCards();
        gameState.turnPhase = 0;
        this.gameStart = false;
        return true;
        //setPlayerTurn();


    }
    //lets you quit the game
    public boolean quitGame(){
        if(gameState.dayNum == 3 && gameState.nobleLine.size() == 0){
            if(gameState.p0Score > gameState.p1Score){
                list.printOutput("Player "+ gameState.playerNames[0]+ " wins with "+ gameState.p0Score + " points!", output);

            }
            else if(gameState.p0Score < gameState.p1Score){
                list.printOutput("Player "+ gameState.playerNames[1]+ " wins with "+ gameState.p1Score + " points!", output);
            }
            else{
                list.printOutput("All players tied with " +gameState.p1Score + " points!", output);
            }
            return true;
        }
        return false;
    }

    //checks if decks have already been shuffled, then shuffles if false
    public boolean shuffleDecks(){
//        if(decksShuffled){
//            return false;
//        }
//        else{
            //code to shuffle all decks
            Collections.shuffle(gameState.deckAction);
            Collections.shuffle(gameState.deckNoble);
            decksShuffled = true;
            return true;
//        }
    }
    //deals all cards needed to be dealt at start of game
    public boolean dealStartingGameCards(){
        if(gameState.dayNum == 1 && gameState.turnPhase == 0) {
            for (int i = 0; i < 5; i++) {
                dealActionCard(gameState.p0Hand);
                dealActionCard(gameState.p1Hand);
            }
            for (int k = 0; k < 12; k++) {
                dealNoble();
            }

            return true;
        }
        else{
            for (int k = 0; k < 12; k++) {
                dealNoble();
                return true;
            }
        }
        return false;
    }
    //deals action cards at end of turn
    public boolean dealActionCard(ArrayList hand){
        if(scarletInPlay && !gameState.actionCardPlayed){
            hand.add(gameState.deckAction.get(0));
            while(!gameState.nobleLine.isEmpty()){
                gameState.deckDiscard.add(gameState.nobleLine.get(0));
            }
        }
        if(this.gameStart || gameState.turnPhase == 2 || gameState.actionCardPlayed) {
            hand.add(gameState.deckAction.get(0));
            gameState.deckAction.remove(0);
            if(!gameState.actionCardPlayed && !gameState.actionCardPlayed){
                gameState.turnPhase = 0;
                return true;
            }
            else{
                return true;
            }
        }
        return false;
        //basically how ever the action cards are stored, the player will have their action
        //cards gain the first action card of the actioncard deck.
    }
    //gets the noble from noble line and gives it to person
    public boolean getNoble(ArrayList field) {
        if (gameState.turnPhase == 1 || gameState.actionCardPlayed) {


            if(shuffle0){
                if(gameState.playerTurn == 0){
                    Collections.shuffle(gameState.nobleLine);
                    shuffle0 = false;
                }

            }
            else if (shuffle1){
                if(gameState.playerTurn == 1){
                    Collections.shuffle(gameState.nobleLine);
                    shuffle1 = false;
                }
            }

            if (gameState.nobleLine.get(0).getId().equals("Clown")) {
                if (gameState.playerTurn == 0) {
                    placeClown(gameState.p0Field, gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
                else {
                    placeClown(gameState.p1Field, gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
            }

            else {
                if(this.FS0 && gameState.playerTurn == 0){
                    field.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                    if(gameState.p0Field.get(0).cardColor.equals("Purple")){
                        dealActionCard(field);
                    }
                }
                else if(this.FS1 && gameState.playerTurn == 1){
                    field.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                    if(gameState.p1Field.get(0).cardColor.equals("Purple")){
                        dealActionCard(field);
                    }

                }
                else {
                    field.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
            }

            if (gameState.nobleLine.isEmpty()) {
                endDay();
            }
            else {
                if (gameState.actionCardPlayed) {
                    return true;
                } else {
                    gameState.turnPhase++;
                    return true;
                }
            }
        }

        return false;
    }
        //same as dealAction card, just give the player the first card in the noble array

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
        if(!this.noAction) {
            if (!gameState.nobleLine.get(0).getId().equals("Judge1") || !gameState.nobleLine.get(0).getId().equals("Judge2")) {
                if (gameState.turnPhase == 0) {
                    this.temp = (Card) hand.get(loc);
                    acknowledgeCardAbility(this.temp);
                    gameState.turnPhase++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else{
            this.noAction = false;
            skipAction();
            return false;
        }
    }
    //lets user skip action card play
    public boolean skipAction(){
        if(gameState.turnPhase == 0){
            gameState.turnPhase++;

            return true;

        }

        return false;
    }

    //method to call on card ability
    //checks if the card is noble and gets the id to play ability
    //if not noble it gets id of action and plays ability

    /**
     * this can be more effective, but i did the best I could in the time I had
     * There are not that many checks for certain cards to see if they are valid
     * Some methods have errors since they require the index of the card that the user chose in onCLick
     * I believe I have a comment for each card to give a short description
     * If you have any question on the specifics of the code I will try my best to get back to you
     * I believe the majority of it isnt too complex to understand
     * https://guillotine-card-translations.github.io/cards/
     * ^^^ Use this to help you with looking at the cards
     */
    public boolean acknowledgeCardAbility(Card card){
        if(card.isNoble){
            switch(card.getId()){

                //add noble from deck to end of card line after collecting this noble
                case "Capt_Guard":
                    gameState.actionCardPlayed = true;
                    dealNoble();
                    gameState.actionCardPlayed = false;
                    break;
//
//                case "Count":
//                    //needs to go in special calculate points
//                    if(gameState.playerTurn == 0){
//                        p0Count = true;
//                    }
//                    else{
//                        p1Count = true;
//                    }
//                    break;
//
//                case "Countess":
//                    //need to go in calculate points statement
//                    if(gameState.playerTurn == 0){
//                        p0Countess = true;
//                    }
//                    else{
//                        p1Countess = true;
//                    }
//                    break;
//
//                case "Fast_Noble":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        getNoble(gameState.p0Field);
//                    }
//                    else{
//                        getNoble(gameState.p1Field);
//                    }
//                    gameState.actionCardPlayed = false;
//
//                    break;
//
//                case "General":
//                    gameState.actionCardPlayed = true;
//
//                    dealNoble();
//
//                    gameState.actionCardPlayed = false;
//
//                    break;
//
//                //user selects a card in their action card and the click
//                //gets the location and then it uses the location to discard the card
//                case "Innocent":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        discardActionCard(gameState.p0Field, loc);
//                    }
//                    else{
//                        discardActionCard(gameState.p1Field, loc);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Lady":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        dealActionCard(gameState.p0Field);
//                    }
//                    else{
//                        dealActionCard(gameState.p1Field);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Lady_Waiting":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        dealActionCard(gameState.p0Field);
//                    }
//                    else{
//                        dealActionCard(gameState.p1Field);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Lord":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        dealActionCard(gameState.p0Field);
//                    }
//                    else{
//                        dealActionCard(gameState.p1Field);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Spy":
//                    gameState.actionCardPlayed = true;
//                    //need to add method and checks
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Palace_Guard1":
//                    //ability is in calculate point method
//                    if(gameState.playerTurn == 0){
//                        p0PalaceGuard++;
//                    }
//                    else{
//                        p1PalaceGuard++;
//                    }
//                    break;
//
//                case "Palace_Guard2":
//                    //ability is in calculate point method
//                    if(gameState.playerTurn == 0){
//                        p0PalaceGuard++;
//                    }
//                    else{
//                        p1PalaceGuard++;
//                    }
//                    break;
//
//                case "Palace_Guard3":
//                    //ability is in calculate point method
//                    if(gameState.playerTurn == 0){
//                        p0PalaceGuard++;
//                    }
//                    else{
//                        p1PalaceGuard++;
//                    }
//                    break;
//
//                case "Palace_Guard4":
//                    //ability is in calculate point method
//                    if(gameState.playerTurn == 0){
//                        p0PalaceGuard++;
//                    }
//                    else{
//                        p1PalaceGuard++;
//                    }
//                    break;
//
//                case "Palace_Guard5":
//                    //ability is in calculate point method
//                    if(gameState.playerTurn == 0){
//                        p0PalaceGuard++;
//                    }
//                    else{
//                        p1PalaceGuard++;
//                    }
//                    break;
//
//                case "Rival1":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        gameState.p0Field.add(gameState.deckNoble.get(0));
//                        gameState.deckNoble.remove(0);
//                    }
//                    else{
//                        gameState.p1Field.add(gameState.deckNoble.get(0));
//                        gameState.deckNoble.remove(0);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Rival2":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        gameState.p0Field.add(gameState.deckNoble.get(0));
//                        gameState.deckNoble.remove(0);
//                    }
//                    else{
//                        gameState.p1Field.add(gameState.deckNoble.get(0));
//                        gameState.deckNoble.remove(0);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Robespierre":
//                    gameState.actionCardPlayed = true;
//                    discardRemainingNobles();
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Clown":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        placeClown(gameState.p1Field, gameState.p0Field.get(gameState.p0Field.size()-1));
//                        gameState.p0Field.remove(gameState.p0Field.size()-1);
//                    }
//                    else{
//                        placeClown(gameState.p0Field, gameState.p1Field.get(gameState.p1Field.size()-1));
//                        gameState.p1Field.remove(gameState.p1Field.size()-1);
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Tragic_Figure":
//                    //action is in calculate points method
//                    break;
//
//
            }
        }
        else{
            switch(card.getId()){
//
//                // put noble at front of line into other players field
//                case "After_You":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        getNoble(gameState.p1Field);
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("After_You")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        getNoble(gameState.p0Field);
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("After_You")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble at front of line to end of line
//                case "Bribed":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    moveNoble(0, gameState.nobleLine.size()-1);
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Bribed")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Bribed")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                case "Callous":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Callous")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Callous")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //card goes to field and is used for the pointer counter
//                case "Church_Support":
//                    gameState.actionCardPlayed = true;
//
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Church_Support")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Church_Support")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //moves one green card to a different spot in the line
//                //gets the locations from the onclick, so i cant implement this
//                case "Civic_Pride":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(gameState.nobleLine.get(loc1).cardColor.equals("Green")) {
//                        moveNoble(loc1, loc2);
//                    }
//
//
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Civic_Pride")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Civic_Pride")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //increases point values in field. Will be calculated in a points method
//                case "Civic_Support":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Civic_Support")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Civic_Support")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //lets players swap nobles (cant swap same noble)
//                //need locations that both players choose using onclick
//                case "Clerical_Error":
//                    gameState.actionCardPlayed = true;
//                    //will write method later
//
//
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Clerical_Error")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Clerical_Error")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //discard any noble in nobleline and replace it with card in Ndeck
//                case "Clothing_Swap":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    gameState.deckDiscard.add(gameState.nobleLine.get(touchloc));
//                    gameState.nobleLine.remove(touchLoc);
//                    gameState.nobleLine.add(touchloc, gameState.deckNoble.get(0));
//                    gameState.deckNoble.remove(0);
//                    gameState.actionCardPlayed = false;
//
//
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Clothing_Swap")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Clothing_Swap")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    break;
//
//                //shuffles noble line right before other player draws a noble
//                case "Confusion":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(gameState.playerTurn == 0){
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Confusion")){
//                                this.shuffle1 = true;
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Confusion")){
//                                this.shuffle0 = true;
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //lets user collect additional noble from noble line
//                case "Double_Feature1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        getNoble(gameState.p0Hand);
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Double_Feature1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        getNoble(gameState.p1Hand);
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Double_Feature1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //lets user collect additional noble from noble line
//                case "Double_Feature2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        getNoble(gameState.p0Hand);
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Double_Feature2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        getNoble(gameState.p1Hand);
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Double_Feature2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//                //discard two nobleline cards and then shuffle the nobleline deck
//                case "Escape":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0) {
//                        int rand1 = (int) (Math.random() * gameState.nobleLine.size());
//                        gameState.deckDiscard.add(gameState.nobleLine.get(rand1));
//                        gameState.nobleLine.remove(rand1);
//
//                        int rand2 = (int) (Math.random() * gameState.nobleLine.size());
//                        gameState.deckDiscard.add(gameState.nobleLine.get(rand2));
//                        gameState.nobleLine.remove(rand2);
//                        Collections.shuffle(gameState.nobleLine);
//
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Escape")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Escape")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //add 3 nobles from noble deck to noble line
//                case "Extra_Cart1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    for(int i = 0; i < 3; i++){
//                        dealNoble();
//                    }
//
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Extra_Cart1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Extra_Cart1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //add 3 nobles from noble deck to noble line
//                case "Extra_Cart2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    for(int i = 0; i < 3; i++){
//                        dealNoble();
//                    }/
//
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Extra_Cart2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Extra_Cart2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble up to 3 card positions back
//                //locs from onclick
//                case "Fainting":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(newPos - origPos > 3){
//                        gameState.nobleLine.add(newPost, gameState.nobleLine.get(origPost));
//                        gameState.nobleLine.remove(origPost);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Fainting")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Fainting")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //discard any noble in line
//                //needs onclick to select the noble
//                case "Fled":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    gameState.nobleLine.remove(cardPos);
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Fled")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Fled")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //randomly removes action card from other player
//                case "Forced_Break":
//                    gameState.actionCardPlayed = true;
//
//                    int rand = 0;
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Forced_Break")){
//                                if(!gameState.p1Hand.isEmpty()) {
//                                    rand = (int) (Math.random() * gameState.p1Hand.size());
//                                    gameState.deckDiscard.add(gameState.p1Hand.get(rand));
//                                    gameState.p1Hand.remove(rand);
//                                }
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Forced_Break")){
//                                if(!gameState.p0Hand.isEmpty()) {
//                                    rand = (int) (Math.random() * gameState.p0Hand.size());
//                                    gameState.deckDiscard.add(gameState.p0Hand.get(rand));
//                                    gameState.p0Hand.remove(rand);
//                                }
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //put card in field and draw an actioncard when you get purple noble
//                case "Foreign_Support":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Foreign_Support")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                                FS0 = true;
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Foreign_Support")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                                FS0 = true;
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //grabs the first palace guard in noble line and puts it at position 0
//                case "Forward_March":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//
//                    //going through line to find the first palace guard. Putting in discard
//                    //so then I could get the card when readding it.
//                    for(int k = 0; k < gameState.nobleLine.size(); k++){
//                        if(gameState.nobleLine.get(k).getId().contains("Palace_Guard")){
//                            gameState.deckDiscard.add(0,gameState.nobleLine.get(k));
//                            gameState.nobleLine.remove(k);
//                            gameState.nobleLine.add(0, gameState.deckDiscard.get(0));
//                            gameState.deckDiscard.remove(0);
//                            k = gameState.nobleLine.size();
//                        }
//                    }
//
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Forward_March")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Forward_March")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //card is in field and worth 2 points
//                //actiond one in points method
//                case "Fountain":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Fountain")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Fountain")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble backwards up to 2 spaces
//                case "Friend_Queen1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(newLoc - oldLoc < 3){
//                        moveNoble(oldLoc, newLoc);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Friend_Queen1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Friend_Queen1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card 2 spaces back
//                case "Friend_Queen2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(newLoc - oldLoc < 3){
//                        moveNoble(oldLoc, newLoc);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Friend_Queen2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Friend_Queen2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble up to two spaces
//                case "Idiot1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(oldLoc - newLoc < 3 && oldLoc - newLoc >=0){
//                        moveNoble(oldLoc, newLoc);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Idiot1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Idiot1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card up to 2 spaces
//                case "Idiot2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(oldLoc - newLoc < 3 && oldLoc - newLoc >=0){
//                        moveNoble(oldLoc, newLoc);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Idiot2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Idiot2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble exactly four spaces
//                //need onclick location of card they choose
//                case "Ignoble1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(oldLoc - 4 >= 0){
//                        moveNoble(oldLoc, oldLoc-4);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Ignoble1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Ignoble1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble exactly four spaces
//                //need onclick location of card they choose
//                case "Ignoble2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(oldLoc - 4 >= 0){
//                        moveNoble(oldLoc, oldLoc-4);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Ignoble2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Ignoble2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //makes all gray cards in user field worth 1 point
//                //implemented more in point method
//                case "Indifferent":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Indifferent")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Indifferent")){
//                                gameState.p0Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //other player discards two action cards
//                //uses onclick for user choice
//                case "Infighting":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Infighting")){
//                                for(int k = 0; k < 2; k ++) {
//                                    if(gameState.p1Hand.size() != 0) {
//                                        discardActionCard(gameState.p1Hand, userLoc);
//                                    }
//                                }
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Infighting")){
//                                for(int k = 0; k < 2; k ++) {
//                                    if(gameState.p0Hand.size() != 0) {
//                                        discardActionCard(gameState.p0Hand, userLoc);
//                                    }
//                                }
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //trade user hands
//                case "Info_Exchange":
//                    gameState.actionCardPlayed = true;
//                    tradeHands(gameState.p0Hand, gameState.p1Hand);
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Info_Exchange")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Info_Exchange")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move nearest blue noble to front of line
//                case "Lack_Faith":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    for(int k = 0; k < gameState.nobleLine.size(); k++){
//                        if(gameState.nobleLine.get(k).cardColor.equals("Blue")){
//                            moveNoble(k, 0);
//                        }
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Lack_Faith")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Lack_Faith")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //look at enemy player hand and discard one card
//                //cannot do as of right now
//                case "Lack_Support":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Lack_Support")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Lack_Support")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//
//                //look at top three cards of noble deck and add one to nobleLine
//                case "Late_Arrival":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Late_Arrival")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                                topThreeCards(gameState.p0Field);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Late_Arrival")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                                topThreeCards(gameState.p1Field);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //if marie is in line, move her to front
//                case "Let_Cake":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    for(int k = 0; k < gameState.nobleLine.size(); k++){
//                        if(gameState.nobleLine.get(k).id.equals("Antoinette")){
//                            moveNoble(k, 0);
//                        }
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Let_Cake")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Let_Cake")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move purple noble up to 2 spaces ahead
//                //needs onclick
//                case "Majesty":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(gameState.nobleLine.get(userLoc).cardColor.equals("Purple")){
//                        if(userLoc - newLoc < 3){
//                            moveNoble(userLoc, newLoc);
//                        }
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Majesty")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Majesty")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //put all noble cards in noble line back in noble deck, shuffle noble deck
//                //and redeal the same amount of noble cards that used to be in line
//                case "Mass_Confusion":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    int var = gameState.nobleLine.size();
//                    for(int k = 0; k < gameState.nobleLine.size(); k++){
//                        gameState.deckNoble.add(gameState.nobleLine.get(k));
//                        gameState.nobleLine.remove(k);
//                    }
//                    shuffleDecks();
//                    for(int l = 0; l < var; l++){
//                        dealNoble();
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Mass_Confusion")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Mass_Confusion")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move red card up to two spaces forward
//                case "Military_Might":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(gameState.nobleLine.get(userLoc).cardColor.equals("Red")){
//                        if(userLoc - newLoc < 3){
//                            moveNoble(userLoc, newLoc);
//                        }
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Military_Might")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Military_Might")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //if this card is in your field, then you get +1 points for all red cards you have
//                //is implemented in point method
//                case "Military_Support":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Military_Support")){
//                                gameState.p0Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Military_Support")){
//                                gameState.p1Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //randomly shuffle first 5 nobles in line
//                case "Milling1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    rearrangeFives();
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Milling1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Milling1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //randomly shuffle first five nobles in line
//                case "Milling2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    rearrangeFives();
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Milling2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Milling2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //other player places their last collected noble back in noble line
//                case "Missed":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    boolean notFound = true;
//                    int length;
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Missed")){
//                                length = gameState.p1Field.size()-1;
//                                while(notFound){
//                                    if(gameState.p1Field.get(length).isNoble){
//                                        gameState.nobleLine.add(gameState.p1Field.get(length));
//                                        gameState.p1Field.remove(length);
//                                        notFound = false;
//                                    }
//                                    length--;
//                                }
//
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Missed")){
//                                length = gameState.p0Field.size()-1
//                                while(notFound){
//                                    if(gameState.p0Field.get(length).isNoble){
//                                        gameState.nobleLine.add(gameState.p0Field.get(length));
//                                        gameState.p0Field.remove(length);
//                                        notFound = false;
//                                    }
//                                    length--;
//                                }
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //removes random noble from enemy player noble field
//                case "Missing_Heads":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Missing_Heads")){
//                                gameState.p1Field.remove((int)(Math.random()*gameState.p1Field.size()));
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Missing_Heads")){
//                                gameState.p0Field.remove((int)(Math.random()*gameState.p0Field.size()));
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //user rearranges the first four nobles however they want
//                case "Opinionated":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(gameState.nobleLine.size() > 3) {
//                        moveNoble(0, newLoc);
//                        moveNoble(1, newLoc);
//                        moveNoble(2, newLoc);
//                        moveNoble(3, newLoc);
//                    }
//                    else{
//                        for(int k = 0; k < gameState.nobleLine.size(); k++){
//                            moveNoble(k, newLoc);
//                        }
//                    }
//
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Opinionated")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Opinionated")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //get 3 action cards and skip collect noble phase
//                case "Political_Influence1":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Political_Influence1")){
//                                dealActionCard(gameState.p0Hand);
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Political_Influence1")){
//                                dealActionCard(gameState.p1Hand);
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.turnPhase++;
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //get 3 action cards and skip noble draw phase
//                case "Political_Influence2":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Political_Influence2")){
//                                dealActionCard(gameState.p0Hand);
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Political_Influence2")){
//                                dealActionCard(gameState.p1Hand);
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.turnPhase++;
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move anynoble in line to front
//                //requires on click
//                case "Public_Demand":
//                    gameState.actionCardPlayed = true;
//                    moveNoble(userLoc, 0);
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Public_Demand")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Public_Demand")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card exactly 2 spaces in line
//                //need on click listener
//                case "Pushed1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(userLoc - 2 >=0){
//                        moveNoble(userLoc, userLoc-2);
//                    }
//                    else{
//                        moveNoble(userLoc, 0);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Pushed1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Pushed1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card exactly 2 spaces in line
//                //need on click listener
//                case "Pushed2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(userLoc - 2 >=0){
//                        moveNoble(userLoc, userLoc-2);
//                    }
//                    else{
//                        moveNoble(userLoc, 0);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Pushed2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Pushed2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //put players action cards back in action deck, shuffle action deck and give each player 5 cards
//                case "Rain_Delay":
//                    gameState.actionCardPlayed = true;
//
//                    while(!gameState.p0Hand.isEmpty()){
//                        gameState.deckAction.add(gameState.p0Hand.get(0));
//                        gameState.p0Hand.remove(0);
//                    }
//                    while(!gameState.p1Hand.isEmpty()){
//                        gameState.deckAction.add(gameState.p1Hand.get(0));
//                        gameState.p1Hand.remove(0);
//                    }
//                    shuffleDecks();
//                    for(int k = 0; k < 5; k++){
//                        dealActionCard(gameState.p0Hand);
//                        dealActionCard(gameState.p1Hand);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Rain_Delay")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Rain_Delay")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //put action card in discard pile into your hand
//                //need onclick
//                case "Rat_Break":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Rat_Break")){
//                                if(!gameState.deckDiscard.get(userLoc).isNoble) {
//                                    gameState.p0Hand.add(gameState.deckDiscard.get(userLoc));
//                                    gameState.deckDiscard.remove(userLoc);
//                                }
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Rat_Break")){
//                                if(!gameState.deckDiscard.get(userLoc).isNoble) {
//                                    gameState.p1Hand.add(gameState.deckDiscard.get(userLoc));
//                                    gameState.deckDiscard.remove(userLoc);
//                                }
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //other player cant play action on their next turn
//                case "Rush_Job":
//                    gameState.actionCardPlayed = true;
//                    noAction = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Rush_Job")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Rush_Job")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //day ends after player finishes turn, discards all noble line
//                case "Scarlet":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    scarletInPlay = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Scarlet")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Scarlet")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move one noble card one spot forward
//                case "Stumble1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(userLoc != 0){
//                        moveNoble(userLoc, userLoc -1);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Stumble1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Stumble1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move one noble card one spot forward
//                case "Stumble2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(userLoc != 0){
//                        moveNoble(userLoc, userLoc -1);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Stumble2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Stumble2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
                //reverse nobleline order
                case "Long_Walk":
                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
                        break;
                    }
                    gameState.actionCardPlayed = true;
                    reverseLineOrder();
                    if(gameState.playerTurn == 0){
                        for(int i = 0; i < gameState.p0Hand.size(); i++){
                            if(gameState.p0Hand.get(i).getId().equals("Long_Walk")){
                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
                                gameState.p0Hand.remove(i);
                            }
                        }
                    }
                    else{
                        for(int i = 0; i < gameState.p1Hand.size(); i++){
                            if(gameState.p1Hand.get(i).getId().equals("Long_Walk")){
                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
                                gameState.p1Hand.remove(i);
                            }
                        }

                    }
                    gameState.actionCardPlayed = false;
                    break;
//
//                //move noble forward exactly 3 places
//                case "Better_Thing":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    if(userLoc - 3 >=0){
//                        moveNoble(userLoc, userLoc-3);
//                    }
//                    else{
//                        moveNoble(userLoc, 0);
//                    }
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Better_Thing")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Better_Thing")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//
//                //add card to other player field, it is worth -2
//                //will be done in point method
//                case "Tough_Crowd":
//
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Tough_Crowd")){
//                                gameState.p1Field.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Tough_Crowd")){
//                                gameState.p0Field.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card back one spot let user play another action
//                //i have no idea how to let the user play another action
//                case "Trip1":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    gameState.turnPhase --;
//
//                    if(userLoc != gameState.nobleLine.size()-1){
//                        moveNoble(userLoc, userLoc +1);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Trip1")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Trip1")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move noble card back one spot let user play another action
//                //i have no idea how to let the user play another action
//                case "Trip2":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//                    gameState.turnPhase --;
//
//                    if(userLoc != gameState.nobleLine.size()-1){
//                        moveNoble(userLoc, userLoc +1);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Trip2")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Trip2")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //put any action card in player field into discard
//                //need onclick user loc
//                case "Twist_Fate":
//                    gameState.actionCardPlayed = true;
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Twist_Fate")){
//                                if(!gameState.p1Field.get(userLoc).isNoble){
//                                    gameState.deckDiscard.add(gameState.p1Field.get(userLoc));
//                                    gameState.p1Field.remove(userLoc);
//                                }
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Twist_Fate")){
//                                if(!gameState.p0Field.get(userLoc).isNoble){
//                                    gameState.deckDiscard.add(gameState.p0Field.get(userLoc));
//                                    gameState.p0Field.remove(userLoc);
//                                }
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
//                //move any noble up to 3 spaces forward
//                //need onclick user loc and the new location
//                case "Was_Name":
//                    if(gameState.p1Field.contains(card.id.equals("Callous")) || gameState.p0Field.contains(card.id.equals("Callous")) ){
//                        break;
//                    }
//                    gameState.actionCardPlayed = true;
//
//                    if(userLoc - newLoc >=0){
//                        moveNoble(userLoc, newLoc);
//                    }
//                    else{
//                        moveNoble(userLoc, 0);
//                    }
//
//                    if(gameState.playerTurn == 0){
//                        for(int i = 0; i < gameState.p0Hand.size(); i++){
//                            if(gameState.p0Hand.get(i).getId().equals("Was_Name")){
//                                gameState.deckDiscard.add(gameState.p0Hand.get(i));
//                                gameState.p0Hand.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        for(int i = 0; i < gameState.p1Hand.size(); i++){
//                            if(gameState.p1Hand.get(i).getId().equals("Was_Name")){
//                                gameState.deckDiscard.add(gameState.p1Hand.get(i));
//                                gameState.p1Hand.remove(i);
//                            }
//                        }
//
//                    }
//                    gameState.actionCardPlayed = false;
//                    break;
//
            }
        }
        return false;
    }


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
            //this is commented out so that nothing extra is printed for tests
            //quitGame();
            return true;
        }
        else{
            gameState.dayNum++;
            dealStartingGameCards();
            return true;
        }
        //end the day

    }

    /**
     * Action Card Actions
     */


        //removes the desired card from enemy player and puts it in the player who called this method
        public boolean takeNoble(ArrayList taker, ArrayList victim, Card desiredcard){
            if(victim.contains(desiredcard)){
                victim.remove(desiredcard);
                taker.add(desiredcard);
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

        public boolean reverseLineOrder(){
            if(!gameState.nobleLine.isEmpty()){
                //for command that goes through the list/array and swaps the first and last posoitions, stops when one counter reaches halfway
                Collections.reverse(gameState.nobleLine);
                return true;
            }
            return false;
        }

    public boolean discardActionCard(ArrayList hand, int loc){
        if(hand.size() > loc){
            //discard  actioncard from player
            gameState.deckDiscard.add((Card) hand.get(loc));
            hand.remove(loc);
            return true;
        }
        return false;
    }


    /**
     * Noble Card Actions
     */
        public boolean dealNoble(){

                gameState.nobleLine.add(gameState.deckNoble.get(0));
                gameState.deckNoble.remove(0);

            return true;
        }




    /**
     *For this method, you must add all the noble points together. It takes in the user's field and
     * that turn number of the user
     * I would suggest adding all the noble cards that are not dependant on other cards first
     * WARNING: action cards are in this field if the action card manipulates the points, so check if the card is noble first
     * Then, I would add any noble card point that has an action of it's own.
     * I have some instance vars that will tell you important information (such as how many palace guards a user has)
     * Use those vars (you will most likely have to make your own for I think I am missing some) to find the new point values
     * I would also add a local counter for each card color. (Red, Blue, Green, Purple, and Grey)
     * Then check each card in field and count how many cards of certain colors exist
     * Use this information to find the point value of all cards that are dependent of number of certain colors
     *
     *
     */
    public boolean calculatePoints(ArrayList<Card> field, int user){
            boolean end = false;
            if(user == 0){
                for(int i = 0; i < field.size(); i++){

                    if(field.get(i).isNoble) {
                        gameState.p0Score += field.get(i).points;
                    }

                }

            }
            else{
                for(int i = 0; i < field.size(); i++){

                    if(field.get(i).isNoble) {
                        gameState.p1Score += field.get(i).points;
                    }

                }
            }

            return true;
        }




        /*All the methods below are not being demonstrated for one reason or other in part d
          Some are because they require drawing to function and others are just rehashes of what other
          methods do.
         */
        public boolean discardRemainingNobles(){
            if(!gameState.nobleLine.isEmpty()){
                //go through nobleline list and discard each one
                while(!gameState.nobleLine.isEmpty()){
                    gameState.deckDiscard.add(gameState.nobleLine.get(0));
                    gameState.nobleLine.remove(0);
                }
                endDay();
                return true;
            }
            return false;
        }

    public boolean placeClown(ArrayList reciever, Card card){

        reciever.add(card);
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
        if(gameState.turnPhase == 0) {
            tempList = p1;
            p1 = p2;
            p2 = tempList;
            tempList = null;
            return true;
        }
        return false;
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

//        public boolean topThreeCards(ArrayList user){
//            //puts 3 noble cards in user field in pos 0, 1, 2
//            //user then selects the one they want and the others are put in back in deck
//            if(!gameState.deckNoble.isEmpty()){
//                user.add(0, gameState.deckNoble.get(0));
//                user.add(1, gameState.deckNoble.get(1));
//                user.add(2, gameState.deckNoble.get(2));
//                chooseCard(user);
//                return true;
//            }
//            return false;
//        }

    //gets the card that the user clicks and keeps it.
    //idk how to implement this with onclick
//        public boolean chooseCard(ArrayList user){
//            if(userloc == 0){
//                user.remove(2);
//                user.remove(1);
//                gameState.deckDiscard.add(gameState.deckNoble.get(0));
//                gameState.deckNoble.remove(0);
//            }
//            else if( userloc == 1){
//                user.remove(2);
//                user.remove(0);
//                gameState.deckDiscard.add(gameState.deckNoble.get(1));
//                gameState.deckNoble.remove(1);
//            }
//            else{
//                user.remove(1);
//                user.remove(2);
//                gameState.deckDiscard.add(gameState.deckNoble.get(2));
//                gameState.deckNoble.remove(2);
//            }
//        }


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

    //checks if player has the card, if they do, it removes the card
    public boolean discardNoble(ArrayList hand, Card card){
        if(hand.contains(card) ){
            hand.remove(card);
            return true;
        }
        return false;
    }

    public boolean placeNoble( ArrayList playerField){
        //check if card is first noble, then check if player exists
        //add card to player's hand
        playerField.add(gameState.nobleLine.get(0));
        gameState.nobleLine.remove(0);
        //remove card from noble line
        return true;

    }
    public boolean moveNoble(int nobleCardLocation, int newLocation){
        if(gameState.nobleLine.size() > nobleCardLocation && gameState.nobleLine.size() > newLocation ) {
            gameState.deckDiscard.add(0,gameState.nobleLine.get(nobleCardLocation));
            gameState.nobleLine.remove(nobleCardLocation);
            gameState.nobleLine.add(newLocation, gameState.deckDiscard.get(0));
            gameState.deckDiscard.remove(0);
            //code to remove card from array of cards, then make everycard's location inbewteen nobleCardLocation and newLocation have their
            //location +1, then put the card in newLocation
            return true;
        }
        else{
            return false;
        }
    }
}
