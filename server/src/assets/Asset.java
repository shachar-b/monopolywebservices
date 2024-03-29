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
           boolean willGoBankrupt = player.getBalance()<getRentPrice();
            int amountRcvd = willGoBankrupt? player.getBalance() : getRentPrice();
            this.owner.ChangeBalance(amountRcvd, GameManager.ADD, false, true);
            player.ChangeBalance(getRentPrice(), GameManager.SUBTRACT, false, true);
            if (!willGoBankrupt) //If player went bankrupt - forfeit will call endTurn.
                GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
        }
    }
    /**
     * public void buyAsset(Player player)
     * public
     * 
     * @param player - the player who want to buy the asset
     */
    public void buyAsset(Player player) {
        Monopoly.addEvent(EventImpl.createNewGroupD(GameManager.currentGame.getGameName(), EventImpl.EventTypes.AssetBoughtMessage,
                name + " has been bought", player.getName(), player.getCurrentPosition()));
        player.ChangeBalance(cost, GameManager.SUBTRACT, true, false);
        setOwner(player);
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
