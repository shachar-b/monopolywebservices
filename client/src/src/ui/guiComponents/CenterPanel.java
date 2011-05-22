package src.ui.guiComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import listeners.innerChangeEventListener.InnerChangeEventListner;
import listeners.innerChangeEventListener.InnerChangeEvet;
import src.client.GameManager;
import src.players.Player;
import ui.utils.IconCellRenderer;
import ui.utils.ImagePanel;
import ui.utils.TransparentTable;
import ui.utils.Utils;

/**
 * public class CenterPanel extends JPanel
 * This is the center area of the application.
 * It holds the game logo, the card decks' pictures and the player's legend.
 * @author Omer Shenhar and Shachar Butnaro
 */
public class CenterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel CallUpCardsPanel;
	private JPanel LegendPanel;
	private TransparentTable legendTable;
	InnerChangeEventListner playerListner =new InnerChangeEventListner() {
		@Override
		public void eventHappened(InnerChangeEvet innerChangeEvet) {
			updateLegend();	
		}
	};
	
	/**
	 * Constructor - calls the relevant functions.
	 */
	public CenterPanel() {
		initComponents();
		initGameLogo();
		initCardPanels();
		initLegendArea();
		initLegend();
	}

	/**
	 * private void initLegendArea()
	 * Initializes the area used for showing the legend.
	 */
	private void initLegendArea()
	{
		legendTable = new TransparentTable();
		LegendPanel.add(new JScrollPane(legendTable)); //JScrollPane is to show column headers
		legendTable.setRowHeight(50); //Set row height to enable showing images appropriately
	}

	/**
	 * private void initLegend()
	 * Initializes the table used to represent the legend.
	 * Allows insertion of icons into the cells of the first column.
	 */
	private void initLegend()
	{
		DefaultTableModel model =(DefaultTableModel)legendTable.getModel();
		model.addColumn("Icon");
		model.addColumn("Name");
		model.addColumn("Balance");
		model.addColumn("Current Square");
		TableColumn col = legendTable.getColumnModel().getColumn(0);
		col.setCellRenderer(new IconCellRenderer());
	}

	/**
	 * public void updateLegend()
	 * Resets the legend, re-initializes it and inserts current information.
	 */
	public void updateLegend()
	{
            //TODO:implement legend update
		legendTable.resetModel();
		initLegend(); //To reset the model
		//ArrayList<Player> players = GameManager.currentGame.getGamePlayers();
		DefaultTableModel model =(DefaultTableModel)legendTable.getModel();
/*
		for (Player player : players)//Add players' information
		{
			player.removeListener(playerListner);//avoid duplicates
			player.addInnerChangeEventListner(playerListner);
			Object icon = player.getIconPanel();
			Object name = player.getName();
			Object balance = player.getBalance();
			Object currentSquare = GameManager.currentGame.getGameBoard().get(player.getCurrentPosition()).getName();
			Object[] rowData = {icon,name,balance,currentSquare};
			model.addRow(rowData);
		}*/
	}

	/**
	 * private void initGameLogo()
	 * Initializes the game logo picture.
	 */
	private void initGameLogo()
	{
		JLabel logoLabel = new JLabel(Utils.getImageIcon(GameManager.IMAGES_FOLDER+"MiscIcons/logo.gif"));
		this.add(logoLabel, BorderLayout.NORTH);
	}

	/**
	 * private void initCardPanels()
	 * Initializes the card decks' pictures.
	 */
	private void initCardPanels()
	{
		CallUpCardsPanel.setPreferredSize(new Dimension(100, 200));
		ImagePanel callUpLabel = new ImagePanel(GameManager.IMAGES_FOLDER+"MiscIcons/CallUp.gif", true);
		ImagePanel surpriseLabel = new ImagePanel(GameManager.IMAGES_FOLDER+"MiscIcons/surprise.gif",true);
		CallUpCardsPanel.add(callUpLabel, BorderLayout.CENTER);
		CallUpCardsPanel.add(surpriseLabel, BorderLayout.CENTER);
	}

	/**
	 * private void initComponents()
	 * Initializes the components of the CenterPanel.
	 */
	private void initComponents() {
		CallUpCardsPanel = new JPanel();
		LegendPanel = new JPanel();

		//======== this ========
		setLayout(new BorderLayout());

		//======== CallUpCardsPanel ========
		{
			CallUpCardsPanel.setLayout(new BoxLayout(CallUpCardsPanel, BoxLayout.Y_AXIS));
		}
		add(CallUpCardsPanel, BorderLayout.WEST);

		//======== LegendPanel ========
		{
			LegendPanel.setLayout(new FlowLayout());
		}
		add(LegendPanel, BorderLayout.CENTER);
	}
}