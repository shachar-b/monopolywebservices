package assets;

import monopoly.EventImpl;
import monopoly.GameManager;
import players.Player;
import squares.Square;
import monopoly.Monopoly;


/**
 * public abstract class Asset extends Square
 * public
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
     * method Boolean isOwnedBy(Player player)
     * public
     * @param player - a player
     * @return true if the player is the owner false otherwise
     */
    public Boolean isOwnedBy(Player player) {
        return owner.equals(player);
    }

    /**
     * method int getCost()
     * public
     * @return the buy cost of the asset
     */
    public int getCost() {
        return cost;
    }

    /* (non-Javadoc)
     * @see squares.Square#playerArrived(players.Player)
     * also offers the player an option to  buy the asset if there is no owner
     * otherwise if needed it makes the player pay rent
     */
    public void playerArrived(Player player) {
        if (owner == GameManager.assetKeeper) {
           player.buyDecision(this);
            } else if (owner != player) {
//            GameManager.CurrentUI.notifyPlayerPaysRent(player, this, owner);
            int amountRcvd = player.ChangeBalance(getRentPrice(), GameManager.SUBTRACT);
            this.owner.ChangeBalance(amountRcvd, GameManager.ADD);
        }
    }

    public void buyAsset(Player player) {
                Monopoly.addEvent(EventImpl.createNewGroupD(GameManager.currentGame.getGameName(), EventImpl.EventTypes.AssetBoughtMessage,
                        name+"has been bougnt", player.getName(), player.getCurrentPosition()));
                Monopoly.addEvent(EventImpl.createNewPaymentEvent(player.getName(), EventImpl.EventTypes.Payment, "pay "+cost,
                        player.getName(), true, false, "game", cost));
        player.ChangeBalance(cost, GameManager.SUBTRACT);
        setOwner(player);
        GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
//        GameManager.CurrentUI.notifyPlayerBoughtAsset(player, this);
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getName();
    }
}
