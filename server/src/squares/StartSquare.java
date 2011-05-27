package squares;

import monopoly.EventImpl;
import monopoly.GameManager;
import monopoly.Monopoly;
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
                 //3 events: first is the step on start second is the payment last is a call to end turn
                //TODO:fix my spelling :)
                String message="player "+player.getName()+" landed on start and whould recive a bouns";
                Monopoly.addEvent(EventImpl.createNewGroupB(GameManager.currentGame.getGameName(), 
                        EventImpl.EventTypes.LandedOnStartSquare, message, player.getName()));
                message="player "+player.getName() +" recived " +GameManager.START_LAND_BONUS+GameManager.MoneySign;
                Monopoly.addEvent(EventImpl.createNewPaymentEvent(GameManager.currentGame.getGameName(), 
                        EventImpl.EventTypes.Payment,message, player.getName(), true, false,
                        player.getName(), GameManager.START_LAND_BONUS));
		
		player.ChangeBalance(GameManager.START_LAND_BONUS, GameManager.ADD,true,false);
               GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
	}
}
