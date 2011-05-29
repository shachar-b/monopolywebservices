package src.ui.guiComponents.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import src.assets.*;
import src.ui.guiComponents.Squares.AssetSquarePanel;
import src.ui.guiComponents.Squares.SquarePanel;

/**
 * public class AssetGroupDialog extends JDialog 
 * shows one asset group
 * @author Omer Shenhar and Shachar Butnaro
 */
public class AssetGroupDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private AssetGroup represntedGroup;
    private JPanel cardsPane;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton okButton;
    private ArrayList<SquarePanel> componets = new ArrayList<SquarePanel>();

    /**
     * public AssetGroupDialog(Frame owner,AssetGroup groupToShow) 
     * a constructor for a AssetGroupDialog
     * @param owner - a valid frame
     * @param groupToShow - an valid not null AssetGroup
     */
    public AssetGroupDialog(Frame owner, AssetGroup groupToShow) {
        super(owner, true);
        represntedGroup = groupToShow;
        initComponents();
    }

    /**
     * public AssetGroupDialog(Dialog owner,AssetGroup groupToShow) 
     * a constructor for a AssetGroupDialog
     * @param owner - a valid Dialog
     * @param groupToShow - an valid not null AssetGroup
     */
    public AssetGroupDialog(Dialog owner, AssetGroup GroupToShow) {
        super(owner);
        represntedGroup = GroupToShow;
        initComponents();
    }

    /**
     * private void initComponents()
     * Initiates all components for the dialog
     */
    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(1, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                //---- okButton ----
                okButton.setText("RETURN");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }

        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        cardsPane = new JPanel(new GridLayout(1, 0));
        for (Asset ass : represntedGroup) {
            SquarePanel comp = new AssetSquarePanel(ass, false);
            componets.add(comp);
            cardsPane.add(comp, BorderLayout.CENTER);
        }
        cardsPane.setVisible(true);
        contentPane.add(cardsPane, BorderLayout.CENTER);
        contentPane.add(buttonBar, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
