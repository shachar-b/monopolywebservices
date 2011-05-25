package src.players;
import java.util.ArrayList;

import listeners.innerChangeEventListener.InnerChangeListenableClass;
import ui.utils.ImagePanel;
import src.assets.Asset;
import src.assets.Offerable;
import src.client.GameManager;

/**
 * abstract class Player
 * @visibility public
 * A player in the monopoly game.
 * @author Omer Shenhar and Shachar Butnaro
 */
public class Player extends InnerChangeListenableClass {

	protected String Name;
	protected int Balance;
	private int CurrentPosition;
	protected boolean GoOnNextTurn;
	protected ArrayList<Asset> assetList = new ArrayList<Asset>();
	private int lastKnownPosition = 0; 
	ImagePanel playerIcon = null;
	private int ParkedOnRound=0;

	/**
	 * Constructor for Player
	 * @visibility public
	 * Creates a new player and sets his position and balance, and makes him active.
	 * @param name A String containing the name of the new player.
	 * @param playerIcon2 an ImagePanel holding the player's icon
	 */
	public Player(String name,ImagePanel playerIcon2,int initalFunds) {
		Name = name;
		setCurrentPosition(GameManager.START_SQ_LOCATION);
		Balance=initalFunds;
		GoOnNextTurn=true;
		this.playerIcon = playerIcon2;
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
	 * method String getIconPanel()
	 * @visibility public
	 * Getter for playerIcon.
	 * @return An Icon representing the player.
	 */
	public ImagePanel getIconPanel(){
		return playerIcon;
	}
        
        public void remove()
        {
            for(Asset asset:assetList)
            {
                removeFromAssetList(asset);
            }
            fireEvent("removed");
            //TODO:stuff
        }

	
	
	/**
	 * method void ChangeBalance(int amount, int sign)
	 * @visibility public
	 * Changes the player's balance.
	 * @param amount An integer containing the amount of money to be added/subtracted from player.
	 * @param sign An integer specifying the direction of the change (Add/Subtract)
	 * 
	 */
	public void ChangeBalance(int amount, int sign) {
            
		if(sign==GameManager.SUBTRACT)
		{
			Balance-=amount;
			fireEvent("taken");
				
		}
		else
		{
			Balance+=amount;
			fireEvent("added");
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
	 * method ArrayList<Offerable> getAssetGroups()
	 * @visibility public
	 * Returns a list of all groups that the player holds.
	 * @return a list containing all groups that the player holds.
	 */
	public ArrayList<Offerable> getGroups(){
		ArrayList<Offerable> result = new ArrayList<Offerable>();
		for (Asset current : assetList)
		{
			if (!result.contains(current.getGroup()))
				if (current.getGroup().isOfSoleOwnership())
					result.add(current.getGroup());
		}
		return result;
	}


	
}