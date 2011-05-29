package src.client;

import comm.Event;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import src.assets.Asset;
import src.assets.City;
import src.players.Player;
import src.squares.Square;
import src.ui.UI;
import src.ui.guiComponents.dialogs.BuyDialog;
import src.ui.guiComponents.dice.Dice;
import src.ui.utils.ImagePanel;

/**
 *
 * @author omer
 */
public class GameManager {

    public static int clientPlayerID = -1;
    public static String clientName = "not joined";
    public static String currentJoinedGame = "not joined";
    public static final int START_SQ_LOCATION = 0;
    public static final Player assetKeeper = null;
    public static final String IMAGES_FOLDER = "src/resources/images/";
    public static final String PLAYER_ICONS_PATH = IMAGES_FOLDER + "playerIcons/";
    public static final Font DefaultFont = new Font("Serif", Font.BOLD, 10);
    public static UI currentUI = null;
    public static HashMap<String, PlayerDetails> joined = new HashMap<String, PlayerDetails>();//name detailes
    public static final TimerTask feeder = new EventFeeder();
    private ArrayList<Player> gamePlayers;
    private ArrayList<Square> gameBoard;
    public static GameManager staticInstance;
    private Timer feederTimer;

    private GameManager() {
        Collection<PlayerDetails> playerDetails = joined.values();
        XMLInitializer gameInitializer = new XMLInitializer();
        gameBoard = gameInitializer.initBoard();
        this.gamePlayers = convertPlayerDetailsToPlayers(playerDetails);
        currentUI = new UI();
        startEventFeederTask();
    }

    public static void resetAllStaticVars() {
        clientPlayerID = -1;
        clientName = "not joined";
        currentJoinedGame = "not joined";
        currentUI = null;
        joined = new HashMap<String, PlayerDetails>();
        staticInstance = null;
    }

    public static GameManager createGameManager() {
        GameManager g = new GameManager();
        staticInstance = g;
        return g;
    }

    private ArrayList<Player> convertPlayerDetailsToPlayers(Collection<PlayerDetails> list) {
        PlayerDetails[] helper = new PlayerDetails[list.size()];
        helper = list.toArray(helper);
        ArrayList<Player> result = new ArrayList<Player>();
        for (int i = 0; i < helper.length; i++) {
            result.add(new Player(helper[i].getName(), new ImagePanel(PLAYER_ICONS_PATH + (i + 1) + ".png"), helper[i].getAmount()));
        }

        return result;
    }

    public ArrayList<Square> getGameBoard() {
        return gameBoard;
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public Player getPlayerByName(String given) {
        for (Player p : gamePlayers) {
            if (p.getName().equals(given)) {
                return p;
            }
        }
        throw new RuntimeException("this is wrong- an unknown player name was given by server: " + given);
    }

    public void handelEvent(Event event) {
        final int eventID = event.getEventID();
        final int eventType = event.getEventType().intValue();
        final int eventBoardSquareID = event.getBoardSquareID();
        final int eventNextBoardSquareID = event.getNextBoardSquareID();
        Player pl = null;
        if (event.getEventType() > 2) {
            pl = getPlayerByName(event.getPlayerName().getValue());

        }
        switch (eventType) {


            /*
             * GameStart(1),GameOver(2),GameWinner(3),PlayerResigned(4),PlayerLost(5),
            PromptPlayerToRollDice (6),DiceRoll(7),Move(8),PassedStartSquare(9),LandedOnStartSquare(10),
            GoToJail(11),PromptPlayerToBuyAsset(12),PromptPlayerToBuyHouse(13),AssetBoughtMessage(14),HouseBoughtMessage(15),
            SurpriseCard(16),WarrantCard(17),GetOutOfJailCard(18),Payment(19),PlayerUsedJailCard(20);
             */
            case 1://game start - ignored -removed
                break;
            case 2://GameOver - Nothing to do here, work will be done on the following GameWinner
                break;
            case 3://GameWinner
                stopEventFeederTask();
                currentUI.notifyGameWinner(event.getEventMessage().getValue());
                break;
            case 4://PlayerResigned- do the same as 5
            case 5://PlayerLost
                currentUI.displayMessage(event.getEventMessage().getValue());
                currentUI.notifyPlayerLeftGame(pl);
                pl.remove();
                gamePlayers.remove(pl);
                break;
            case 6://PromptPlayerToRollDice
                if (pl.getName().equals(clientName) && pl.getWantsToQuit() == false) {
                    currentUI.promptPlayerToChooseDice(eventID);
                }
                break;
            case 7://dice roll
                Dice.getGameDice().makeItRoll(event.getFirstDiceResult(), event.getSecondDiceResult());
                break;
            case 8://Move
                currentUI.movePlayer(pl, eventBoardSquareID, eventNextBoardSquareID);
                break;
            case 9://PassedStartSquare
                currentUI.notifyPassStartSquare(pl.getName());
                break;
            case 10://LandedOnStartSquare
                currentUI.notifyPlayerLandsOnStartSquare(pl.getName());
                break;
            case 11://GoToJail
                currentUI.notifyPlayerLandsOnGoToJail(pl);
                break;
            case 12://PromptPlayerToBuyAsset
            case 13://PromptPlayerToBuyHouse
                if (event.getPlayerName().getValue().equals(GameManager.clientName) && pl.getWantsToQuit() == false) {
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            boolean buyAsset = (eventType==12);
                            BuyDialog diag = new BuyDialog((JFrame) currentUI.getFrame(), true, (Asset) gameBoard.get(eventBoardSquareID), buyAsset, eventID);
                            diag.setVisible(true);
                        }
                    });
                }
                break;
            case 14://AssetBoughtMessage
                ((Asset) gameBoard.get(eventBoardSquareID)).setOwner(pl);
                break;
            case 15://HouseBoughtMessage
                ((City) gameBoard.get(eventBoardSquareID)).BuyHouse(pl);
                break;
            case 16://SurpriseCard
                currentUI.notifyPlayerGotCard(pl, true, event.getEventMessage().getValue());
                break;
            case 17://WarrantCard
                currentUI.notifyPlayerGotCard(pl, false, event.getEventMessage().getValue());
                break;
            case 18://GetOutOfJailCard
                currentUI.notifyPlayerGotCard(pl, true, event.getEventMessage().getValue());
                break;
            case 19://Payment
                Player recipient;
                recipient = getPlayerByName(event.getPaymentToPlayerName().getValue());
                recipient.ChangeBalance(event.getPaymentAmount());
                currentUI.notifyPlayerPays(event.getEventMessage().getValue());
                break;
            case 20://PlayerUsedJailCard
                currentUI.notifyPlayerUsedJailCard(event.getEventMessage().getValue());
                break;
        }
    }

    private static class EventFeeder extends TimerTask {

        @Override
        public void run() {
            if (!Server.getInstance().isEventQueueEmpty()) {
                Event event = Server.getInstance().popEventFromQueue();
                while (!Server.getInstance().isEventQueueEmpty() && !(event.getGameName().getValue().equals(currentJoinedGame))) {
                    event = Server.getInstance().popEventFromQueue();
                }
                if (event.getGameName().getValue().equals(currentJoinedGame)) {
                    staticInstance.handelEvent(event);
                }
            }
        }
    }

    private void startEventFeederTask() {
        if (feederTimer != null) {
            feederTimer.cancel();
        }
        feederTimer = Server.getInstance().startPolling("EventFeeder Timer", GameManager.feeder, 0, 3);
    }

    private void stopEventFeederTask() {
        if (feederTimer != null) {
            feederTimer.cancel();
        }
    }
}
