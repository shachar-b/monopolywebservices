package src.ui.guiComponents.dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import src.client.GameManager;

import src.ui.utils.Utils;

/**
 * public class aboutDialog extends JDialog
 * this is the about dialog for the game
 * @author Omer Shenhar and Shachar Butnaro
 */
public class aboutDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JTextPane textPane1;
    private JPanel buttonBar;
    private JButton okButton;
    private JPanel PicturePanel;

    /**
     * public aboutDialog(Frame owner)
     * a constructor for the about dialog
     * @param owner -a valid frame	
     */
    public aboutDialog(Frame owner) {
        super(owner);
        initComponents();
        PicturePanel.add(new JLabel(Utils.getImageIcon(GameManager.IMAGES_FOLDER + "MiscIcons/GameIcon.png")));
        this.pack();
    }

    /**
     * public aboutDialog(Dialog owner)
     * a constructor for the about dialog
     * @param owner -a Dialog frame	
     */
    public aboutDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    /**	
     * private void okButtonActionPerformed(ActionEvent e)
     * Indicates that the OK button was pressed -closes dialog
     * @param e - the click event
     */
    private void okButtonActionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * private void initComponents()
     * Initiates all components for the dialog
     */
    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        textPane1 = new JTextPane();
        buttonBar = new JPanel();
        okButton = new JButton();
        PicturePanel = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                //---- textPane1 ----
                textPane1.setText("Monopoly (for Java)\nBy Shachar Butnaro and Omer Shenhar");
                textPane1.setEditable(false);
                textPane1.setBackground(SystemColor.menu);
                contentPanel.add(textPane1);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new BorderLayout());

                //---- okButton ----
                okButton.setText("Who cares?");
                okButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, BorderLayout.CENTER);
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            //======== PicturePanel ========
            {
                PicturePanel.setLayout(new FlowLayout());
            }
            dialogPane.add(PicturePanel, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
