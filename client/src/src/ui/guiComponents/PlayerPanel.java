package src.ui.guiComponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import listeners.gameActions.GameActionEvent;
import listeners.gameActions.GameActionEventListener;
import listeners.gameActions.GameActionsListenableClass;
import src.assets.Asset;
import src.assets.Offerable;
import src.client.GameManager;
import src.players.Player;
import src.squares.Square;
import src.ui.guiComponents.Squares.SqurePanelFactory;

import src.ui.guiComponents.dice.Dice;

/**
 * public class PlayerPanel extends GameActionsListenableClass
 * A panel holding the current data of the active player.
 * @author Omer Shenhar and Shachar Butnaro
 */
public class PlayerPanel extends GameActionsListenableClass {
	private static final long serialVersionUID = 1L;
	private Player representedPlayer;
	private JPanel buttonPane;
	private JButton Forfeit;
	private JLabel nameLabel;
	private JPanel DicePane;
	private JButton useJailFreeCard;
	private JPanel CurrentSquare;
	private JButton showGroup;
	private JButton buyAsset;
	private JButton buyHouse;
	private JPanel PlayerInformation;
	private JScrollPane scrollPane1;
	private JTree PlayerHoldings;
	private GameActionEventListener dieListner= new GameActionEventListener() {

		@Override
		public void eventHappened(GameActionEvent gameActionEvent) {
			dieRolled();
		}
	};

	/**
	 * private void dieRolled()
	 * Fires the event that the die have been thrown, and sets buttons accordingly.
	 */
	private void dieRolled()
	{
		useJailFreeCard.setEnabled(false);
		fireEvent("throwDie");
	}

	/**
	 * Constructor.
	 * @param player A valid non-null player.
	 */
	public PlayerPanel(Player player)
	{
		representedPlayer=player;
		initComponents();
		Dice.getGameDice().addGameActionsListener(dieListner);
		nameLabel.setText(player.getName());
                //TODO:stuff
		Square currentSquare =GameManager.staticInstance.getGameBoard().get(player.getCurrentPosition());
		setSquarePanelContent(currentSquare,player);
		initTreeModel();
	}

	/**
	 * public void setGetOutOfJailButtonStatus(boolean value)
	 * Setter for the button status (enabled/disabled)
	 * @param value a boolean defining the status of the button.
	 */
	public void setGetOutOfJailButtonStatus(boolean value)
	{//value==true -> enable button, otherwise -> disable button.
		useJailFreeCard.setEnabled(value);
	}

	/**
	 * public void ClickGetOutOfJailButton()
	 * invokes doClick() for the Get Out Of Jail button.
	 */
	public void ClickGetOutOfJailButton()
	{
		useJailFreeCard.doClick();
	}

	/**
	 * public void setBuyAssetButtonStatus(boolean value)
	 * Setter for the button status (enabled/disabled)
	 * @param value a boolean defining the status of the button.
	 */
	public void setBuyAssetButtonStatus(boolean value)
	{//value==true -> enable button, otherwise -> disable button.
		buyAsset.setEnabled(value);
	}

	/**
	 * public void ClickBuyAssetButton()
	 * invokes doClick() for the Buy Asset button.
	 */
	public void ClickBuyAssetButton()
	{
		buyAsset.doClick();
	}

	/**
	 * public void setShowGroupButtonStatus(boolean value)
	 * Setter for the button status (enabled/disabled)
	 * @param value a boolean defining the status of the button.
	 */
	public void setShowGroupButtonStatus(boolean value)
	{//value==true -> enable button, otherwise -> disable button.
		showGroup.setEnabled(value);
	}

	/**
	 * public void setBuyHouseButtonStatus(boolean value)
	 * Setter for the button status (enabled/disabled)
	 * @param value a boolean defining the status of the button.
	 */
	public void setBuyHouseButtonStatus(boolean value)
	{//value==true -> enable button, otherwise -> disable button.
		buyHouse.setEnabled(value);
	}

	/**
	 * public void ClickBuyHouseButton()
	 * invokes doClick() for the Buy Asset button.
	 */
public void ClickBuyHouseButton()
	{
		buyHouse.doClick();
	}

	/**
	 * public void setSquarePanelContent(Square currentSquare, Player player)
	 * Sets the content of the center square, which holds information
	 * and action options regarding a gameboard square.
	 * @param currentSquare - The Square being represented.
	 * @param player A valid non-null player on that Square.
	 */
	public void setSquarePanelContent(Square currentSquare, Player player)
	{
		if (CurrentSquare.getComponentCount()>3)
			CurrentSquare.remove(0);
		CurrentSquare.add(SqurePanelFactory.makeCorrectSqurePanel(currentSquare,false),0);
		if(currentSquare instanceof Asset)
		{
			showGroup.setEnabled(true);
		}
		initTreeModel();
	}

	/**
	 * private void initTreeModel()
	 * Inserts the current player's information in the information tree of the panel.
	 */
	private void initTreeModel()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(representedPlayer.getName()+"     ");

		DefaultMutableTreeNode moneyNode = new DefaultMutableTreeNode("Money");
		moneyNode.add(new DefaultMutableTreeNode("Balance = "+representedPlayer.getBalance()));

		DefaultMutableTreeNode assetsNode = new DefaultMutableTreeNode("Assets");
		ArrayList<Asset> assetsList = representedPlayer.getAssetList();
		for (Asset asset : assetsList)
			assetsNode.add(new DefaultMutableTreeNode(asset.getName()));

		DefaultMutableTreeNode groupsNode = new DefaultMutableTreeNode("Groups");
		ArrayList<Offerable> groupsList = representedPlayer.getGroups();
		for (Offerable group : groupsList)
			groupsNode.add(new DefaultMutableTreeNode(group.getName()));

		DefaultTreeModel playerModel = new DefaultTreeModel(root);
		playerModel.insertNodeInto(moneyNode, root, playerModel.getChildCount(root));
		playerModel.insertNodeInto(assetsNode,root, playerModel.getChildCount(root));
		playerModel.insertNodeInto(groupsNode,root, playerModel.getChildCount(root));

		PlayerHoldings.setModel(playerModel);
	}

	/**
	 * private void ForfeitActionPerformed(ActionEvent e)
	 * Fires a forfeit event to remove a player from the game.
	 * @param e The ActionEvent
	 */
	private void ForfeitActionPerformed(ActionEvent e) {
		Dice.getGameDice().resetDiceListeners();
		fireEvent(new GameActionEvent(this, "forfeit"));
		revalidate();
		repaint();
	}

	/**
	 * private void useJailFreeCardActionPerformed(ActionEvent e)
	 * Fires an event to accommodate using a get out of jail free card.
	 * @param e The ActionEvent.
	 */
	private void useJailFreeCardActionPerformed(ActionEvent e) {
		useJailFreeCard.setEnabled(false);
		fireEvent(new GameActionEvent(this, "getOutOfJail"));
	}

	/**
	 * private void EndTurnActionPerformed(ActionEvent e)
	 * Fires an event to accommodate ending the player's turn.
	 * @param e The ActionEvent
	 */
	private void EndTurnActionPerformed(ActionEvent e) {
		Dice.getGameDice().resetDiceListeners();
		Dice.getGameDice().removeListener(dieListner);
		fireEvent("endTurn");
		this.setVisible(false);
		revalidate();
		repaint();
	}

	/**
	 * private void showGroupActionPerformed(ActionEvent e)
	 * Opens a dialog showing the items in the AssetGroup.
	 * @param e The ActionEvent
	 */
	private void showGroupActionPerformed(ActionEvent e) 
	{//TODO:somthing
		/*AssetGroupDialog groups=
			new AssetGroupDialog((Frame)GameManager.CurrentUI.getFrame() , ((Asset)GameManager.currentGame.getGameBoard().get(representedPlayer.getCurrentPosition())).getGroup());
		groups.setVisible(true);*/
	}

	/**
	 * private void buyAssetActionPerformed(ActionEvent e)
	 * Fires an event to accommodate that the player has bought an asset.
	 * @param e The ActionEvent
	 */
	private void buyAssetActionPerformed(ActionEvent e) {
		buyAsset.setEnabled(false);
		fireEvent("buyAsset");
		initTreeModel();
	}

	/**
	 * private void buyHouseActionPerformed(ActionEvent e)
	 * Fires an event to accommodate that the player has bought a house.
	 * @param e The ActionEvent
	 */
	private void buyHouseActionPerformed(ActionEvent e) {
		buyHouse.setEnabled(false);
		fireEvent("buyHouse");
		initTreeModel();
	}

	/**
	 * private void initComponents()
	 * Initializes the components of the PlayerPanel. 
	 */
	private void initComponents() {
		buttonPane = new JPanel();
		Forfeit = new JButton();
		nameLabel = new JLabel();
		DicePane = new JPanel();
		useJailFreeCard = new JButton();
		CurrentSquare = new JPanel();
		showGroup = new JButton();
		buyAsset = new JButton();
		buyHouse = new JButton();
		PlayerInformation = new JPanel();
		scrollPane1 = new JScrollPane();
		PlayerHoldings = new JTree();

		//======== this ========
		setLayout(new BorderLayout());

		//======== buttonPane ========
		{
			buttonPane.setLayout(new GridLayout());

			//---- Forfeit ----
			Forfeit.setText("Forfeit Game");
			Forfeit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ForfeitActionPerformed(e);
				}
			});
			buttonPane.add(Forfeit);
		}
		add(buttonPane, BorderLayout.SOUTH);

		//---- nameLabel ----
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setText("his name");
		add(nameLabel, BorderLayout.NORTH);

		//======== DicePane ========
		{
			DicePane.setBorder(new EtchedBorder());
			DicePane.setLayout(new BorderLayout());

			//---- useJailFreeCard ----
			useJailFreeCard.setText("Get out of jail free");
			useJailFreeCard.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					useJailFreeCardActionPerformed(e);
				}
			});
			DicePane.add(useJailFreeCard, BorderLayout.SOUTH);
		}
		add(DicePane, BorderLayout.EAST);

		//======== CurrentSquare ========
		{
			CurrentSquare.setBorder(new EtchedBorder());
			CurrentSquare.setLayout(new BoxLayout(CurrentSquare, BoxLayout.Y_AXIS));

			//---- showGroup ----
			showGroup.setText("Show Group");
			showGroup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showGroupActionPerformed(e);
				}
			});
			CurrentSquare.add(showGroup);

			//---- buyAsset ----
			buyAsset.setText("Buy Asset");
			buyAsset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buyAssetActionPerformed(e);
				}
			});
			CurrentSquare.add(buyAsset);

			//---- buyHouse ----
			buyHouse.setText("Buy House");
			buyHouse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buyHouseActionPerformed(e);
				}
			});
			CurrentSquare.add(buyHouse);
		}
		add(CurrentSquare, BorderLayout.CENTER);

		//======== PlayerInformation ========
		{
			PlayerInformation.setBorder(new EtchedBorder());
			PlayerInformation.setLayout(new FlowLayout());

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(PlayerHoldings);
			}
			PlayerInformation.add(scrollPane1);
		}
		add(PlayerInformation, BorderLayout.WEST);
		
		DicePane.add(Dice.getGameDice());
		showGroup.setEnabled(false);
		buyAsset.setEnabled(false);
		buyHouse.setEnabled(false);
		useJailFreeCard.setEnabled(false);
	}
}
