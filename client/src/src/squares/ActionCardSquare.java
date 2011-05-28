package src.squares;

/**
 * public class ActionCardSquare extends Square
 * @see Square
 * public
 * a Call up or Surprise Square in the monopoly game 
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class ActionCardSquare extends Square {

    int sign;

    /**
     * method public ActionCardSquare(int sign1)
     * public
     * a constructor for ActionCardSquare
     * @param sign1 - either -1 for call up or 1 for surprise
     */
    public ActionCardSquare(int sign1) {
        sign = sign1;
    }


    /* (non-Javadoc)
     * @see squares.Square#getName()
     */
    @Override
    public String getName() {
        return sign == 1 ? "Surprise" : "CallUp";
    }

    /**
     * @return true if it is call up false if it is surprise
     */
    public boolean IsCallUp() {
        return sign == -1;

    }
}
