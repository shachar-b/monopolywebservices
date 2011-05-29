package src.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import src.assets.Asset;
import src.assets.City;
import src.client.GameManager;
import src.players.Player;
import src.squares.ParkingSquare;
import src.squares.Square;
import src.ui.guiComponents.CardPanel;
import src.ui.guiComponents.MainWindow;
import src.ui.guiComponents.dialogs.gameWinnerDialog;
import src.ui.guiComponents.dialogs.manualDiceRollDialog;

/**
 * public class ConsoleUI implements IUI
 * @see IUI
 * public
 * A console UI for the Monopoly game
 * @author Omer Shenhar and Shachar Butnaro
 */
public class UI {

    private MainWindow frame;

    /**
     * s constructor for the UI and start of main window
     */
    public UI() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame = new MainWindow();
                setPlayerPanel();
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });
    }

    /**
     * 
     * @return the main window
     */
    public MainWindow getFrame() {
        return frame;
    }

    /**
     * 
     * @param p- a non null player
     * @param currSQ -the square on which the player is sitting
     */
    public void notifyPlayerLanded(Player p, Square currSQ) {
        String message = "Player " + p.getName() + " landed on square "
                + (p.getCurrentPosition() + 1) + ": "
                + currSQ.getName();
        displayMessage(message);
        frame.getPlayerPanel().setSquarePanelContent(currSQ, p);
    }

    /**
     * sets the player Panel
     */
    public void setPlayerPanel() {
        frame.setPlayerPanel();
        frame.validate();
        frame.repaint();
    }

    /**
     * notifys game winner
     * @param message  a message to display
     */
    public void notifyGameWinner(String message) {
        gameWinnerDialog diag = new gameWinnerDialog(frame, true, message);
        diag.setVisible(true);
    }

    /**
     * notifys a player Passed StartSquare
     * @param playerName - a valid non null player
     */
    public void notifyPassStartSquare(String playerName) {
        displayMessage("Player " + playerName + " passed through \"GO!\" square and will recieve a reward!");
    }

    /**
     * notify Player Pays
     * @param message -the message to display
     */
    public void notifyPlayerPays(String message) {
        displayMessage(message);
    }

    /**
     * notifys a Player Bought an asset
     * @param player- a valid non null player
     * @param asset - a valid non null asset
     */
    public void notifyPlayerBoughtAsset(Player player, Asset asset) {
        String message = player.getName() + " bought " + asset.getName() + " for " + asset.getCost() + "!";
        displayMessage(message);
    }

    /**
     * notifys a Player has Bought a House
     * @param player - a valid non null player
     * @param asset - a valid non null asset
     */
    public void notifyPlayerBoughtHouse(Player player, City asset) {
        String message = player.getName() + " bought house number " + asset.getNumHouses() + " in " + asset.getName() + " for " + asset.getCostOfHouse() + "!";
        displayMessage(message);
    }

    /**
     * notifys that a Player Landed On Parking Square
     * @param player a valid non null player
     */
    public void notifyPlayerLandsOnParkingSquare(Player player) {
        String message = player.getName() + " landed on parking, and will not move on the following round.!";
        displayMessage(message);
    }

    /**
     * notifys that a Player Landed On StartSquare
     * @param playerName - the player name
     */
    public void notifyPlayerLandsOnStartSquare(String playerName) {
        String message = playerName + " has stepped on \"GO\" square and gets an additional bonus!";
        displayMessage(message);
    }

    /**
     * notify Player Lands OnJailFreePass on free pass mode
     * @param player - a valid non null player
     */
    public void notifyPlayerLandsOnJailFreePass(Player player) {
        String message = player.getName() + " is walking through the free pass.";
        displayMessage(message);
    }

    /**
     * notifys that a player was thrown to jail
     * @param player - a valid not null player
     */
    public void notifyPlayerLandsOnGoToJail(Player player) {
        String message = player.getName() + " is thrown to Jail!";
        displayMessage(message);
    }

    /**
     * writes a message to console
     * @param message - a non empty string 
     */
    public void displayMessage(String message) {
        if (frame != null) {
            frame.addLineToConsole(message);
        } else {
            ; //Do nothing, Swing thread is being lazy and hasnt started yet.
        }

    }

    /**
     * private void displaySelfClosingCardDialog(Player player, String cardMsg, boolean isSurprise)
     * This opens an un-interruptible dialog showing the card a player got
     * during the game. The dialog closes after 5 seconds.
     * @param player - A valid non-null player
     * @param cardMsg - The card message the player got.
     * @param isSurprise - a boolean signifying if the card is a surprise card.
     */
    private void displaySelfClosingCardDialog(Player player, String cardMsg, boolean isSurprise) {
        final JDialog cardDialog = new JDialog(frame, player.getName() + " got a card!", true);
        cardDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cardDialog.add(new CardPanel(isSurprise, cardMsg));
        cardDialog.pack();

        Timer timer = new Timer(5000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cardDialog.setVisible(false);
                cardDialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
        cardDialog.setVisible(true);
    }

    /**
     * notifys a player has left the game and removes him from the board
     * @param p - a valid non null player
     */
    public void notifyPlayerLeftGame(Player p) {
        String message = "player " + p.getName() + " has left the game!";
        frame.getGameboard().removePlayerIcon(p);
        displayMessage(message);
    }

    /**
     * notifyes a player has got a card
     * @param player- a non null valid player
     * @param isSurprise- true for supprise false otherwise
     * @param cardText - the card text to display
     */
    public void notifyPlayerGotCard(Player player, boolean isSurprise, String cardText) {
        displayMessage("Player " + player.getName() + " got a card: " + cardText);
        displaySelfClosingCardDialog(player, cardText, isSurprise);
    }

    /**
     * mooves the player on the painted board
     * @param p- a non null valid player 
     * @param boardSquareID- the squre id player is currently on
     * @param nextBoardSquareID - the next squre id the player moves to
     */
    public void movePlayer(Player p, Integer boardSquareID, Integer nextBoardSquareID) {
        Square newSquare = GameManager.staticInstance.getGameBoard().get(nextBoardSquareID);
        p.setCurrentPosition(nextBoardSquareID);
        notifyPlayerLanded(p, newSquare);
        frame.movePlayer(p);
        if (newSquare instanceof ParkingSquare) {
            notifyPlayerLandsOnParkingSquare(p);
        }
    }

    /**
     * opens a promt for dice dialog
     * @param eventID - the prompt event id
     */
    public void promptPlayerToChooseDice(int eventID) {
        manualDiceRollDialog diag = new manualDiceRollDialog(frame, eventID);
        diag.setVisible(true);
    }

    /**
     * notifyis that a player has used the GOJF card
     * @param value - the message to display
     */
    public void notifyPlayerUsedJailCard(String value) {
        displayMessage(value);
    }
}
