/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.client;

import comm.Event;
import java.util.List;
import java.util.TimerTask;

/**
 *
 * @author Shachar
 */
public class eventPoller extends TimerTask  {
    private static int lastEvent=0;

    @Override
    public void run() {
        System.err.println("eventPoller running. lastEvent="+lastEvent);
        List<Event> events=Server.getInstance().getAllEvents(lastEvent);
        if(events!=null && !events.isEmpty())
        {
            System.err.println("first event that i got was: "+events.get(0).getEventMessage().getValue());
            lastEvent+=events.size();
            Server.getInstance().addEventsToQueue(events);
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            Thread.currentThread().yield();
        }
    }

}
