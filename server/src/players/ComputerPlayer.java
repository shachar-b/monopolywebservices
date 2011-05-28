package players;

import monopoly.GameManager;
import assets.Asset;
import assets.City;

/**
 * class ComputerPlayer extends Player
 * @see Player
 * @visibility public
 * A computer player in the Monopoly game.
 * @author Omer Shenhar and Shachar Butnaro
 */
public class ComputerPlayer extends Player {

    private static final int BUY_THRESHHOLD = 300;// the minimal amount of funds to leave in balance when buying something

    /**
     * Constructor for computer player.
     * Sends to super constructor.
     */
    public ComputerPlayer(String name, int ID) {
        super(name, ID);
    }

    /* (non-Javadoc)
     * @see players.Player#buyDecision(java.lang.String, assets.Asset, int)
     * If computer will be left with BUY_THRESHHOLD or more after buying - chooses to buy.
     */
    @Override
    public void buyDecision(Asset asset) {

        super.buyDecision(asset);
        if (Balance - asset.getCost() >= BUY_THRESHHOLD) {
            GameManager.currentGame.eventDispatch(getID(), "buyAsset");
        } else {
            GameManager.currentGame.eventDispatch(getID(), "endTurn");
        }
    }

    /* (non-Javadoc)
     * @see players.Player#buyHouseDecision(assets.City)
     * Same buying algorithm as an asset.
     */
    @Override
    public void buyHouseDecision(City asset) {

        super.buyHouseDecision(asset);
        if (Balance - asset.getCostOfHouse() >= BUY_THRESHHOLD) {
            GameManager.currentGame.eventDispatch(getID(), "buyHouse");
        } else {
            GameManager.currentGame.eventDispatch(getID(), "endTurn");
        }
    }
}
