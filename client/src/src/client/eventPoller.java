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

/**
 *
 * @author Shachar
 */
public class eventPoller extends TimerTask  {

    public static  ConcurrentLinkedQueue<Event> EventQueue= new ConcurrentLinkedQueue<Event>();
    private static int lastEvent=0;

    @Override
    public void run() {
        System.err.println("eventPoller running");
        List<Event> events=Server.getInstance().getAllEvents(lastEvent);
        if(events!=null)
        {
            lastEvent+=events.size();
            EventQueue.addAll(events);
        }

    }

}
