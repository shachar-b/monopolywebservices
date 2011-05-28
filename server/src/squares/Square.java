package squares;

import listeners.innerChangeEventListener.InnerChangeListenableClass;
import players.Player;

/**
 * abstract class Square  a base class for the monopoly board squares 
 * public
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public abstract class Square extends InnerChangeListenableClass {

    protected String name = this.getClass().getSimpleName();

    /**
     * method boolean shouldPlayerMove(Player player)
     * public
     * makes a decision about player movement  
     * @param player a valid non null player
     * @return true or false depending on square rules
     */
    public boolean shouldPlayerMove(Player player) {
        return true; //To be overridden by Jail and Parking squares.
        //Also prevents the player from rolling the die (on parking).
    }

    /**
     * method abstract void playerArrived(Player player)
     * public
     * Performs the action the square has to carry out upon player landing on it
     * @param player a valid non null player 
     */
    public abstract void playerArrived(Player player);

    /**
     * method public String getName
     * public
     * a getter for Square name
     * @return the name of the current Square
     */
    public String getName() {
        return name;
    }
}