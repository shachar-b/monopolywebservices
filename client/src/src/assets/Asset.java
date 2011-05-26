package src.assets;

import src.client.GameManager;
import src.players.Player;
import src.squares.Square;
import ui.OfferType;

/**
 * public abstract class Asset extends Square implements Offerable
 * public
 * @see Offerable
 * this class represent a Square which can be bought and sold by a player
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public abstract class Asset extends Square {

    protected Player owner;
    protected AssetGroup group;
    protected int cost;

    /**
     * method  Asset(AssetGroup group)
     * public
     * this method is a constructor to be used by classes extending Asset
     * @param group - an non null AssetGroup
     */
    public Asset(AssetGroup group) {
        super();
        owner = GameManager.assetKeeper;
        this.group = group;
        group.add(this);
    }

    /**
     * method int getCost()
     * public
     * @return the buy cost of the asset
     */
    public int getCost() {
        return cost;
    }

    /**
     * method  abstract int getRentPrice()
     * public
     * @return the current rent price of the asset
     */
    public abstract int getRentPrice();

    /* (non-Javadoc)
     * @see assets.Offerable#setOwner(players.Player)
     */
    public void setOwner(Player newOwner) {
        if (owner != GameManager.assetKeeper) {
            owner.removeFromAssetList(this);
        }
        if (newOwner != GameManager.assetKeeper) {
            newOwner.addToAssetList(this);
        }
        this.owner = newOwner;
        GameManager.currentUI.notifyPlayerBoughtAsset(newOwner, this);
        fireEvent("owner"); //notify of a change
    }

    /**
     * method Player getOwner()
     * public
     * @return the owner of the Asset
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * method AssetGroup getGroup()
     * public
     * @return the group this Asset belongs to
     */
    public AssetGroup getGroup() {
        return group;
    }

    /* (non-Javadoc)
     * @see assets.Offerable#getType()
     */
    public OfferType getType() {
        return OfferType.Assets;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getName();
    }
}
