package src.client.ui;

import java.util.Timer;
import src.client.Server;
import src.client.eventPoller;
import src.client.ui.initgame.GamesPanel;
import src.client.ui.utils.ExamplesUtils;

/**
 *
 * @author blecherl
 */
public class JoinGameExample {

    static Timer eventPollerTimer = null;

    public static void main (String[] args) {
        ExamplesUtils.setNativeLookAndFeel();
        startEventPollingTask();
        ExamplesUtils.showExample("Join Game", new GamesPanel());
    }

    private static void startEventPollingTask() {
        eventPollerTimer = Server.getInstance().startPolling("Event polling timer", new eventPoller(), 0, 2);
    }

    public static void stopEventPollingTask() { //TODO : maybe make not public?
        if (eventPollerTimer != null)
            eventPollerTimer.cancel();
    }
}