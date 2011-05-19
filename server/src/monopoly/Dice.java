package monopoly;

import java.util.Random;

/**
 * class Dice
 * @visibility public
 * this represent the dice in the monopoly game
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class Dice {

    private static Random generator;
    private static final int NUM_OF_DIE = 2;

    /**
     * method void initGenerator()
     * @visibility public
     * this method initializes the dice generator
     */
    public static void initGenerator() {
        generator = new Random();
    }

    /**
     * method int rollDice()
     * @visibility public
     * rolls the dice and returns the outcome
     * @return a int from 1 to 6 representing a single dice toss
     */
    public static int[] rollDie() {
        int[] outcomes = new int[NUM_OF_DIE];
        for (int i = 0; i < NUM_OF_DIE; i++) {
            outcomes[i] = generator.nextInt(6) + 1;
        }
        String message = "Rolled: " + outcomes[0] + "," + outcomes[1] + ".";
        //TODO : fix player name
        String playerName = "momo";//GameManager.currentGame.getCurrentActivePlayer().getName();
        EventImpl.EventTypes typeCode = EventImpl.EventTypes.DiceRoll;
        Monopoly.addEvent(EventImpl.createNewDiceRollEvent(GameManager.currentGame.getGameName(), typeCode, message, playerName, outcomes[0], outcomes[1]));
        return outcomes;
    }
}
