package src.listeners.innerChangeEventListener;

import java.util.ArrayList;

/**
 * public abstract class InnerChangeListenableClass extends JPanel
 * An abstract class for components that would have to be listened to by
 * an inner change events listener.
 * @author Omer Shenhar and Shachar Butnaro
 */
public abstract class InnerChangeListenableClass {

    private ArrayList<InnerChangeEventListner> listeners;

    /**
     * Constructor.
     * Creates a new ArrayList of listeners.
     */
    public InnerChangeListenableClass() {
        listeners = new ArrayList<InnerChangeEventListner>();
    }

    /**
     * public void addInnerChangeEventListner (InnerChangeEventListner listener)
     * Adds a new listener to the listeners list.
     * @param listener an InnerChangeEventListner
     */
    public void addInnerChangeEventListner(InnerChangeEventListner listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * public void removeListener(InnerChangeEventListner listener)
     * Removes a given listener from the listeners list.
     * @param listener the listener to be removed.
     */
    public void removeListener(InnerChangeEventListner listener) {
        listeners.remove(listener);
    }

    /**
     * protected void fireEvent (String message)
     * Creates an appropriate event from "message" and fires it.
     * @param message A String depicting the change event happening.
     */
    protected void fireEvent(String message) {
        for (InnerChangeEventListner listener : listeners) {
            listener.eventHappened(new InnerChangeEvet(this, message));
        }
    }

    /**
     * protected void fireEvent(InnerChangeEvet e)
     * Fires an event e
     * @param e An InnerChangeEvet that is happening.
     */
    protected void fireEvent(InnerChangeEvet e) {
        for (InnerChangeEventListner listener : listeners) {
            listener.eventHappened(e);
        }
    }
}
