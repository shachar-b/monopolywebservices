package src.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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

/**
 * public class ConsoleUI implements IUI
 * @see IUI
 * public
 * A console UI for the Monopoly game
 * @author Omer Shenhar and Shachar Butnaro
 */
public class UI {
    
    private MainWindow frame;
    
    public UI() {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                frame = new MainWindow();
                setPlayerPanel(GameManager.staticInstance.getPlayerByName(GameManager.clientName));
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });
    }
    
    public MainWindow getFrame() {
        return frame;
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLanded(players.Player, squares.Square)
     */
    public void notifyPlayerLanded(Player p, Square currSQ) {
        String message = "Player " + p.getName() + " landed on square "
                + (p.getCurrentPosition() + 1) + ": "
                + currSQ.getName();
        displayMessage(message);
        frame.getPlayerPanel().setSquarePanelContent(currSQ, p);
    }

    /* (non-Javadoc)
     * @see ui.IUI#setPlayerPanel(players.Player, int, squares.Square)
     */
    public void setPlayerPanel(Player p) {
        frame.setPlayerPanel(p);
        frame.validate();
        frame.repaint();
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyGameWinner(players.Player)
     */
    public void notifyGameWinner(Player player) {
        String message = "The winner is: " + player.getName() + "!!!";
        displayMessage(message);
        JOptionPane.showMessageDialog(frame, message);
        frame.clearPlayerPanelArea();
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPassStartSquare(int)
     */
    public void notifyPassStartSquare(String playerName) {
        displayMessage("Player " + playerName + " passed through \"GO!\" square and will recieve a reward!");
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerPays(players.Player, assets.Asset, players.Player)
     */
    public void notifyPlayerPays(String message) {
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerBoughtAsset(players.Player, assets.Asset)
     */
    public void notifyPlayerBoughtAsset(Player player, Asset asset) {
        String message = player.getName() + " bought " + asset.getName() + " for " + asset.getCost() + "!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerBoughtHouse(players.Player, assets.City)
     */
    public void notifyPlayerBoughtHouse(Player player, City asset) {
        String message = player.getName() + " bought house number " + asset.getNumHouses() + " in " + asset.getName() + " for " + asset.getCostOfHouse() + "!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLandsOnParkingSquare(players.Player)
     */
    public void notifyPlayerLandsOnParkingSquare(Player player) {
       String message = player.getName() + " landed on parking, and will not move on the following round.!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLandsOnStartSquare(players.Player)
     */
    public void notifyPlayerLandsOnStartSquare(String playerName) {
        String message = playerName + " has stepped on \"GO\" square and gets an additional bonus!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLandsOnJailFreePass(players.Player)
     */
    public void notifyPlayerLandsOnJailFreePass(Player player) {
        String message = player.getName() + " is walking through the free pass.";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLandsOnGoToJail(players.Player)
     */
    public void notifyPlayerLandsOnGoToJail(Player player) {
        String message = player.getName() + " is thrown to Jail!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#displayMessage(java.lang.String)
     */
    public void displayMessage(String message) {
        frame.addLineToConsole(message);
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

        //TODO:do somthing
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLeftGame(players.Player)
     */
    public void notifyPlayerLeftGame(Player p) {
        String message = "player " + p.getName() + " has left the game!";
        frame.getGameboard().removePlayerIcon(p);
        displayMessage(message);
    }
    
    public void notifyPlayerGotCard(Player player, int cardType, String cardText) {
        boolean isSurprise = (cardType == 1 ? true : false);
        displaySelfClosingCardDialog(player, cardText, isSurprise);
    }
    
    public void movePlayer(Player p, Integer boardSquareID, Integer nextBoardSquareID) {
        Square newSquare = GameManager.staticInstance.getGameBoard().get(nextBoardSquareID);
        p.setCurrentPosition(nextBoardSquareID);
        notifyPlayerLanded(p, newSquare);
        frame.movePlayer(p);
        if (newSquare instanceof ParkingSquare) {
            notifyPlayerLandsOnParkingSquare(p);
        }
    }
}
