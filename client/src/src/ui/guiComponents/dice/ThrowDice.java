package src.ui.guiComponents.dice;

import java.util.Random;
import java.util.TimerTask;
import javax.swing.JLabel;
import src.listeners.gameActions.GameActionEvent;
import src.listeners.gameActions.GameActionEventListener;

/**
 * @author Stijn Strickx, from http://www.proglogic.com/code/java/game/rolldice.php.
 * Modified by Omer Shenhar and Shachar Butnaro.
 *
 */
class ThrowDice extends TimerTask {

    private Random rg = new Random();
    private int count;
    int dice1Outcome;
    int dice2Outcome;
    GameActionEventListener preformWhenDone;
    public final static int NUM_OF_THROWS = 20;

    /**
     * public ThrowDice(JLabel dice1, JLabel dice2, JLabel text,GameActionEventListener preformWhenDone) 
     * @param dice1 - a JLabel which contains the first dice icon
     * @param dice2 - a JLabel which contains the second dice icon
     * @param text - the text box for the sum of the throw
     * @param preformWhenDone- an valid non null GameActionEventListener to run when die roll is finished
     */
    public ThrowDice(JLabel dice1, JLabel dice2, JLabel text, GameActionEventListener preformWhenDone) {
        count = NUM_OF_THROWS;
        this.preformWhenDone = preformWhenDone;
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     * runs the dice roll
     */
    public void run() {
        if (count > 0) {
            count--;
            dice1Outcome = rg.nextInt(6) + 1;
            dice2Outcome = rg.nextInt(6) + 1;
            Dice.getGameDice().setDieOutcome(dice1Outcome, dice2Outcome);

        } else {
            Dice.getGameDice().setDieOutcome(dice1Outcome, dice2Outcome);
            preformWhenDone.eventHappened(new GameActionEvent(this, "throwDie"));
            this.cancel();
        }
    }
}
