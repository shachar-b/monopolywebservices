/**
 * 
 */
package assets;

import listeners.innerChangeEventListener.InnerChangeEventListner;
import listeners.innerChangeEventListener.InnerChangeEvet;

/**
 * class UtilOrTranspoAssetGroup extends AssetGroup
 * a Utility Or Transport asset Group in the monopoly game - contains only members of class UtilOrTranspoAsset 
 * public
 * @see AssetGroup
 * @author Omer Shenhar and Shachar Butnaro
 *
 */

public class UtilOrTranspoAssetGroup extends AssetGroup {



	int fullRental;
	/** 
	 * method UtilOrTranspoAssetGroup(String nameOfGroup, int priceForEntireGroup)
	 * public
	 * this is the constructor of class UtilOrTranspoAssetGroup 
	 * @param nameOfGroup
	 * @param priceForEntireGroup : A positive integer which depicts the rental price for
	 *								a square in the group when whole of group is held by the same player.
	 */
	public UtilOrTranspoAssetGroup(String nameOfGroup, int priceForEntireGroup) {
		super(nameOfGroup);
		fullRental=priceForEntireGroup;
	}


	//collection functions
	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Asset asset) {
		asset.addInnerChangeEventListner(new InnerChangeEventListner() {

			@Override
			public void eventHappened(InnerChangeEvet innerChangeEvet) {
				if(innerChangeEvet.getMessage().equals("owner"))
					fireEvent("group");
			}
		});
		return assetsInGroup.add(asset);
	}


	/**
	 * method int getFullRental()
	 * public
	 * @return the full rental price for a single asset in this group if it is of sole ownership
	 */
	public int getFullRental() {
		return fullRental;
	}

}
