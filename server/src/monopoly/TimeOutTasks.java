package monopoly;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import players.Player;

/**
 *
 * @author omer
 */
public class TimeOutTasks {

    private static Timer timer = null;

    public static void StartTimer(Player P) {//assuming we only have 1 timer at a time
        stopTimer();
        if (GameManager.currentGame.getGamePlayers().contains(P)) {
            timer = new Timer();
            timer.schedule(new PlayerPromptTimeOutTimerTask(), (long) (GameManager.TIMEOUT_IN_SECONDS * GameManager.CONVERT_MS_TO_SECONDS));
        }
    }

    public static void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    private static class PlayerPromptTimeOutTimerTask extends TimerTask {

        public void run() {
            GameManager.currentGame.forfeit();
        }
    }
}
