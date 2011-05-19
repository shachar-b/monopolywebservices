package squares;

import monopoly.GameManager;
import monopoly.GameManager.jailActions;
import players.Player;

/**
 * public class JailSlashFreePassSquare extends Square
 * @see Square
 * public
 * a Jail and FreePassSquare Square in the monopoly game 
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class JailSlashFreePassSquare extends Square {

	/* (non-Javadoc)
	 * @see squares.Square#playerArrived(players.Player)
	 */
	@Override
	public void playerArrived(Player player)
	{
		if (player.getGoOnNextTurn()==true)
		{
			GameManager.CurrentUI.notifyPlayerLandsOnJailFreePass(player);
			// nothing to do - player is on free pass
		}

	}

	/* (non-Javadoc)
	 * @see squares.Square#shouldPlayerMove(players.Player)
	 * this method returns true if the Player isn't being thrown to jail -free pass mode
	 * Return false if the player is really imprisoned.
	 */
	@Override
	public boolean shouldPlayerMove(Player player)
	{
		if (player.getGoOnNextTurn())
			return true;
		else
		{
			return false;
		}
	}

	/**
	 * method public void playerUsesGetOutOfJailCard(Player player)
	 * @param player - A valid non-null player currently in Jail.
	 * This method uses the Get Out Of Jail free card, releases the player for the next turn,
	 * And returns the used card back to the deck.
	 */
	public void playerUsesGetOutOfJailCard(Player player)
	{
		player.setGoOnNextTurn(true);
		GameManager.currentGame.getSurprise().add(player.getGetOutOfJailFreeCardPlaceHolder());
		player.setGetOutOfJailFreeCardPlaceHolder(null);
		GameManager.CurrentUI.notifyJailAction(player, jailActions.USED_CARD);
	}

	/**
	 * method public void release(Player player,boolean hasDouble)
	 * @param player A valid non null player currently in jail.
	 * @param hasDouble A boolean signifying if the player rolled a double.
	 * This method is used when the player tried rolling for a double.
	 * Releases the player in the next turn if a double was rolled.
	 */
	public void release(Player player,boolean hasDouble) {
		if(hasDouble)
		{
			player.setGoOnNextTurn(true);
			
		}
		else
		{
			player.setGoOnNextTurn(false);
		}
                GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
	}
}
