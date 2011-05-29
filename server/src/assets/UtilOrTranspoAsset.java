package assets;

import javax.management.RuntimeErrorException;

import listeners.innerChangeEventListener.InnerChangeEventListner;
import listeners.innerChangeEventListener.InnerChangeEvet;
import monopoly.GameManager;
import players.Player;

/**
 * class UtilOrTranspoAsset extends Asset
 * @see Asset
 * public 
 * Represents a utility or transport square in the monopoly game
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class UtilOrTranspoAsset extends Asset {

    private int basicRental;

    @Override
    public void playerArrived(Player player) {
        super.playerArrived(player);
        if (owner == player)//nothing to do here
        {
            GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
        }
    }

    /**
     * method UtilOrTranspoAsset(AssetGroup group, String name, int cost, int basic)
     * public 
     * this is a constructor for class UtilOrTranspoAsset
     * @param group - a valid  non null UtilOrTranspoAssetGroup
     * @param name - the name of the group
     * @param cost - a non negative integer. the buy cost for the asset
     * @param basic - the basic rent for this Asset
     */
    public UtilOrTranspoAsset(AssetGroup group, String name, int cost, int basic) {
        super(group);
        this.name = name;
        basicRental = basic;
        this.cost = cost;
        group.addInnerChangeEventListner(new InnerChangeEventListner() {

            @Override
            public void eventHappened(InnerChangeEvet innerChangeEvet) {
                fireEvent("update");
            }
        });
    }
    /**
     *  public int getBasicRent()
     * public
     * 
     * @return the basic rent price for this group
     */
    public int getBasicRent() {
        return basicRental;
    }

    /* (non-Javadoc)
     * @see assets.Asset#getRentPrice()
     */
    @Override
    public int getRentPrice() {
        if (group.isOfSoleOwnership()) {
            try {
                return ((UtilOrTranspoAssetGroup) group).getFullRental();
            } catch (ClassCastException e) {//could only get here if the class has been handled incorrectly- could not recover
                throw new RuntimeErrorException(null, "The utility " + this.name + " has been added to a non Utility object");
            }
        } else {
            return basicRental;
        }
    }

    /**
     * 
     */
    protected void update() {
        this.fireEvent("an item of this group has changed owner update all");
    }
}
