
package src.ui.guiComponents.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



/**
 * public class midGameEntryDialog extends JDialog
 * this is a dialog which opens when trying to start a new game after another game has started
 * @author Omer Shenhar and Shachar Butnaro
 */
public class midGameEntryDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel dialogPane;

	private JPanel buttonBar;

	private JLabel TextLabel;

	private JButton okButton;

	private JButton cancelButton;

	private JPanel contentPanel;

	/**
	 * public midGameEntryDialog(Frame owner) 
	 * a constructor for a midGameEntryDialog
	 * @param owner - an valid owner frame
	 */
	public midGameEntryDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	/**
	 * public midGameEntryDialog(Dialog owner) 
	 * a constructor for a midGameEntryDialog
	 * @param owner - a valid owner dialog
	 */
	public midGameEntryDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	/**
	 * private void cancelButtonActionPerformed(ActionEvent e)
	 * this method cancels the new game action and disposes of this window
	 * @param e - the click event
	 */
	private void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * private void okButtonActionPerformed(ActionEvent e)
	 * this method acknowledges that  the OK button was pressed and
	 * Therefore starts a new monopoly game and a new ui
	 * @param e - the click event
	 */
	private void okButtonActionPerformed(ActionEvent e) {
		
	}

	/**
	 *	private void initComponents() 
	 *	initiates all the dialog components
	 */
	private void initComponents() {
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		TextLabel = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("End game");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

				//---- TextLabel ----
				TextLabel.setText("Are you sure you want to end the current game?");
				TextLabel.setVerticalAlignment(SwingConstants.TOP);
				contentPanel.add(TextLabel);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK - End game");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel - Stay in game");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
	
	}
}
