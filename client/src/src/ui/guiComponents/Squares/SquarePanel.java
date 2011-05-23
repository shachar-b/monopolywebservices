package src.ui.guiComponents.Squares;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import src.client.GameManager;


import src.squares.Square;
import ui.utils.ImagePanel;

/**
 * public abstract class SquarePanel extends JPanel
 * an abstract square panel
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public abstract class SquarePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel playerPanel;
    protected Square representedSquare;
    protected JLabel titleLabel;
    protected JLabel groupLabel;
    private ArrayList<ImagePanel> iconList = new ArrayList<ImagePanel>();

    /**
     * public SquarePanel(Square representedSquare)
     * a constructor for an abstract SquarePanel
     * @param representedSquare- a valid non null square
     */
    public SquarePanel(Square representedSquare) {
        super();
        this.representedSquare = representedSquare;
        initComponents();
        initPlayersPanel();
    }

    /**
     * private void initComponents()
     * initiates all components of the square panel
     */
    private void initComponents() {
        JPanel titleArea = new JPanel();
        titleArea.setLayout(new BoxLayout(titleArea, BoxLayout.Y_AXIS));
        titleLabel = new JLabel();

        //======== this ========
        setBorder(new EtchedBorder());
        setLayout(new BorderLayout());

        //---- Group ----
        groupLabel = new JLabel();
        groupLabel.setEnabled(false);
        groupLabel.setFont(GameManager.DefaultFont);
        titleArea.add(groupLabel);

        //---- Name ----
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(new Color(51, 51, 255));
        titleArea.setFont(GameManager.DefaultFont);
        titleArea.add(titleLabel);
        titleLabel.setText(representedSquare.getName());

        add(titleArea, BorderLayout.NORTH);
    }

    /**
     * private void initPlayersPanel()
     * initiates the panel for player icons
     */
    private void initPlayersPanel() {
        playerPanel = new JPanel(new FlowLayout());
        this.add(playerPanel, BorderLayout.SOUTH);
    }

    /**
     * public void addPlayer(ImagePanel iconPanel)
     * adds a player icon to the player area of this
     * @param iconPanel - a valid non null ImagePanel
     */
    public void addPlayer(ImagePanel iconPanel) {
        iconPanel.setMinimumSize(new Dimension(20, 20));	//Force the ImagePanel holding
        iconPanel.setMaximumSize(new Dimension(20, 20));	//the player icon to
        iconPanel.setPreferredSize(new Dimension(20, 20));	//take the size it needs.
        iconList.add(iconPanel);
        playerPanel.add(iconPanel);
        this.validate();
        this.repaint();
    }

    /**
     * public void removePlayer(ImagePanel icon)
     * removes the given Image icon from the player icons area
     * @param icon - a valid non null ImagePanel which was added by addPlayer
     */
    public void removePlayer(ImagePanel icon) {
        if (iconList.contains(icon)) {
            iconList.remove(icon);
            playerPanel.remove(icon);
            this.validate();
            this.repaint();
        } else {
            throw new RuntimeException("Error: Player not found when trying to remove from general square.");
        }
    }
}
