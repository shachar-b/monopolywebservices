
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.client.ui.initgame;

import comm.Event;
import java.util.TimerTask;
import javax.swing.JFrame;
import src.client.Server;
import src.client.eventPoller;
import src.client.ui.DAFClient;
import src.client.ui.utils.EventTypes;

/**
 *
 * @author Shachar
 */
public class MonitorGameStartTask extends TimerTask {

    JFrame frameToClose;
    private final String gameName;
    private final String playerName;
    public MonitorGameStartTask(JFrame frameToClose,String gameName, String playerName) {
        this.frameToClose=frameToClose;
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
               DAFClient client1 = new DAFClient();
               client1.setVisible(true);
               frameToClose.setContentPane(client1);
               frameToClose.setTitle("DAFClient - " + gameName + " : " + playerName);
               frameToClose.pack();
               frameToClose.validate();
               frameToClose.repaint();
               
               this.cancel(); //Game started, no need to continue monitoring game start.
           }
       }
    }
}
