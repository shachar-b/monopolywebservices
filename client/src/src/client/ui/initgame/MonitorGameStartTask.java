package src.client.ui.initgame;

import comm.Event;
import java.util.TimerTask;
import javax.swing.JFrame;
import src.client.GameManager;
import src.client.Server;
import src.client.ui.utils.EventTypes;

/**
 *  A class to monitor when a game has started, which then starts the GUI.
 * @author Shachar Butnaro and Omer Shenhar
 */
public class MonitorGameStartTask extends TimerTask {

    JFrame masterFrame;
    private final String gameName;
    private final Runnable performWhenStarted;

    public MonitorGameStartTask(JFrame masterFrame, String gameName, String playerName, Runnable performWhenStarted) {
        this.masterFrame = masterFrame;
        this.gameName = gameName;
        this.performWhenStarted = performWhenStarted;
    }

    @Override
    /**
     * Checks the event queue - If events are present, pops them until reaching one that is 
     * of the correct game, and that is of a GameStart type.
     * Upon such an event, the GUI is started and the game begins on the client side.
     */
    public void run() {
        System.out.println("Running code on thread: " + Thread.currentThread().getName());
        if (!Server.getInstance().isEventQueueEmpty()) {
            Event given = Server.getInstance().popEventFromQueue();
            while (!Server.getInstance().isEventQueueEmpty() && !given.getGameName().getValue().equals(gameName)) {
                given = Server.getInstance().popEventFromQueue();
            }
            if (given.getGameName().getValue().equals(gameName) && given.getEventType() == EventTypes.GameStart.getCode()) {
                GameManager gman = GameManager.createGameManager();//new window will be opened here
                masterFrame.dispose();
                performWhenStarted.run();
                this.cancel(); //Game started, no need to continue monitoring game start.
            }
        }
    }
}
