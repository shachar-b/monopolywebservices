package src.ui.guiComponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import src.client.GameManager;
import src.players.Player;
import src.squares.Square;
import src.ui.guiComponents.Squares.SquarePanel;
import src.ui.guiComponents.Squares.SqurePanelFactory;


import ui.utils.ImagePanel;

/**
 * public class GameBoardUI extends JPanel
 * This holds the left side of the frame - that is, the squares and the CenterPanel. 
 * @author Liron Blecher. Modified by Omer Shenhar and Shachar Butnaro.
 */
public class GameBoardUI extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int LINE_SIZE = 9;
    private List<SquarePanel> components;
    private HashMap<Player, ImagePanel> playersIcons = new HashMap<Player, ImagePanel>();
    CenterPanel innerPanel;

    /**
     * Constructor.
     */
    public GameBoardUI() {
        super();
        initUI();
    }

    /**
     * public SquarePanel getSquareAtIndex(int index)
     * Returns a SquarePanel at the requested index.
     * @param index An integer which is the index of the requested square.
     * @return a SquarePanel representing the requested square.
     */
    public SquarePanel getSquareAtIndex(int index) {
        return components.get(index);
    }

    /**
     * public void movePlayer(Player player,int from,int to)
     * Re-draws the player on the board in the new location.
     * @param player A valid non-null player to be moved.
     * @param from an index of where the player started.
     * @param to an index of where the player landed.
     */
    public void movePlayer(Player player, int from, int to) {
        components.get(from).removePlayer(playersIcons.get(player));
        components.get(to).addPlayer(playersIcons.get(player));
        updatePlayersLegend();
    }

    /**
     * public void addPlayerIcon(Player p, ImagePanel icon)
     * Adds a player's icon to the board, to be drawn.
     * @param p a valid non-null player.
     * @param icon the imagePanel holding the player's icon.
     */
    public void addPlayerIcon(Player p, ImagePanel icon) {
        playersIcons.put(p, icon);
        components.get(0).addPlayer(playersIcons.get(p));
    }

    /**
     * public void removePlayerIcon(Player p)
     * Removes a visible player's icon from the board.
     * @param p a valid non-null player.
     */
    public void removePlayerIcon(Player p) {
        components.get(p.getCurrentPosition()).removePlayer(playersIcons.get(p));
        playersIcons.remove(p);
    }

    /**
     * private void initUI()
     * Initialize the component using GridBagLayout - as supplied by Liron Blecher.
     * Changed so that the center part holds a CenterPanel.
     */
    private void initUI() {
        //init layout
        this.setLayout(new GridBagLayout());

        components = new LinkedList<SquarePanel>();
        //TODO:add a game board
        ArrayList<Square> bord = GameManager.staticInstance.getGameBoard();
        for (int i = 0; i < LINE_SIZE * 4; i++) {
            components.add(SqurePanelFactory.makeCorrectSqurePanel(bord.get(i)));
        }

        Iterator<SquarePanel> componentIterator = components.iterator();

        //Add Panels for Each of the four sides
        for (int sideIndex = 0; sideIndex < 4; sideIndex++) {
            for (int lineIndex = 0; lineIndex < LINE_SIZE; lineIndex++) {
                SquarePanel component = componentIterator.next();
                switch (sideIndex) {
                    case 0:
                        //top line
                        addComponent(lineIndex, 0, component);
                        break;
                    case 1:
                        //right line
                        addComponent(LINE_SIZE, lineIndex, component);
                        break;
                    case 2:
                        //bottom line - and in reverse order
                        addComponent(LINE_SIZE - lineIndex, LINE_SIZE, component);
                        break;
                    case 3:
                        //left line - and in reverse order
                        addComponent(0, LINE_SIZE - lineIndex, component);
                        break;
                }
            }
            //TODO : !!! Pick up here, make iconPanel not null !!!
            for (Player p : GameManager.staticInstance.getGamePlayers()) {
                addPlayerIcon(p, p.getIconPanel());
            }

        }

        // Main Inner Area Notice Starts at (1,1) and takes up 11x11
        innerPanel = new CenterPanel();
        this.add(innerPanel,
                new GridBagConstraints(1,
                1,
                LINE_SIZE - 1,
                LINE_SIZE - 1,
                0.1, 0.1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Written by Liron Blecher - untouched.
     */
    private void addComponent(int gridX, int gridY, JComponent component) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridX;
        c.gridy = gridY;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        this.add(component, c);
    }

    /**
     * public void updatePlayersLegend()
     * Calls for an update of the players' legend.
     */
    public void updatePlayersLegend() {
        innerPanel.updateLegend();
    }
}