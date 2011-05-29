package src.ui.guiComponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import src.listeners.gameActions.GameActionEvent;
import src.listeners.gameActions.GameActionEventListener;
import src.listeners.gameActions.GameActionsListenableClass;
import src.assets.Asset;
import src.assets.AssetGroup;
import src.client.GameManager;
import src.client.Server;
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
    private JPanel DicePane;
    private JPanel CurrentSquare;
    private JPanel PlayerInformation;
    private JScrollPane scrollPane1;
    private JTree PlayerHoldings;
    private final int NUMBER_OF_SQUARES_TO_SHOW = 1;
    private GameActionEventListener dieListner = new GameActionEventListener() {

        @Override
        public void eventHappened(GameActionEvent gameActionEvent) {
            dieRolled();
        }
    };

    /**
     * private void dieRolled()
     * Fires the event that the die have been thrown, and sets buttons accordingly.
     */
    private void dieRolled() {
        fireEvent("throwDie");
    }

    /**
     * Constructor.
     * @param player A valid non-null player.
     */
    public PlayerPanel() {
        representedPlayer = GameManager.staticInstance.getPlayerByName(GameManager.clientName);
        initComponents();
        Dice.getGameDice().addGameActionsListener(dieListner);
        Square currentSquare = GameManager.staticInstance.getGameBoard().get(representedPlayer.getCurrentPosition());
        setSquarePanelContent(currentSquare, representedPlayer);
        initTreeModel();
    }

    /**
     * public void setSquarePanelContent(Square currentSquare, Player player)
     * Sets the content of the center square, which holds information
     * and action options regarding a gameboard square.
     * @param currentSquare - The Square being represented.
     * @param player A valid non-null player on that Square.
     */
    public void setSquarePanelContent(Square currentSquare, Player player) {
        if (CurrentSquare.getComponentCount() >= NUMBER_OF_SQUARES_TO_SHOW) {
            CurrentSquare.remove(0);
        }
        CurrentSquare.add(SqurePanelFactory.makeCorrectSqurePanel(currentSquare, false), 0);
        initTreeModel();
    }

    /**
     * private void initTreeModel()
     * Inserts the current player's information in the information tree of the panel.
     */
    private void initTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(representedPlayer.getName() + "     ");

        DefaultMutableTreeNode moneyNode = new DefaultMutableTreeNode("Money");
        moneyNode.add(new DefaultMutableTreeNode("Balance = " + representedPlayer.getBalance()));

        DefaultMutableTreeNode assetsNode = new DefaultMutableTreeNode("Assets");
        ArrayList<Asset> assetsList = representedPlayer.getAssetList();
        for (Asset asset : assetsList) {
            assetsNode.add(new DefaultMutableTreeNode(asset.getName()));
        }

        DefaultMutableTreeNode groupsNode = new DefaultMutableTreeNode("Groups");
        ArrayList<AssetGroup> groupsList = representedPlayer.getGroups();
        for (AssetGroup group : groupsList) {
            groupsNode.add(new DefaultMutableTreeNode(group.getName()));
        }

        DefaultTreeModel playerModel = new DefaultTreeModel(root);
        playerModel.insertNodeInto(moneyNode, root, playerModel.getChildCount(root));
        playerModel.insertNodeInto(assetsNode, root, playerModel.getChildCount(root));
        playerModel.insertNodeInto(groupsNode, root, playerModel.getChildCount(root));

        PlayerHoldings.setModel(playerModel);
    }

    /**
     * private void ForfeitActionPerformed(ActionEvent e)
     * Fires a forfeit event to remove a player from the game.
     * @param e The ActionEvent
     */
    private void ForfeitActionPerformed(ActionEvent e) {
        representedPlayer.setWantsToQuit();
        Dice.getGameDice().resetDiceListeners();
        Server.getInstance().resign(GameManager.clientPlayerID);
        Forfeit.setEnabled(false);
        revalidate();
        repaint();
    }

    /**
     * private void initComponents()
     * Initializes the components of the PlayerPanel. 
     */
    private void initComponents() {
        buttonPane = new JPanel();
        Forfeit = new JButton();
        DicePane = new JPanel();
        CurrentSquare = new JPanel();
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

        //======== DicePane ========
        {
            DicePane.setBorder(new EtchedBorder());
            DicePane.setLayout(new BorderLayout());

        }
        add(DicePane, BorderLayout.EAST);

        //======== CurrentSquare ========
        {
            CurrentSquare.setBorder(new EtchedBorder());
            CurrentSquare.setLayout(new BoxLayout(CurrentSquare, BoxLayout.Y_AXIS));

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
    }
}
