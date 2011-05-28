package monopoly;

import monopoly.GameManager.jailActions;
import players.Player;
import squares.Square;
import assets.Asset;
import assets.City;
import cards.ActionCard;

/**
 * public interface IUI
 * public
 * An interface for a game's user interface
 * @author Omer Shenhar and Shachar Butnaro
 */
public interface IUI {

    /**
     * method void displayMessage(String message)
     * public
     * Displays a message to the output
     * @param message - A String depicting the message displayed
     */
    public void displayMessage(String message);

    /**
     * method void notifyPlayerLanded(Player p, Square currSQ)
     * public
     * Notifies the output where the player landed after moving.
     * @param p the valid non-null player.
     * @param currSQ the square that the player landed on.
     */
    public void notifyPlayerLanded(Player p, Square currSQ);

    /**
     * method void notifyPlayerLandsOnParkingSquare(Player player)
     * public
     * Notifies the output that the player landed on Parking.
     * @param player the valid non-null player
     */
    public void notifyPlayerLandsOnParkingSquare(Player player);

    /**
     * method void notifyPlayerLandsOnStartSquare(Player player)
     * public
     * Notifies the output that the player landed on Start.
     * @param player the valid non-null player
     */
    public void notifyPlayerLandsOnStartSquare(Player player);

    /**
     * method void notifyPlayerLandsOnJailFreePass(Player player)
     * public
     * Notifies the output that the player landed on Jail/Free Pass
     * @param player the valid non-null player
     */
    public void notifyPlayerLandsOnJailFreePass(Player player);

    /**
     * method void notifyPlayerLandsOnGoToJail(Player player)
     * public
     * Notifies the output that the player landed on GoToJail.
     * @param player the valid non-null player
     */
    public void notifyPlayerLandsOnGoToJail(Player player);

    /**
     * method void notifyPlayerBoughtHouse(Player player, City asset)
     * public
     * Notifies the output that the player bought a house in a city.
     * @param player The valid non-null player.
     * @param asset The city in which the house was bought.
     */
    public void notifyPlayerBoughtHouse(Player player, City asset);

    /**
     * method void notifyPlayerBoughtAsset(Player player, Asset asset)
     * public
     * Notifies the output that the player bought an asset
     * @param player The valid non-null player.
     * @param asset The asset bought
     */
    public void notifyPlayerBoughtAsset(Player player, Asset asset);

    /**
     * method void notifyPlayerPaysRent(Player player, Asset asset, Player owner)
     * public
     * Notifies the output that a player has to pay rent for landing on another player's asset.
     * @param player The valid non-null player that has to pay.
     * @param asset The asset that the rent is for.
     * @param owner The valid non-null player the owns the asset.
     */
    public void notifyPlayerPaysRent(Player player, Asset asset, Player owner);

    /**
     * method void notifyPlayerGotCard(Player player,ActionCard card)
     * public
     * Notifies the output that a player got a surprise/callup card, and which card.
     * @param player The valid non-null player
     * @param card The card drawn.
     */
    public void notifyPlayerGotCard(Player player, ActionCard card);

    /**
     * method void notifyPlayerCantBuy(Player player, String what, int cost)
     * public
     * Notifies the output that a player can't buy an asset or a house.
     * @param player The valid non-null player.
     * @param what A String depicting what cannot be bought
     * @param cost The cost of the transaction.
     */
    public void notifyPlayerCantBuy(Player player, String what, int cost);

    /**
     * method void notifyPlayerIsParked(Player player)
     * public
     * Notifies the output that a player is parked and cannot move.
     * @param player The valid non-null player.
     */
    public void notifyPlayerIsParked(Player player);

    /**
     * method void notifyPassStartSquare(int bonus)
     * public
     * Notifies the output that a player has passed over Start and got a bonus.
     * @param bonus The amount of the bonus received.
     */
    public void notifyPassStartSquare(int bonus);

    /**
     * method void notifyNewRound(Player p, int roundNumber, Square currSQ)
     * public
     * Notifies the output that a new turn has begun and shows relevant information.
     * @param p The valid non-null player.
     * @param roundNumber A positive integer containing the current round number.
     * @param currSQ A monopoly game square on which the player is standing.
     */
    public void notifyNewRound(Player p, int roundNumber, Square currSQ);

    /**
     * method void notifyGameWinner(Player player)
     * public
     * Notifies the output that the game has ended and who the winner is.
     * @param player A valid non-null player.
     */
    public void notifyGameWinner(Player player);

    /**
     * method void notifyDiceRoll(int LastRollOutcome1,int LastRollOutcome2)
     * public
     * Notifies the output that the die have been rolled and what the outcome was.
     * @param LastRollOutcome1 An integer from 1 to 6 containing the dice roll outcome.
     * @param LastRollOutcome1 An integer from 1 to 6 containing the dice roll outcome.
     */
    public void notifyDiceRoll(int LastRollOutcome1, int LastRollOutcome2);

    /**
     * method void notifyJailAction(Player player, jailActions action)
     * public
     * Notifies the output what happened to the player in jail
     * @param player A valid non-null player.
     * @param action The action the player took.
     */
    public void notifyJailAction(Player player, jailActions action);

    /**
     * method public void notifyPlayerLeftGame(Player p)
     * public
     * notifies the console that a player has left the game.
     * @param p a valid non-null player.
     */
    public void notifyPlayerLeftGame(Player p);
}