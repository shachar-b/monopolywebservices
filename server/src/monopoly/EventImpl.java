/**
 * this is an implemetion for Event
 */
package monopoly;

/**
 * 
 * @author omer shenhar and shachar butnero
 */
public class EventImpl implements Event {

    private String gameName = "";
    private int eventID = 0;
    private int timeoutCount = 0;
    private int eventType = 0;
    private String playerName = "";
    private String eventMessage = "";
    private int boardSquareID = 0;
    private int firstDiceResult = 0;
    private int secondDiceResult = 0;
    private boolean playerMoved = false;
    private int nextBoardSquareID = 0;
    private boolean paymentToOrFromTreasury = false;
    private boolean paymentFromUser = false;
    public String paymentToPlayerName = "";
    private int paymentAmount = 0;

    /**
     * private EventImpl(String gameName, EventTypes eventType, String eventMessage)
     * a constructor for this class
     * private
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     */
    private EventImpl(String gameName, EventTypes eventType, String eventMessage) {
        this.gameName = gameName;
        this.eventID = EventImpl.generateEventId();
        this.eventType = eventType.getCode();
        this.eventMessage = eventMessage;
    }

    /**
     * 
     * @return a unique eventID
     */
    private static int generateEventId() {

        return Monopoly.generateEventId();
    }

    /*
     * Events are divided into groups, according to the relevant fields needed for each event type.
     * GroupA : Game Start,Game Over
     * GroupB : Game Winner,Player Resigned,Player Lost,Passed Start Square,
    Landed On Start Square,Go to Jail,Surprise Card,Warrant Card,
    Get Out Of Jail Card,Player used Get out of jail card
     * GroupC : Prompt Player To Buy Asset,Prompt Player To Buy House
     * GroupD : Asset Bought Message,House bought message
     *
     * And singleton groups:
     *      Prompt Player To Roll Dice
     *      Dice Roll
     *      Move
     *      Payment
     *
     */
    /**GroupA : Game Start,Game Over
     * 
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @return  an event of GroupA : Game Start,Game Over
     */
    public static Event createNewGroupA(String gameName, EventTypes eventType, String eventMessage) {
        return new EventImpl(gameName, eventType, eventMessage);
    }

    /**
     * GroupB : Game Winner,Player Resigned,Player Lost,Passed Start Square,
     * Landed On Start Square,Go to Jail,Surprise Card,Warrant Card,
     *  Get Out Of Jail Card,Player used Get out of jail card
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @return a group B eventIMPL
     */
    public static Event createNewGroupB(String gameName, EventTypes eventType, String eventMessage, String playerName) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        return generatedEvent;
    }

    /**
     * GroupC : Prompt Player To Buy Asset,Prompt Player To Buy House
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param TimeOutCount- time out in seconds
     * @param boardSquareID - a number from 0 to 36
     * @return a group c event
     */
    public static Event createNewGroupC(String gameName, EventTypes eventType, String eventMessage, String playerName, int TimeOutCount, int boardSquareID) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.timeoutCount = TimeOutCount;
        generatedEvent.boardSquareID = boardSquareID;
        return generatedEvent;
    }

    /**
     * GroupD : Asset Bought Message,House bought message
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param boardSqureID
     * @return a group D event
     */
    public static Event createNewGroupD(String gameName, EventTypes eventType, String eventMessage, String playerName, int boardSqureID) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.boardSquareID = boardSqureID;
        return generatedEvent;
    }

    /**
     * PromptRollEvent
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param timeoutCount- timeout in seconeds
     * @return a PromptRoll Event 
     */
    public static Event createNewPromptRollEvent(String gameName, EventTypes eventType, String eventMessage, String playerName, int timeoutCount) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.timeoutCount = timeoutCount;
        return generatedEvent;
    }

    /**
     * 
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param firstRes- a number from 1 to 6
     * @param secondRes - a number from 1 to 6
     * @return a new event to infrom of dice roll
     */
    public static Event createNewDiceRollEvent(String gameName, EventTypes eventType, String eventMessage, String playerName, int firstRes, int secondRes) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.firstDiceResult = firstRes;
        generatedEvent.secondDiceResult = secondRes;
        return generatedEvent;
    }

    /**
     * 
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param playerMoved- a boolean -true if player moved false otherwise
     * @param boardSquareID-the origan square
     * @param nextBoardSquareID- the dest square
     * @return a new move event
     */
    public static Event createNewMoveEvent(String gameName, EventTypes eventType, String eventMessage, String playerName, boolean playerMoved, int boardSquareID, int nextBoardSquareID) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.playerMoved = playerMoved;
        generatedEvent.boardSquareID = boardSquareID;
        generatedEvent.nextBoardSquareID = nextBoardSquareID;
        return generatedEvent;
    }

    /**
     * 
     * @param gameName -the name of the running game
     * @param eventType- the type of the event
     * @param eventMessage - a message to be sent
     * @param playerName - the name of the player for whome the massage is ment
     * @param paymentToOrFromTreasury- boolean is one of the sides is trusery
     * @param paymentFromUser= is the giver a player
     * @param paymentToPlayerName-the name of the recipiet
     * @param amount- the amount to give
     * @return a new payment event
     */
    public static Event createNewPaymentEvent(String gameName, EventTypes eventType, String eventMessage, String playerName, boolean paymentToOrFromTreasury, boolean paymentFromUser, String paymentToPlayerName, int amount) {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.paymentToOrFromTreasury = paymentToOrFromTreasury;
        generatedEvent.paymentFromUser = paymentFromUser;
        generatedEvent.paymentToPlayerName = paymentToPlayerName;
        generatedEvent.paymentAmount = amount;
        return generatedEvent;
    }

    /**
     * 
     * @return the name of the game
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * 
     * @return the event id
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * 
     * @return the timeout
     */
    public int getTimeoutCount() {
        return timeoutCount;
    }

    /**
     * 
     * @return the event type as int
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * 
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * 
     * @return the event message
     */
    public String getEventMessage() {
        return eventMessage;
    }

    /**
     * 
     * @return 
     */
    public int getBoardSquareID() {
        return boardSquareID;
    }

    /**
     * 
     * @return 
     */
    public int getFirstDiceResult() {
        return firstDiceResult;
    }

    /**
     * 
     * @return 
     */
    public int getSecondDiceResult() {
        return secondDiceResult;
    }

    /**
     * 
     * @return 
     */
    public boolean isPlayerMoved() {
        return playerMoved;
    }

    /**
     * 
     * @return 
     */
    public int getNextBoardSquareID() {
        return nextBoardSquareID;
    }

    /**
     * 
     * @return 
     */
    public boolean isPaymentToOrFromTreasury() {
        return paymentToOrFromTreasury;
    }

    /**
     * 
     * @return 
     */
    public boolean isPaymemtFromUser() {
        return paymentFromUser;
    }

    /**
     * 
     * @return 
     */
    public String getPaymentToPlayerName() {
        return paymentToPlayerName;
    }

    /**
     * 
     * @return 
     */
    public int getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @author  omer and shachar
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
}
