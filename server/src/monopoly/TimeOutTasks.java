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
/**
     * start a timer for the given player
     * @param P -a non null player
     */
    public static void StartTimer(Player P) {//assuming we only have 1 timer at a time
        stopTimer();
        if (GameManager.currentGame.getGamePlayers().contains(P)) {
            timer = new Timer();
            timer.schedule(new PlayerPromptTimeOutTimerTask(), (long) (GameManager.TIMEOUT_IN_SECONDS * GameManager.CONVERT_MS_TO_SECONDS));
        }
    }
    /**
     * if a timer exsist stope it
     */
    public static void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    
    /*
     * forfit when expires
     */
    private static class PlayerPromptTimeOutTimerTask extends TimerTask {

        public void run() {
            GameManager.currentGame.forfeit();
        }
    }
}
