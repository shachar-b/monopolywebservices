package src.ui.guiComponents.dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JLabel;

import listeners.gameActions.GameActionEvent;
import listeners.gameActions.GameActionEventListener;
import listeners.gameActions.GameActionsListenableClass;

/**
 * @author Stijn Strickx, from http://www.proglogic.com/code/java/game/rolldice.php.
 * Modified by Omer Shenhar and Shachar Butnaro.
 *
 */
class ButtonListener extends GameActionsListenableClass implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel dice1;
	private JLabel dice2;
	private JLabel text;

	/**
	 * public ButtonListener(JLabel dice1, JLabel dice2, JLabel text)
	 * a constructor for ButtonListener
	 * @param dice1 - JLabel which holds dice1 icon
	 * @param dice2 - JLabel which holds dice2 icon
	 * @param text	- JLabel which holds result text
	 */
	public ButtonListener(JLabel dice1, JLabel dice2, JLabel text) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * creates a throw animation
	 */
	public void actionPerformed(ActionEvent e) {
		Timer timer = new Timer();
		GameActionEventListener preformWhenDone= new GameActionEventListener() {

			@Override
			public void eventHappened(GameActionEvent innerChangeEvet) {
				done();

			}
		};
		timer.scheduleAtFixedRate(new ThrowDice(dice1, dice2, text,preformWhenDone), 0, 100);
	}
	/**
	 * private  void  done()
	 * notifies all listing classes that the roll is done
	 */
	private  void  done() {
		fireEvent("throwDie");
	}
}

