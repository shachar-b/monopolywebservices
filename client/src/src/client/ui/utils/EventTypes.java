package src.client.ui.utils;

/**
 *  An enumerated type to make using the different event type codes more convinient.
 * @author Shachar Butnaro and Omer Shenhar.
 */
public enum EventTypes {

    GameStart(1), GameOver(2), GameWinner(3), PlayerResigned(4), PlayerLost(5),
    PromptPlayerToRollDice(6), DiceRoll(7), Move(8), PassedStartSquare(9), LandedOnStartSquare(10),
    GoToJail(11), PromptPlayerToBuyAsset(12), PromptPlayerToBuyHouse(13), AssetBoughtMessage(14), HouseBoughtMessage(15),
    SurpriseCard(16), WarrantCard(17), GetOutOfJailCard(18), Payment(19), PlayerUsedJailCard(20);
    private int code;

    public int getCode() {
        return code;
    }

    private EventTypes(int code) {
        this.code = code;
    }
}