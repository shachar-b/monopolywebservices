package players;

import assets.Asset;
import assets.City;
import monopoly.TimeOutTasks;

/**
 * class HumanPlayer extends Player
 * @see Player
 * Marker class
 * A human player in the Monopoly game.
 * @author Omer Shenhar and Shachar Butnaro
 */
public class HumanPlayer extends Player {

    /**
     * Constructor for human player.
     * Gets a name from the UI and transfers it the the super constructor.
     * @param name his name
     * @param ID - his ID
     */
    public HumanPlayer(String name, int ID) {
        super(name, ID);
    }

    /**
     * @return  true
     */
    @Override
    public boolean isHuman() {
        return true;
    }
    /**
     * starts a timer and prompts user
     * @param asset - the aset for sale
     */
    @Override
    public void buyDecision(Asset asset) {
        super.buyDecision(asset);
        TimeOutTasks.StartTimer(this);
    }
/**
     * starts a timer and prompts user
     * @param asset - the City with houses to be built
     */
    @Override
    public void buyHouseDecision(City asset) {
        super.buyHouseDecision(asset);
        TimeOutTasks.StartTimer(this);

    }
}