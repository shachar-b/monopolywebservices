package monopoly;

import java.awt.Font;
import players.Player;

public class GameManager {

    public static long EVENT_ID = 0;
    public static final int TIMEOUT_IN_SECONDS = 300;
    public static final int MAX_NUMBER_OF_PLAYERS = 6;
    public static final int MIN_NUMBER_OF_PLAYERS = 2;
    public static final int MAX_NUMBER_OF_HOUSES = 3;
    public static final int NUMBER_OF_SQUARES = 36;
    public static final int START_LAND_BONUS = 200; //Is actually 400 with pass bonus
    public static final int START_PASS_BONUS = 200;
    public static final int START_SQ_LOCATION = 0;
    public static final int INITAL_FUNDS = 1500;
    public static final int ADD = 1;
    public static final int SUBTRACT = -1;
    public static final Player assetKeeper = null; //This is the "Kupa"
    public static final String MoneySign = "$"; //This is the currency symbol
    public static Monopoly currentGame;
    public static boolean useAutomaticDiceRoll = false;
    public static final Font DefaultFont = new Font("Serif", Font.BOLD, 10);
    private static final String resourcePath = "resources/";
    public static final String IMAGES_FOLDER = resourcePath + "images/";
    public static final String SchemaFile = resourcePath + "config/monopoly_config.xsd";
    public static final String DataFile = resourcePath + "config/monopoly_config.xml";

    /**
     * enumerated type jailActions
     * public
     * Specifies the action taken by a player in jail.
     * @author Omer Shenhar and Shachar Butnaro
     */
    public static enum jailActions {

        USED_CARD, ROLLED_DOUBLE, STAY_IN_JAIL
    }

    /**
     * enumerated type AgainstWho
     * public
     * Specifies whether a card is against other players or the treasury.
     * @author Omer Shenhar and Shachar Butnaro
     */
    public static enum AgainstWho {

        Treasury(2), OtherPlayers(1);
        private int code;

        private AgainstWho(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}