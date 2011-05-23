/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.client;

import comm.Event;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import src.client.ui.DAFClient;

/**
 *
 * @author Shachar
 */
public class eventPoller extends TimerTask  {
    private static int lastEvent=0;

    @Override
    public void run() {
        System.err.println("eventPoller running");
        List<Event> events=Server.getInstance().getAllEvents(lastEvent);
        if(events!=null)
        {
            lastEvent+=events.size();
            Server.getInstance().addEventsToQueue(events);
        }
    }

}
