package squares;

import monopoly.GameManager;
import players.Player;

/**
 * public class StartSquare extends Square
 * @see Square
 * public
 * the start Square in the monopoly game 
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class StartSquare extends Square {

	/* (non-Javadoc)
	 * @see squares.Square#playerArrived(players.Player)
	 */
	@Override
	public void playerArrived(Player player) {
		GameManager.CurrentUI.notifyPlayerLandsOnStartSquare(player);
		player.ChangeBalance(GameManager.START_LAND_BONUS, GameManager.ADD);
	}
}
