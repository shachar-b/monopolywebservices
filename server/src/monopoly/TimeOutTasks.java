/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.Date;
import java.util.HashMap;
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
        timer = new Timer();
        Date currDate = new Date();
        System.err.println("Starting at - " + currDate.toString());
        System.err.println(GameManager.TIMEOUT_IN_SECONDS + " RECEIVED!");
        timer.schedule(new PlayerPromptTimeOutTimerTask(P), (long) (GameManager.TIMEOUT_IN_SECONDS * GameManager.CONVERT_MS_TO_SECONDS));
    }

    public static void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;

    }

    private static class PlayerPromptTimeOutTimerTask extends TimerTask {

        private Player player;

        public PlayerPromptTimeOutTimerTask(Player p) {
            this.player = p;
        }

        public void run() {
            //TODO: make the correct player forfit
            Date currDate = new Date();
            System.err.println("Ending at  - " + currDate.toString());
            GameManager.currentGame.eventDispatch(player.getID(), "forfeit");
        }
    }
}
