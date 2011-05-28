package src.assets;

import src.ui.OfferType;

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
     * method getType() -get the OfferType
     * public
     * @return - the OfferType of the Item  
     */
    public OfferType getType();
}
