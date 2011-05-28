package listeners.gameActions;

/**
 * an Interface for a listener used for GameActionEvents.
 * @author Omer Shenhar and Shachar Butnaro
 */
public interface GameActionEventListener {

    /**
     * public void eventHappened(GameActionEvent gameActionEvent)
     * @param gameActionEvent an event which happened in the game.
     */
    public void eventHappened(GameActionEvent gameActionEvent);
}
