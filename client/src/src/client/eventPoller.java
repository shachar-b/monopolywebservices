package src.client;

import comm.Event;
import java.util.List;
import java.util.TimerTask;

/**
 *  An event poller class, handles getting the events from the server, and putting them in an event queue.
 * @author Shachar Butnaro and Omer Shenhar
 */
public class eventPoller extends TimerTask {

    private static int lastEvent = 0;

    public static void resetEventPoller() {
        lastEvent = 0;
    }

    @Override
    /**
     * Gets events from server, from after the lastEvent received.
     * If events were indeed receives, increments the lastEvent appropriately
     * and adds them to the event queue.
     * Also yields so that the event feeder can take priority in handling them.
     */
    public void run() {
        System.out.println("Running code on thread: " + Thread.currentThread().getName());
        List<Event> events = Server.getInstance().getAllEvents(lastEvent);
        if (events != null && !events.isEmpty()) {
            lastEvent += events.size();
            Server.getInstance().addEventsToQueue(events);
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            Thread.currentThread().yield();
        }
    }
}