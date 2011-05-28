package src.assets;

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
}
