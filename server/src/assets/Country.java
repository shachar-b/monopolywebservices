package assets;

import javax.management.RuntimeErrorException;

/**
 * class Country extends AssetGroup
 * a Country in the monopoly game - contains only members of class City 
 * public
 * @see AssetGroup
 * @author Omer Shenhar and Shachar Butnaro
 *
 */

public class Country extends AssetGroup {

	/**
	 * method Country(String nameOfCountry)
	 *  a constructor for type Country
	 * public 
	 * @param nameOfCountry - a string sets the name of the country
	 */
	public Country(String nameOfCountry) {
		super(nameOfCountry);
	}


	/**
	 * method boolean hasHousesConstructed()
	 * public
	 * @return true if one of the contained City has houses constructed false otherwise
	 * @throws  RuntimeErrorException iff the group has been added an non city object 
	 */
	public boolean hasHousesConstructed() throws RuntimeErrorException
	{
		for (Asset city : assetsInGroup)
		{
			try {
				if (((City)city).getNumHouses()>0)
				{
					return true;
				}
			} catch (ClassCastException e) {//could only get here if the class has been handled incorrectly- could not recover
				throw new RuntimeErrorException(null,"the Contry "+ this.nameOfGroup + " has been added a non City object");
			}
		}
		return false;
	}
}
