/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import players.Player;

/**
 *
 * @author omer
 */
public class TimeOutTasks {
    
    private  static HashMap<Player,Timer>  timers=new HashMap<Player, Timer>();
    
    
    
    public static void StartTimer(Player P,int timerInSeconds)
    {//assuming we only have 1 timer at a time
        Timer helper=new Timer();
        
        helper.schedule(new PlayerPromptTimeOutTimerTask(P) ,timerInSeconds*1000 );
        
        timers.put(P, helper);
    }
    public static void stopTimer(Player p)
    {
        if(timers.containsKey(p))
        {
            Timer helper=(timers.get(p));
            if(helper!=null)
                helper.cancel();
            timers.remove(p);
        }
    }

    private static class PlayerPromptTimeOutTimerTask extends TimerTask {

        private Player player;

        public PlayerPromptTimeOutTimerTask(Player p) {
            this.player = p;
        }

        public void run() {
            //TODO: make the correct player forfit
           GameManager.currentGame.eventDispatch(player.getID(), "forfeit");
        }
    }
}

