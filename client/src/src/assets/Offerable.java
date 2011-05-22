package src.assets;

import src.players.Player;
import ui.OfferType;

/**
 * Interface Offerable represent an Item which could be sold or bought
 * @author Omer Shenhar and Shachar Butnaro
 *
 */

public interface Offerable {

	/**
	 * method getName()
	 * public
	 * @return the name of the Item 
	 */
	public String getName();

	/**
	 * method setOwner(Player owner) -sets the given player as the owner of the Item
	 * public
	 * @param owner - a valid Player
	 * @post this.owner=owne
	 */
	public void setOwner(Player owner);

	/** 
	 * method getType() -get the OfferType
	 * public
	 * @return - the OfferType of the Item  
	 */
	public OfferType getType();

}
