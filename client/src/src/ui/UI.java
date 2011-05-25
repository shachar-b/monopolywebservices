package src.ui;

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import listeners.gameActions.GameActionEvent;
import listeners.gameActions.GameActionEventListener;
import src.assets.Asset;
import src.assets.City;
import src.client.GameManager;
import src.players.Player;
import src.squares.Square;
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
                //Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
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
        frame.movePlayer(p);
        frame.getPlayerPanel().setSquarePanelContent(currSQ, p);
    }

    /* (non-Javadoc)
     * @see ui.IUI#setPlayerPanel(players.Player, int, squares.Square)
     */
    public void setPlayerPanel(Player p) {
        frame.setPlayerPanel(p).addGameActionsListener(new GameActionEventListener() {

            @Override
            public void eventHappened(GameActionEvent gameActionEvent) {//TODO:here
                //GameManager.currentGame.eventDispatch(gameActionEvent.getMessage());
            }
        });

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
     * @see ui.IUI#notifyDiceRoll(int)
     */
    public void notifyDiceRoll(int LastRollOutcome1, int LastRollOutcome2) {
        String message = "Rolled : " + LastRollOutcome1 + " , " + LastRollOutcome2 + ".";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPassStartSquare(int)
     */
    public void notifyPassStartSquare() {
        displayMessage("player passed throu startsqure and will recive a reward!");
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerPaysRent(players.Player, assets.Asset, players.Player)
     */
    public void notifyPlayerPaysRent(Player player, Asset asset, Player owner) {
        String message = player.getName() + " has to pay rent of " + asset.getRentPrice() + " to " + owner.getName() + "!";
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
        String message = player.getName() + " will wait a turn on parking.";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyJailAction(players.Player, monopoly.GameManager.jailActions)
     */
    /*	public void notifyJailAction(Player player, jailActions action){
    String message;
    switch (action) {
    case USED_CARD:
    message = player.getName() + " used a get out of jail freeCard and is out of jail in next turn!"; 
    break;
    case ROLLED_DOUBLE:
    message = player.getName() + " has a double and is out of jail in next turn!"; 
    break;
    
    case STAY_IN_JAIL:
    message = player.getName() + " stays in jail!";
    break;
    
    default: //Shouldn't get here
    throw new RuntimeErrorException(null, "Unknown agrument for function \"notifyJailAction\"");
    }
    displayMessage(message);
    }*/

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerLandsOnStartSquare(players.Player)
     */
    public void notifyPlayerLandsOnStartSquare(Player player) {
        //String message = player.getName() + " has stepped on Start and gets and additional " + GameManager.START_LAND_BONUS  + "!";
        displayMessage("fix here");
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
     * private void displaySelfClosingCardDialog(Player player, ActionCard card)
     * This opens an un-interruptible dialog showing the card a player got
     * during the game. The dialog closes after 5 seconds.
     * @param player - A valid non-null player
     * @param card - The card the player got.
     */
    private void displaySelfClosingCardDialog(Player player/*, ActionCard card*/) {
        /*	final JDialog cardDialog = new JDialog(frame, player.getName()+" got a card!", true);
        cardDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cardDialog.add(new CardPanel(card));
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
        
         *///TODO:do somthing
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerCantBuy(players.Player, java.lang.String, int)
     */
    public void notifyPlayerCantBuy(Player player, String what, int cost) {
        String message = player.getName() + " cant buy " + what + " for " + cost + " due to insufficient funds!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyPlayerIsParked(players.Player)
     */
    public void notifyPlayerIsParked(Player player) {
        String message = player.getName() + " is parked and cannot move!";
        displayMessage(message);
    }

    /* (non-Javadoc)
     * @see ui.IUI#notifyNumOfPlayers(int, int)
     */
    public void notifyNumOfPlayers(int numOfPlayers, int numOfComputerPlayers) {
        displayMessage("Starting game with " + numOfPlayers + " players, " + numOfComputerPlayers + " are computer controlled.");
        displayMessage("Randomly setting playing order.\n");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void movePlayer(Player p, Integer boardSquareID, Integer nextBoardSquareID) {
        p.setCurrentPosition(nextBoardSquareID);
        System.err.println("move player " + p.getName() + "from " + boardSquareID + " to " + nextBoardSquareID);
        frame.movePlayer(p);
    }
}