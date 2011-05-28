package src.squares;

import src.listeners.innerChangeEventListener.InnerChangeListenableClass;

/**
 * abstract class Square  a base class for the monopoly board squares 
 * public
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public abstract class Square extends InnerChangeListenableClass {

    protected String name = this.getClass().getSimpleName();

    /**
     * method public String getName
     * public
     * a getter for Square name
     * @return the name of the current Square
     */
    public String getName() {
        return name;
    }
}