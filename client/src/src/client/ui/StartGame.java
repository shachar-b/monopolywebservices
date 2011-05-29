package src.client.ui;

import java.util.Timer;
import javax.swing.SwingUtilities;
import src.client.Server;
import src.client.eventPoller;
import src.client.ui.initgame.GamesPanel;
import src.client.ui.initgame.ServerChooser;
import src.client.ui.utils.ExamplesUtils;

/**
 *
 * @author blecherl
 */
public class StartGame {

    static Timer eventPollerTimer = null;

    /*
     * This is the entry point of the client.
     * Launches a server chooser window.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                ServerChooser chooser= new ServerChooser(null, true);
                chooser.setVisible(true);
            }
        });
    }
    
    /**
     * Launches the game creation, selection and joining window.
     */
    public static void StartGame()
    {
        ExamplesUtils.setNativeLookAndFeel();
        startEventPollingTask();
        ExamplesUtils.showExample("Join Game", new GamesPanel());    
    }

    /**
     * Starts the event polling task
     */
    private static void startEventPollingTask() {
        eventPoller.resetEventPoller();
        eventPollerTimer = Server.getInstance().startPolling("Event polling timer", new eventPoller(), 0, 2);
    }

    /**
     * Stops the event polling task
     */
    public static void stopEventPollingTask() {
        if (eventPollerTimer != null) {
            eventPollerTimer.cancel();
        }
    }
}