package src.ui.guiComponents.Squares;

import src.assets.Asset;
import src.client.GameManager;
import src.squares.*;

/**
 * public class SqurePanelFactory
 * a factory for squrePanels
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class SqurePanelFactory {

    /**
     * public static SquarePanel  makeCorrectSqurePanel(Square represent)
     * a factory for squrePanels
     * @param represent - a valid non null Square
     * @return a SquarePanel which fits the given type
     */
    public static SquarePanel makeCorrectSqurePanel(Square represent) {
        return makeCorrectSqurePanel(represent, true);
    }

    /**
     * public static SquarePanel  makeCorrectSqurePanel(Square represent)
     * a factory for squrePanels
     * @param represent - a valid non null Square
     * @param isHover - is valid only for assets. if it is true the outcome when clicked the offer would have an hover window with the squre info
     * 			otherwise the info would be displayed in the outcome panel
     * @return a SquarePanel which fits the given type
     */
    public static SquarePanel makeCorrectSqurePanel(Square represent, boolean isHover) {
        String SquareIconsFolder = GameManager.IMAGES_FOLDER + "/SquareIcons/";
        if ((represent instanceof Asset)) {
            return new AssetSquarePanel((Asset) represent, isHover);

        } else if ((represent instanceof StartSquare)) {
            return new ImageOnlySquarePanel(represent, SquareIconsFolder + "GO.gif");
        } else if ((represent instanceof GoToJailSquare)) {
            return new ImageOnlySquarePanel(represent, SquareIconsFolder + "GoToJail.gif");
        } else if ((represent instanceof ParkingSquare)) {
            return new ImageOnlySquarePanel(represent, SquareIconsFolder + "parking.png");
        } else if ((represent instanceof ActionCardSquare)) {
            if (((ActionCardSquare) represent).IsCallUp()) {
                return new ImageOnlySquarePanel(represent, SquareIconsFolder + "callUp.gif");

            } else//Surprise
            {
                return new ImageOnlySquarePanel(represent, SquareIconsFolder + "surprise.gif");
            }
        } else if ((represent instanceof JailSlashFreePassSquare)) {
            return new ImageOnlySquarePanel(represent, SquareIconsFolder + "jail.gif");
        } else // never happens
        {
            throw new RuntimeException("SqurePanelFactory dont know type: " + represent.getClass().getName());
        }
    }
}
