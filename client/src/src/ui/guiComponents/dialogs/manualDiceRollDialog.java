/*
 * Created by JFormDesigner on Tue Apr 26 17:48:23 IDT 2011
 */
package src.ui.guiComponents.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import src.client.GameManager;
import src.client.Server;

/**
 * @author Shachar
 */
public class manualDiceRollDialog extends JDialog {

    private int eventID;
    private static final long serialVersionUID = 1L;

    public manualDiceRollDialog(Frame owner, int eventID) {
        super(owner, true);
        this.eventID = eventID;
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        Server.getInstance().setDiceRollResults(GameManager.clientPlayerID, eventID, (Integer) dice1.getValue(), (Integer) dice2.getValue());
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        titleLabel = new JLabel();
        spinnersPanel = new JPanel();
        dice1 = new JSpinner();
        dice2 = new JSpinner();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("Manual dice selector");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout(10, 10));

                //---- titleLabel ----
                titleLabel.setText("Set the die outcome:");
                contentPanel.add(titleLabel, BorderLayout.NORTH);

                //======== spinnersPanel ========
                {
                    spinnersPanel.setLayout(new BoxLayout(spinnersPanel, BoxLayout.X_AXIS));

                    //---- dice1 ----
                    dice1.setModel(new SpinnerNumberModel(1, 0, 36, 1));
                    spinnersPanel.add(dice1);

                    //---- dice2 ----
                    dice2.setModel(new SpinnerNumberModel(1, 0, 36, 1));
                    spinnersPanel.add(dice2);
                }
                contentPanel.add(spinnersPanel, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JPanel spinnersPanel;
    private JSpinner dice1;
    private JSpinner dice2;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
