package src.ui.guiComponents.dice;

import java.awt.BorderLayout;
import java.util.Timer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.listeners.gameActions.GameActionEvent;
import src.listeners.gameActions.GameActionEventListener;
import src.listeners.gameActions.GameActionsListenableClass;
import src.client.GameManager;
import src.ui.utils.Utils;

/**
 * @author Stijn Strickx, from http://www.proglogic.com/code/java/game/rolldice.php.
 * Modified by Omer Shenhar and Shachar Butnaro.
 *
 */
public class Dice extends GameActionsListenableClass {

    private static final long serialVersionUID = 1L;
    private static Dice gameDice = new Dice();
    JLabel dice1;
    JLabel dice2;
    int dice1Outcome;
    int serverOutcome1;
    int dice2Outcome;
    int serverOutcome2;
    JLabel text;
    JPanel eastPane = new JPanel();
    JPanel westPane = new JPanel();

    /**
     * private Dice() 
     * a constructor for the dice pane of the game
     * 
     */
    private Dice() {
        dice1 = new JLabel(Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stone1.gif"));
        dice2 = new JLabel(Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stone1.gif"));
        text = new JLabel("Total: 2");
        this.setLayout(new BorderLayout());
        westPane.add(dice1);
        eastPane.add(dice2);

        this.add(westPane, BorderLayout.WEST);
        this.add(eastPane, BorderLayout.EAST);
        this.add(text, BorderLayout.CENTER);
    }

    /**
     * public void makeItRoll(int serverOutcome1, int serverOutcome2)
     * starts the dice roll outcome
     * @param serverOutcome1 the dice outcome to set in die1
     * @param serverOutcome2 the dice outcom to set in die2
     */
    public void makeItRoll(int serverOutcome1, int serverOutcome2) {
        this.serverOutcome1 = serverOutcome1;
        this.serverOutcome2 = serverOutcome2;
        Timer timer = new Timer();
        GameActionEventListener performWhenDone = new GameActionEventListener() {

            @Override
            public void eventHappened(GameActionEvent innerChangeEvet) {
                done();
            }
        };
        timer.scheduleAtFixedRate(new ThrowDice(dice1, dice2, text, performWhenDone), 0, 100);
    }

    /**
     * private  void  done()
     * notifies all listing classes that the roll is done
     */
    private void done() {
        setDieOutcome(serverOutcome1, serverOutcome2);
        fireEvent("throwDie");
    }

    /**
     * public void resetDiceListeners() 
     * Dumps all listeners
     * 
     */
    public void resetDiceListeners() {
        removeAllListeners();//this is done to avoid dead listeners
    }

    /**
     * public static Dice getGameDice()
     * a getter for the gameDice
     * @return the singleton game dice
     */
    public static Dice getGameDice() {
        return gameDice;
    }

    /**	
     * public int[] getDieOutcome()
     * @return an array of the die outcomes
     */
    public int[] getDieOutcome() {
        int[] results = {dice1Outcome, dice2Outcome};
        return results;
    }

    /**
     * void setDieOutcome(int dice1Roll, int dice2Roll)
     * sets the die outcome
     * @param dice1Roll - an int
     * @param dice2Roll- an int
     */
    void setDieOutcome(int dice1Roll, int dice2Roll) {
        dice1Outcome = dice1Roll;
        dice2Outcome = dice2Roll;
        Icon icon1;
        Icon icon2;
        if (dice1Roll < 1 || dice1Roll > 6) {
            icon1 = Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stoneUnknown.gif");
        } else {
            icon1 = Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stone" + (dice1Outcome) + ".gif");
        }
        if (dice2Roll < 1 || dice2Roll > 6) {
            icon2 = Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stoneUnknown.gif");
        } else {
            icon2 = Utils.getImageIcon(GameManager.IMAGES_FOLDER + "dice/" + "stone" + (dice2Outcome) + ".gif");
        }
        dice1.setIcon(icon1);
        dice1.revalidate();
        dice1.repaint();
        dice2.setIcon(icon2);
        dice2.revalidate();
        dice2.repaint();
        text.setText("Total: " + (dice1Outcome + dice2Outcome));
    }
}