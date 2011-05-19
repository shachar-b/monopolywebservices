package cards;

import java.util.ArrayList;

import monopoly.GameManager;
import monopoly.GameManager.AgainstWho;
import players.Player;
import squares.Square;

/**
 *  class ActionCard represent an action card in the monopoly game
 *  public
 * @author Omer Shenhar and Shachar Butnaro
 *
 */

public class ActionCard {

	public static final int CALLUP_CARD = -1;
	public static final int SURPRISE_CARD = 1;
	private int sign;
	private String action;
	private int amount;
	private AgainstWho against;
	private Class<? extends Square> goOnNext;
	private boolean CollectBonus;

	/**
	 * method ActionCard(int sign, String action, int amount, AgainstWho against,Class<? extends Square> goOnNext, boolean collectBonus)
	 * a constructor for an action card
	 * public
	 * @param sign - an int only 1 or -1 are allowed
	 * @param action - a String representing the action
	 * @param amount - an positive int the amount of money the card has to give or take
	 * @param against -an AgainstWho. represent if the action is against treasury or other players
	 * @param goOnNext - a class which extends Square -the player would be moved to next Square of that type
	 * 					if the card is a get out of jail this attribute must be set to class Square and null
	 * 					if the card doesn't move player
	 * @param collectBonus - a boolean if it is true and the card moves the player the player would receive 
	 * 						an start bonus if he will pass thru StartSquare. if it is false he would not
	 */
	public ActionCard(int sign, String action, int amount, AgainstWho against,
			Class<? extends Square> goOnNext, boolean collectBonus) {
		super();
		this.sign = sign;
		this.action = action;
		this.amount = amount;
		this.against = against;
		this.goOnNext = goOnNext; //Get Out of jail free card is characterized by goOnNext=Square (any kind of square)
		CollectBonus = collectBonus;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return action;
	}

	/**
	 * method boolean isGetOutOfJailFreeCard()
	 * public
	 * @return : true if this is a get out of jail card False otherwise
	 */
	public boolean isGetOutOfJailFreeCard()
	{
		return (goOnNext==Square.class);
	}

	/**
	 * public boolean isSurprise() 
	 * @return true IFF Card is a surprise card.
	 */
	public boolean isSurprise()
	{
		return (this.sign == SURPRISE_CARD);
	}

	/**
	 * Method doCard(Player player)- performs actions as specified in the constructor
	 * public
	 * @param player a non null initialized Player who is a member of the current game
	 */
	public void doCard(Player player)
	{
		if (goOnNext != null)
		{
			GameManager.currentGame.gotoNextSquareOfType(player, goOnNext, CollectBonus);			
		}//otherwise i assume it isn't this type of card
		if(amount!=0)
		{
			if(against == AgainstWho.OtherPlayers)
			{
				int sum = 0;
				ArrayList<Player> list = GameManager.currentGame.getGamePlayers();
				for(int currentPlayer=0;currentPlayer<list.size(); currentPlayer++)
				{
					Player other=list.get(currentPlayer);
					if(other != player)//Don't take money from yourself..
					{
						other.ChangeBalance(amount,-sign); //-sign to TAKE from players
						sum += amount;
					}
					if(!list.contains(other))
					{
						currentPlayer--;//player has been removed so next player is in his index
					}
				}
				player.ChangeBalance(sum,sign);
			}
			else { // get from Treasury
				player.ChangeBalance(amount,sign);
			}
		}
	}
}
