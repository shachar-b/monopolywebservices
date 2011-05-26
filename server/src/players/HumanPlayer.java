/**
 * 
 */
package players;

import assets.Asset;
import assets.City;
import monopoly.EventImpl;
import monopoly.GameManager;
import monopoly.Monopoly;
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
	 */
	public HumanPlayer(String name,int ID) {
		super(name,ID);
	}

        @Override
        public boolean isHuman(){
            return true;
        }

    @Override
    public void buyDecision(Asset asset) {
       super.buyDecision(asset);
        TimeOutTasks.StartTimer(this, GameManager.TIMEOUT_IN_SECONDS);
         //TODO: really prtompt user wait for timeout and move to next state

    }



    @Override
    public void buyHouseDecision(City asset)
    {
        super.buyHouseDecision(asset);
        TimeOutTasks.StartTimer(this, GameManager.TIMEOUT_IN_SECONDS);

    }


}