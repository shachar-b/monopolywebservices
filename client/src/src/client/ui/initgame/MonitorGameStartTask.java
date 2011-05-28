
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.client.ui.initgame;

import comm.Event;
import java.util.TimerTask;
import javax.swing.JFrame;
import src.client.GameManager;
import src.client.Server;
import src.client.ui.utils.EventTypes;

/**
 *
 * @author Shachar
 */
public class MonitorGameStartTask extends TimerTask {

    JFrame masterFrame;
    private final String gameName;
    private final Runnable performWhenStarted;
    
    public MonitorGameStartTask(JFrame masterFrame,String gameName, String playerName, Runnable performWhenStarted) {
        this.masterFrame=masterFrame;
        this.gameName=gameName;
        this.performWhenStarted=performWhenStarted;
    }



    @Override
    public void run() {
       if(!Server.getInstance().isEventQueueEmpty())
       {
           Event given=Server.getInstance().popEventFromQueue();
           while (!Server.getInstance().isEventQueueEmpty() && !given.getGameName().getValue().equals(gameName)){
               given=Server.getInstance().popEventFromQueue();
           }
           if( given.getGameName().getValue().equals(gameName) && given.getEventType()==EventTypes.GameStart.getCode() )
           {
               GameManager gman = GameManager.createGameManager();//new window will be opened here
               masterFrame.dispose();
               performWhenStarted.run();
               this.cancel(); //Game started, no need to continue monitoring game start.
           }
       }
    }
}
