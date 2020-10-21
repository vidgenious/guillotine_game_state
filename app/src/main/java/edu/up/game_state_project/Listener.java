package edu.up.game_state_project;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * @author William Cloutier
 * @author Moses Karemera
 * @author Maxwell McAtee
 */

public class Listener implements View.OnClickListener{
    private TextView output;

    public Listener(TextView t){
        this.output = t;
    }

    @Override
    public void onClick(View view) {
        clearText(output);

        //Setup for the tests
        Guillotine_Game_State firstInstance = new Guillotine_Game_State();
        GameActions firstActions = new GameActions(firstInstance, this, output);
        Guillotine_Game_State secondInstance = new Guillotine_Game_State(firstInstance);

        //startGame calls shuffle decks, dealStartingGameCards, dealActionCard, and dealNoble
        //This chunk is for demonstrating shuffleDecks and dealStartingGameCards, DealActionCard
        //and DealNoble will be more clearly demonstrated later
        String firstString = firstInstance.toString();
        printOutput(firstString, output);
        firstActions.startGame();
        printOutput("The game is started with the starting hands being dealt and" +
                " the line being drawn from the NobleDeck, as well as the first player being selected.",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //These two are demonstrated above
        //firstActions.shuffleDecks();
        //firstActions.dealStartingGameCards();

        firstActions.skipAction();

        //Adds a noble to play field
        firstActions.getNoble(firstInstance.p0Field);
        printOutput("The top noble in line is added to player 0's play area",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //Deals a noble to the line
        firstActions.dealNoble();
        printOutput("Another noble is added to the end of the line",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        firstActions.dealActionCard(firstInstance.p0Hand);

        //ends turn
        firstActions.endTurn();
        printOutput("The turn changes from p0, to p1",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //specifically adding card to hand for playAction test
        firstInstance.p1Hand.add(new Card(false, true, 0, "actionCard","Long_Walk"));

        firstActions.playAction(firstInstance.p1Hand, firstInstance.p1Hand.size() - 1);
        printOutput("Line reversed due to the long walk",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //This is called by the above method because it is the effect of the "The Long Walk" card
        //firstActions.reverseLineOrder();


        //ends two days
        firstActions.endDay();
        firstActions.endDay();
        printOutput("Since it is called the day goes from 1 to 3",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //to get the turn to the correct player for calculating points
        firstActions.endTurn();

        firstActions.calculatePoints(firstInstance.p0Field, firstInstance.playerTurn);

        //This command empties the line and ends the day
        firstActions.discardRemainingNobles();
        printOutput("The rest of the nobles have been discarded",
                output);
        firstString = firstInstance.toString();
        printOutput(firstString, output);

        //What is printed to the TextView comes directly from the quitGame method.
        firstActions.quitGame();




        Guillotine_Game_State thirdInstance = new Guillotine_Game_State();
        Guillotine_Game_State fourthInstance = new Guillotine_Game_State(thirdInstance);

        //prints second and fourth instances
        String secondString = secondInstance.toString();
        String fourthString = fourthInstance.toString();
        printOutput(secondString, output);
        printOutput(fourthString, output);

        //Prints if the copies are the same
        if(secondString.equals(fourthString)){
            printOutput("Second Instance and Fourth Instance match!", output);
        }
    }

    public void printOutput(String newText, TextView output){
        CharSequence oldText = output.getText();
        String compText = oldText + "\n\n" + newText;
        output.setText(compText);
    }

    //Clears text from a TextView
    private void clearText(TextView t){
        t.setText("");
    }
}
