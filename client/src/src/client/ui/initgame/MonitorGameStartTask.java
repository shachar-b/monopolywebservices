
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
    private final String playerName;
    
    public MonitorGameStartTask(JFrame masterFrame,String gameName, String playerName) {
        this.masterFrame=masterFrame;
        this.gameName=gameName;
        this.playerName = playerName;
    }



    @Override
    public void run() {
       if(!Server.getInstance().isEventQueueEmpty())
       {
           Event given=Server.getInstance().popEventFromQueue();
           if(given.getEventType()==EventTypes.GameStart.getCode() )//&& given.getGameName().toString()==gameName)
           {
               GameManager gman = GameManager.createShit();//new window will be opend here
               masterFrame.dispose();
               this.cancel(); //Game started, no need to continue monitoring game start.
           }
       }
    }
}
