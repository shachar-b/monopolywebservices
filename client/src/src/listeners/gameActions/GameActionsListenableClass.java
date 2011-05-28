package src.listeners.gameActions;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * public abstract class GameActionsListenableClass extends JPanel
 * An abstract class for components that would have to be listened to by
 * a game events listener.
 * @author Omer Shenhar and Shachar Butnaro
 */
public abstract class GameActionsListenableClass extends JPanel {

    private static final long serialVersionUID = 1L;
    private ArrayList<GameActionEventListener> listeners;

    /**
     * Constructor.
     * Creates a new ArrayList of listeners.
     */
    public GameActionsListenableClass() {
        listeners = new ArrayList<GameActionEventListener>();
    }

    /**
     * public void addGameActionsListener (GameActionEventListener listener)
     * Adds a new listener to the listeners list.
     * @param listener a GameActionEventListener to be added.
     */
    public void addGameActionsListener(GameActionEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * public void removeListener(GameActionEventListener listener)
     * Removes a given listener from the listeners list.
     * @param listener the listener to be removed.
     */
    public void removeListener(GameActionEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * public void removeAllListeners()
     * removes all listeners from the listeners list.
     */
    public void removeAllListeners() {
        listeners.clear();
    }

    /**
     * protected void fireEvent (String message)
     * Creates an appropriate event from "message" and fires it.
     * @param message A String depicting the event happening.
     */
    protected void fireEvent(String message) {
        for (GameActionEventListener listener : listeners) {
            listener.eventHappened(new GameActionEvent(this, message));
        }
    }

    /**
     * protected void fireEvent(GameActionEvent e)
     * Fires an event e
     * @param e A GameActionEvent that is happening.
     */
    protected void fireEvent(GameActionEvent e) {
        for (GameActionEventListener listener : listeners) {
            listener.eventHappened(e);
        }
    }
}
