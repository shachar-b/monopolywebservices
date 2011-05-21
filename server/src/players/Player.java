package players;
import java.util.ArrayList;

import listeners.innerChangeEventListener.InnerChangeListenableClass;
import monopoly.GameManager;
import assets.Asset;
import assets.City;
import cards.ActionCard;
import monopoly.EventImpl;
import monopoly.Monopoly;

/**
 * abstract class Player
 * @visibility public
 * A player in the monopoly game.
 * @author Omer Shenhar and Shachar Butnaro
 */
public abstract class Player extends InnerChangeListenableClass {

	protected String Name;
	protected int Balance;
	private int CurrentPosition;
        private boolean isActive;
	protected boolean GoOnNextTurn;
	protected ArrayList<Asset> assetList = new ArrayList<Asset>();
	private ActionCard getOutOfJailFreeCardPlaceHolder = null;
	private int lastKnownPosition = 0; 
	private int ParkedOnRound=0;
        private int ID;



	/**
	 * Constructor for Player
	 * @visibility public
	 * Creates a new player and sets his position and balance, and makes him active.
	 * @param name A String containing the name of the new player.
	 * @param playerIcon2 an ImagePanel holding the player's icon
	 */
	public Player(String name,int ID) {
		Name = name;
		setCurrentPosition(GameManager.START_SQ_LOCATION);
		Balance=GameManager.INITAL_FUNDS;
                isActive = true;
		GoOnNextTurn=true;
                this.ID=ID;
	}	

	/**
	 * method Boolean equals(Player other)
	 * @visibility public
	 * Checks if two player instances are identical.
	 * @param other A player to compare with.
	 * @return true IFF both players have the exact same name.
	 */
	public Boolean equals(Player other)
	{
		return Name.equals(other.getName());		
	}

	/**TODO: edit
	 * method void buyDecision(Asset asset)
	 * @visibility public
	 * Lets a player choose whether to buy an asset or not.
	 * @param asset The asset that the player can buy
	 * @return true - player chooses to buy an asset by clicking the appropriate button in the GUI.
	 */
	public void buyDecision(Asset asset)
         {
            Monopoly.addEvent(EventImpl.createNewGroupC(GameManager.currentGame.getGameName()
                    ,  EventImpl.EventTypes.PromptPlayerToBuyAsset, "buy asset "+this.getName()+"?"
                    , getName(), GameManager.TIMEOUT_IN_SECONDS, getCurrentPosition()));//Prompt player to buy asset
	
        }
	

	/**
	 * method Boolean buyHouseDecision(City asset)
	 * @visibility public
	 * Lets a player choose whether to buy a house in a city or not.
	 * @param asset The city the player is on.
	 * @return true - player chooses to buy a house by clicking the appropriate button in the GUI.
	 */
	public void buyHouseDecision(City asset)
	{
		Monopoly.addEvent(EventImpl.createNewGroupC(GameManager.currentGame.getGameName()
                    ,  EventImpl.EventTypes.PromptPlayerToBuyHouse, "buy House at "+this.getName()+"?"
                    , getName(), GameManager.TIMEOUT_IN_SECONDS, getCurrentPosition()));//Prompt player to buy asset
	}

	/**
	 * method void setName(String name)
	 * @visibility public
	 * Setter for Name.
	 * @param name A String containing the new name.
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * method String getName()
	 * @visibility public
	 * Getter for Name.
	 * @return A String containing the Player's name.
	 */
	public String getName() {
		return Name;
	}

	/**
	 * method void setGoOnNextTurn(boolean goOnNextTurn)
	 * @visibility public
	 * Setter for GoOnNextTurn.
	 * @param goOnNextTurn A boolean containing the new value for the field.
	 */
	public void setGoOnNextTurn(boolean goOnNextTurn) {
		GoOnNextTurn = goOnNextTurn;
	}

	/**
	 * method boolean getGoOnNextTurn()
	 * @visibility public
	 * Getter for GoOnNextTurn.
	 * @return A boolean containing the value of GoOnNextTurn.
	 */
	public boolean getGoOnNextTurn() {
		return GoOnNextTurn;
	}

	/**
	 * method int ChangeBalance(int amount, int sign)
	 * @visibility public
	 * Changes the player's balance.
	 * @param amount An integer containing the amount of money to be added/subtracted from player.
	 * @param sign An integer specifying the direction of the change (Add/Subtract)
	 * @return An integer containing the amount that actually moved.
	 */
	public int ChangeBalance(int amount, int sign) { //TODO : Generate and add events to eventQueue
		if(sign==GameManager.SUBTRACT)
		{
			if(amount>Balance)//Can have balance of zero - but not negative balance
			{
                                Monopoly.addEvent(EventImpl.createNewGroupB(GameManager.currentGame.getGameName(),
                                        EventImpl.EventTypes.PlayerLost, "the player "+ Name+ " is out of funds so he lost", Name));
				GameManager.currentGame.removePlayerFromGame(this);//if false remove from game
				fireEvent("removed");
				return Balance;//returns the amount of money taken
			}
			else
			{
                                //TODO;paymaent events?
				Balance-=amount;
				fireEvent("taken");
				return amount;
			}
		}
		else
		{
			Balance+=amount;
			fireEvent("added");
			return 0;
		}
	}

	/**
	 * method void setCurrentPosition(int currentPosition)
	 * @visibility public
	 * Setter for CurrentPosition
	 * @param currentPosition An integer specifying the new position of the player.
	 */
	public void setCurrentPosition(int currentPosition) {
		lastKnownPosition = this.CurrentPosition;
		CurrentPosition = currentPosition;
	}

	/**
	 * method int getLastKnownPosition()
	 * @visibility public
	 * Getter for getLastKnownPosition.
	 * @return an integer containing the last position of the player.
	 */
	public int getLastKnownPosition(){
		return lastKnownPosition;
	}

	/**
	 * method int getCurrentPosition()
	 * @visibility public
	 * Getter for CurrentPosition.
	 * @return an integer containing the current position of the player.
	 */
	public int getCurrentPosition() {
		return CurrentPosition;
	}

	/**
	 * method int getBalance()
	 * @visibility public
	 * Getter for Balance.
	 * @return An integer containing the current balance of the player.
	 */
	public int getBalance()
	{
		return Balance;
	}

	/**
	 * method void addToAssetList(Asset a)
	 * @visibility public
	 * Adds an asset to the player's asset list.
	 * @param a An asset to be added.
	 */
	public void addToAssetList(Asset a) {
		assetList.add(a);
	}

	/**
	 * method void removeFromAssetList(Asset a)
	 * @visibility public
	 * Removes an asset from the player's asset list.
	 * @param a An asset to be removed.
	 */
	public void removeFromAssetList(Asset a) {
		assetList.remove(a);
	}

	/**
	 * method ArrayList<Asset> getAssetList()
	 * @visibility public
	 * Getter for assetList.
	 * @return A list of player's assets.
	 */
	public ArrayList<Asset> getAssetList() {
		return assetList;
	}

	/**
	 * method void setGetOutOfJailFreeCardPlaceHolder(ActionCard getOutOfJailFreeCardPlaceHolder)
	 * @visibility public
	 * Setter for getOutOfJailFreeCardPlaceHolder.
	 * @param getOutOfJailFreeCardPlaceHolder A get out of jail free card reference.
	 */
	public void setGetOutOfJailFreeCardPlaceHolder(ActionCard getOutOfJailFreeCardPlaceHolder)
	{
		this.getOutOfJailFreeCardPlaceHolder = getOutOfJailFreeCardPlaceHolder;
	}

	/**
	 * method ActionCard getGetOutOfJailFreeCardPlaceHolder()
	 * @visibility public
	 * Getter for getOutOfJailFreeCardPlaceHolder.
	 * @return A get out of jail free card.
	 */
	public ActionCard getGetOutOfJailFreeCardPlaceHolder() {
		return getOutOfJailFreeCardPlaceHolder;
	}

	/**
	 * method boolean hasGetOutOfJailFreeCard()
	 * @visibility public
	 * A boolean function, checks whether the player has a get out of jail free card.
	 * @return true IFF player uses a get out of jail free card.
	 */
	public boolean hasGetOutOfJailFreeCard()
	{
		return (getOutOfJailFreeCardPlaceHolder!=null);
	}

	/**
	 * public void setParkedOnRound(int parkedOnRound)
	 * Sets the field  ParkedOnRound to the supplied value.
	 * @param parkedOnRound - 	an integer representing the round
	 *							on which the user landed on Parking Square.
	 */
	public void setParkedOnRound(int parkedOnRound) {
		ParkedOnRound = parkedOnRound;
	}

	/**
	 * public int getParkedOnRound()
	 * Returns the last round on which the player landed on Parking Square.
	 * @return an integer representing a round number.
	 */
	public int getParkedOnRound() {
		return ParkedOnRound;
	}

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * TODO : Complete documentation
     * @return the player's humanity.
     * To be overriden by HumanPlayer.
     */
    public boolean isHuman(){
        return false;
    }

    /**
     * @return the ID
     */
    public int getID() {
        System.err.println(Name + " is up "+ID);
        return ID;
       
    }


}