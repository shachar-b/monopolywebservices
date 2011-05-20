/**
 * 
 */
package squares;

import monopoly.GameManager;
import players.Player;
import cards.ActionCard;
import cards.ShaffledDeck;
import monopoly.EventImpl;
import monopoly.Monopoly;

/**
 * public class ActionCardSquare extends Square
 * @see Square
 * public
 * a Call up or Surprise Square in the monopoly game 
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class ActionCardSquare extends Square {

	int sign;

	/**
	 * method public ActionCardSquare(int sign1)
	 * public
	 * a constructor for ActionCardSquare
	 * @param sign1 - either -1 for call up or 1 for surprise
	 */
	public ActionCardSquare(int sign1)
	{
		sign=sign1;
	}


	/* (non-Javadoc)
	 * @see squares.Square#getName()
	 */
	@Override
	public String getName() {return sign==1?"Surprise":"CallUp";}

	/* (non-Javadoc)
	 * @see squares.Square#playerArrived(players.Player)
	 * gives the player a card from the correct deck and makes an action according to it
	 */
	@Override
	public void playerArrived(Player player) {
            ShaffledDeck currDeck=(sign==1?GameManager.currentGame.getSurprise():GameManager.currentGame.getCallUp());
		ActionCard currCard = currDeck.takeCard();
                
               
		if (currCard.isGetOutOfJailFreeCard())
                {
                    Monopoly.addEvent(EventImpl.createNewGroupB(GameManager.currentGame.getGameName(),
                            EventImpl.EventTypes.GetOutOfJailCard, "the player "+player.getName()+" got a get out of jail free card", player.getName()));
                    player.setGetOutOfJailFreeCardPlaceHolder(currCard);
                    GameManager.currentGame.eventDispatch(player.getID(), "endTurn");
                }
		else
                {
                    EventImpl.EventTypes type=(currCard.isSurprise())? EventImpl.EventTypes.SurpriseCard:EventImpl.EventTypes.WarrantCard;
                    String message= "player "+player.getName()+ " got the card "+currCard;
                    Monopoly.addEvent(EventImpl.createNewGroupB(GameManager.currentGame.getGameName(), 
                            type, "player", player.getName()));
                    currCard.doCard(player);
                }
	}

	/**
	 * @return true if it is call up false if it is surprise
	 */
	public boolean IsCallUp()
	{
		return sign==-1;

	}
}
