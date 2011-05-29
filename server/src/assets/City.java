package assets;

import monopoly.EventImpl;
import monopoly.GameManager;
import monopoly.Monopoly;
import players.Player;

/**
 * class City extends Asset
 * @see Asset
 * public
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class City extends Asset {

    private int rentPrice[] = new int[4]; // index 0=as is, 1=1 house, 2=2 houses, 3=3 houses
    private int numHouses;
    private int costOfHouse;

    /**
     * method City(AssetGroup group, String name, int costOfCity ,int costOfHouse, int[] rentPrices)
     * public
     * this is the constructor for a city in the monopoly game
     * @param group - a valid non null Country which this City belongs to 
     * @param name - the name of the city
     * @param costOfCity - a non negative integer. the buy cost of the City  
     * @param costOfHouse - a non negative integer. cost for each house built
     * @param rentPrices - a array of size 4 were each of its cells is initilized to a non negative integer.
     * @pre costOfCity>0 &&costOfHouse>0 && rentPrices.size()==4 &&if(i<1) !rentPrices.contains(i)
     */
    public City(AssetGroup group, String name, int costOfCity, int costOfHouse, int[] rentPrices) {
        super(group);
        this.name = name;
        this.numHouses = 0;
        this.cost = costOfCity;
        this.costOfHouse = costOfHouse;
        this.rentPrice = rentPrices.clone();
    }

    /* (non-Javadoc)
     * @see assets.Asset#getRentPrice()
     */
    @Override
    public int getRentPrice() {
        return rentPrice[numHouses];
    }

    /* (non-Javadoc)
     * @see squares.Square#playerArrived(players.Player)
     */
    @Override
    public void playerArrived(Player player) {
        super.playerArrived(player); //Check for the other options
        if (GameManager.currentGame != null) //Comes back here after game closes - if game has closed, do nothing.
        {
            if (canHouseBeBuilt(player)) {
                player.buyHouseDecision(this);
            } else if (owner == player) //the player owns this but isnt able to do anything
            {
                GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
            }
        }
    }

    /**
     * public void BuyHouse(Player player)
     * public
     * @param player - a valid non null player
     * Deducts money from player, increases number of houses in city, and fires a notifier event to the listeners.
     */
    public void BuyHouse(Player player) {
        Monopoly.addEvent(EventImpl.createNewGroupD(GameManager.currentGame.getGameName(), EventImpl.EventTypes.HouseBoughtMessage,
                "house number " + numHouses + 1 + " has been built at " + name, player.getName(), player.getCurrentPosition()));
        player.ChangeBalance(costOfHouse, GameManager.SUBTRACT, true, false);
        numHouses++;
        fireEvent("user bought house at " + this.getName()); // if anything changed notify Listeners
    }

    /**
     * public boolean canHouseBeBuilt(Player byWhom)
     * @param byWhom - A valid non null player who wants to buy a house
     * @return true IFF house can be built by player.
     */
    public boolean canHouseBeBuilt(Player byWhom) {
        if (owner == byWhom)//Owner is player
        {
            if (group.isOfSoleOwnership()) {
                if (numHouses < GameManager.MAX_NUMBER_OF_HOUSES) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method int getNumHouses()
     * public
     * @return the number of houses in the city
     */
    public int getNumHouses() {
        return numHouses;
    }

    /**
     * method int getCostOfHouse()
     * public
     * @return the cost for a single house in the city
     */
    public int getCostOfHouse() {
        return costOfHouse;
    }

    /* (non-Javadoc)
     * @see assets.Asset#setOwner(players.Player)
     */
    @Override
    public void setOwner(Player owner) {
        super.setOwner(owner);
        if (owner == GameManager.assetKeeper) //Player has been removed from game.	
        {
            numHouses = 0;
            fireEvent("keeper"); // if anything changed notify
        }
    }

    /**
     * @return a array of prices in the city where ret[i] is the price with i houses
     */
    public int[] getPrices() {
        return rentPrice.clone();
    }
}