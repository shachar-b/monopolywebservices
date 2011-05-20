/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monopoly;

/**
 *
 * @author blecherl
 */
public class EventImpl implements Event{



    
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

    private EventImpl(String gameName,EventTypes eventType,String eventMessage)
    {
        this.gameName = gameName;
        this.eventID = EventImpl.generateEventId();
        this.eventType = eventType.getCode();
        this.eventMessage = eventMessage;
    }

    
    private static synchronized  int generateEventId()
    {

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

    public static Event createNewGroupA(String gameName,EventTypes eventType,String eventMessage)
    {
        return new EventImpl(gameName, eventType, eventMessage);
    }

    public static Event createNewGroupB(String gameName,EventTypes eventType,String eventMessage,String playerName)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        return generatedEvent;
    }

    public static Event createNewGroupC(String gameName,EventTypes eventType
            ,String eventMessage,String playerName,int TimeOutCount, int boardSquareID)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.timeoutCount = TimeOutCount;
        generatedEvent.boardSquareID =  boardSquareID;
        return generatedEvent;
    }

    public static Event createNewGroupD(String gameName,EventTypes eventType
            ,String eventMessage,String playerName, int boardSqureID)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.boardSquareID = boardSqureID;
        return generatedEvent;
    }

    public static Event createNewPromptRollEvent(String gameName,EventTypes eventType
            ,String eventMessage,String playerName, int timeoutCount)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.timeoutCount = timeoutCount;
        return generatedEvent;
    }

    public static Event createNewDiceRollEvent(String gameName,EventTypes eventType
            ,String eventMessage,String playerName, int firstRes, int secondRes)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.firstDiceResult = firstRes;
        generatedEvent.secondDiceResult = secondRes;
        return generatedEvent;
    }

    public static Event createNewMoveEvent(String gameName,EventTypes eventType
            ,String eventMessage,String playerName,boolean playerMoved, int boardSquareID, int nextBoardSquareID)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.playerMoved = playerMoved;
        generatedEvent.boardSquareID = boardSquareID;
        generatedEvent.nextBoardSquareID = nextBoardSquareID;
        return generatedEvent;
    }

    public static Event createNewPaymentEvent(String gameName,EventTypes eventType
            ,String eventMessage,String playerName,boolean paymentToOrFromTreasury
            ,boolean paymentFromUser,String paymentToPlayerName,int amount)
    {
        EventImpl generatedEvent = new EventImpl(gameName, eventType, eventMessage);
        generatedEvent.playerName = playerName;
        generatedEvent.paymentToOrFromTreasury = paymentToOrFromTreasury;
        generatedEvent.paymentFromUser = paymentFromUser;
        generatedEvent.paymentToPlayerName = paymentToPlayerName;
        generatedEvent.paymentAmount = amount;
        return generatedEvent;
    }

    public String getGameName() {
        return gameName;
    }
    
    public int getEventID() {
        return eventID;
    }
    
    public int getTimeoutCount() {
        return timeoutCount;
    }
    
    public int getEventType() {
        return eventType;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public String getEventMessage() {
        return eventMessage;
    }
    
    public int getBoardSquareID() {
        return boardSquareID;
    }
    
    public int getFirstDiceResult() {
        return firstDiceResult;
    }
    
    public int getSecondDiceResult() {
        return secondDiceResult;
    }
    
    public boolean isPlayerMoved() {
        return playerMoved;
    }
    
    public int getNextBoardSquareID() {
        return nextBoardSquareID;
    }
    
    public boolean isPaymentToOrFromTreasury() {
        return paymentToOrFromTreasury;
    }
    
    public boolean isPaymemtFromUser() {
        return paymentFromUser;
    }
    
    public String getPaymentToPlayerName() {
        return paymentToPlayerName;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

        public enum EventTypes
  {

        GameStart(1),GameOver(2),GameWinner(3),PlayerResigned(4),PlayerLost(5),
        PromptPlayerToRollDice (6),DiceRoll(7),Move(8),PassedStartSquare(9),LandedOnStartSquare(10),
        GoToJail(11),PromptPlayerToBuyAsset(12),PromptPlayerToBuyHouse(13),AssetBoughtMessage(14),HouseBoughtMessage(15),
        SurpriseCard(16),WarrantCard(17),GetOutOfJailCard(18),Payment(19),PlayerUsedJailCard(20);

        private int code;

        public  int getCode(){
            return code;
        }

        private EventTypes(int code) {
            this.code = code;
        }
    }

}
