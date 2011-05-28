package src.listeners.innerChangeEventListener;

/**
 * Public class InnerChangeEvet - describes a change event which occurs in the game.
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class InnerChangeEvet {

    private Object source;
    private String message;

    /**
     * public InnerChangeEvet (Object source, String message)
     * constructor for InnerChangeEvet.
     * @param source the Object which initiated the event.
     * @param message a String holding the context of the event.
     */
    public InnerChangeEvet(Object source, String message) {
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
