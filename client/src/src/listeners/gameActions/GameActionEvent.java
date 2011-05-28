package src.listeners.gameActions;

/**
 * Public class GameActionEvent - describes an action which occurs in the game.
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class GameActionEvent {

    private Object source;
    private String message;

    /**
     * public GameActionEvent (Object source, String message)
     * Constructor for GameActionEvent
     * @param source the Object which initiated the event.
     * @param message a String holding the context of the event.
     */
    public GameActionEvent(Object source, String message) {
        this.source = source;
        this.message = message;
    }

    /**
     * public Object getSource()
     * getter for Source.
     * @return the Object which initiated the event.
     */
    public Object getSource() {
        return source;
    }

    /**
     * public String getMessage()
     * getter for Message.
     * @return A String holding the context of the event.
     */
    public String getMessage() {
        return message;
    }
}
