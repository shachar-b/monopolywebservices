package monopoly;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import monopoly.EventImpl.EventTypes;

import players.Player;
import squares.JailSlashFreePassSquare;
import squares.Square;
import assets.Asset;
import assets.City;
import cards.ShaffledDeck;
import java.util.Collections;
import squares.ParkingSquare;

/**
 * public class Monopoly
 * this is the monopoly game
 * @author Omer Shenhar & Shachar Butnaro
 *
 */
public class Monopoly {

    private static ArrayList<Event> eventList = new ArrayList<Event>();
    private ArrayList<Player> quitters = new ArrayList<Player>();

    static int generateEventId() {
        return eventList.size();
    }
    private String gameName;
    private ArrayList<Player> gamePlayers;
    private ShaffledDeck surprise = new ShaffledDeck();
    private ShaffledDeck callUp = new ShaffledDeck();
    private ArrayList<Square> gameBoard;
    private boolean stopGame = false;
    private int playerIndex = 0;
    private int roundNumber = 1;
    private Player currentActivePlayer;
    private Square currentPlayerSquare;
    private int state = 0;
    private boolean gameRunning = false;
    public final int START = 0;
    public final int DIE = 1;
    public final int ENDTURN = 2;
    private final boolean useAutoDiceRoll;

    public Monopoly(String gameName, ArrayList<Player> players, boolean useAutoDiceRoll) {
        //eventList = new ArrayList<Event>();
        this.gameName = gameName;
        gamePlayers = new ArrayList<Player>(players);
        this.useAutoDiceRoll = useAutoDiceRoll;
        init();
    }

    /**
     * method void init (package protected)
     * this method initializes the game data members
     */
    private void init() {
        MonopolyInitilizer gameInitializer = new XMLInitializer();
        // init gameBoard
        gameBoard = gameInitializer.initBoard();
        // init Dice
        Dice.initGenerator();
        // set player's order
        Collections.shuffle(gamePlayers);
        //make a copy of the player list in order to keep the way we implemnted the game (this keeps inactive players)
        currentActivePlayer = gamePlayers.get(0);
        //init CARDS
        surprise = gameInitializer.initSurprise();
        callUp = gameInitializer.initCallUp();
    }

    public String getGameName() {
        return gameName;
    }

    /**
     * method public void play()
     * this methods starts the first round.
     */
    public void play() {
        eventDispatch(currentActivePlayer.getID(), "start");
    }

    private void endGameSequence() {
        TimeOutTasks.stopTimer();
        String winner = getCurrentActivePlayer().getName();
        Event gameOver = EventImpl.createNewGroupA(gameName, EventImpl.EventTypes.GameOver, "Game over, thanks for playing.");
        addEvent(gameOver);
        Event gameWinner = EventImpl.createNewGroupB(gameName, EventTypes.GameWinner, "Player " + winner + " is the undisputed winner! Heap-heap-array!", winner);
        addEvent(gameWinner);

        //clear game settings and variables by nullifying the current game
        MonopolyGame.gameManagerHandle.removeGame(gameName);
        GameManager.currentGame = null;
    }

    /**
     * private void doComputerRound()
     * Computers are controlled by a state machine, on a separate Thread.
     * The run() function of the Thread directs the computer player to take appropriate
     * actions, depending on the current state (depicted by the field "state".
     * The function also resets the state when the player is done.
     */
    private void doRound() {
        currentPlayerSquare = gameBoard.get(currentActivePlayer.getCurrentPosition());
        switch (state) {
            case START:
                state++;//next state is roll die

                for (int i = 0; i < quitters.size(); i++) { //Handle quitters
                    Player quitter = quitters.get(i);
                    if (currentActivePlayer == quitter) {
                        quitters.remove(i);
                        forfeit();
                        return;
                    }
                }

                if (currentPlayerSquare instanceof JailSlashFreePassSquare) {
                    if (currentActivePlayer.hasGetOutOfJailFreeCard()
                            && !currentPlayerSquare.shouldPlayerMove(currentActivePlayer)) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException("state machine problem");
                        }
                        state = ENDTURN;//nothing more to do in this turn
                        eventDispatch(currentActivePlayer.getID(), "getOutOfJail");
                        break;

                    }
                } else {
                    if (currentPlayerSquare instanceof ParkingSquare) {
                        if (!currentPlayerSquare.shouldPlayerMove(currentActivePlayer)) {
                            currentActivePlayer.setGoOnNextTurn(true);
                            state = ENDTURN;
                            eventDispatch(currentActivePlayer.getID(), "endTurn");
                            break;
                        }
                    }

                }
            case DIE:
                state++;//next state is 2- (buy decesions are made outside the state mech
                if (isAutoDiceRoll() || !currentActivePlayer.isHuman())//computer isnt promped for die
                {
                    if (currentPlayerSquare instanceof JailSlashFreePassSquare || currentPlayerSquare.shouldPlayerMove(currentActivePlayer)) {//dont do it only on parking- if GOJC was used this wont be reached
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException("state machine problem");
                        }
                        eventDispatch(currentActivePlayer.getID(), "throwDie");
                        break;
                    }
                } else {
                    TimeOutTasks.StartTimer(currentActivePlayer);
                    Monopoly.addEvent(EventImpl.createNewPromptRollEvent(gameName, EventImpl.EventTypes.PromptPlayerToRollDice, "please roll the die or die :)", currentActivePlayer.getName(), GameManager.TIMEOUT_IN_SECONDS));
                    break;
                }

            case ENDTURN://case end turn
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("state machine problem");
                }
                state = START;//restart state machine for next player
                eventDispatch(currentActivePlayer.getID(), "endTurn");
                break;
        }
    }

    public static int getCurrentEventID() {
        if (eventList.isEmpty()) {
            return -1;
        } else {
            return eventList.get(eventList.size() - 1).getEventID();
        }
    }

    public static boolean isLastEventID(int ID) {
        if (Monopoly.eventList.isEmpty()) {
            return false;
        } else {
            EventImpl lastEvent = (EventImpl) Monopoly.eventList.get(Monopoly.eventList.size() - 1);
            if (ID != lastEvent.getEventID()) {
                return false;
            } else {
                return true;
            }
        }
    }

    static boolean isLastEventIDType(EventTypes type) {
        if (Monopoly.eventList.isEmpty()) {
            return false;
        } else {
            EventImpl lastEvent = (EventImpl) Monopoly.eventList.get(Monopoly.eventList.size() - 1);
            if (type.getCode() != lastEvent.getEventType()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static void addEvent(Event e) {
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(Monopoly.class.getName()).log(Level.SEVERE, null, ex);
        }
        eventList.add(e);
    }

    public static Event getEventAt(int id) {
        return eventList.get(id);
    }

    public static ArrayList<Event> getEventsFrom(int id) {
        ArrayList<Event> returnVal = new ArrayList<Event>();
        for (int i = id; i < eventList.size(); i++) {
            returnVal.add(eventList.get(i));
        }

        return returnVal;
    }

    /**
     * method private int getActualNumPlayers()
     * @return the actual number of players at the time of the request
     */
    private int getActualNumPlayers() {
        return getGamePlayers().size();
    }

    /**
     * public int getRoundNumber()
     * @return an integer representing the current round number.
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Method private void movePlayer(Player player, int currDieSum, boolean getBonus)
     * this method moves a player on the game board
     * @param player - a non null player who is in the game to be moved
     * @param currDieSum - a number from 2 to 12 - how many steps should the player take
     * @param getBonus - true if the player should get a bonus if he goes thru start. false otherwise
     */
    private void movePlayer(Player player, int currDieSum, boolean getBonus) {
        int playerPos = player.getCurrentPosition();
        playerPos += currDieSum;//currDieSum;
        if (playerPos >= GameManager.NUMBER_OF_SQUARES) {
            if (getBonus) {
                addEvent(EventImpl.createNewGroupB(gameName, EventImpl.EventTypes.PassedStartSquare, "passed start square", player.getName()));
                player.ChangeBalance(GameManager.START_PASS_BONUS, GameManager.ADD, true, false);
            }
            playerPos = playerPos % GameManager.NUMBER_OF_SQUARES;
        }
        player.setCurrentPosition(playerPos);
        String message = "player " + player.getName() + " moved from " + GameManager.currentGame.getGameBoard().get(player.getLastKnownPosition()).getName()
                + " to " + GameManager.currentGame.getGameBoard().get(player.getCurrentPosition()).getName();
        addEvent(EventImpl.createNewMoveEvent(gameName, EventImpl.EventTypes.Move,
                message, player.getName(), true, player.getLastKnownPosition(), player.getCurrentPosition()));
        gameBoard.get(playerPos).playerArrived(player);
    }

    /**
     * method public void gotoNextSquareOfType(Player player, Class<? extends Square> type, boolean getBonus)
     * this method moves player to next square of type type;
     * @param player - a non null active player to be moved
     * @param type - a class which extends Square
     * @param getBonus -  true if the player should get a bonus if he goes thru start. false otherwise
     */
    public void gotoNextSquareOfType(Player player, Class<? extends Square> type, boolean getBonus) {
        int currpos = (player.getCurrentPosition() + 1) % GameManager.NUMBER_OF_SQUARES;
        int numOfSteps = 1;
        while (!gameBoard.get(currpos).getClass().getSimpleName().equals(type.getSimpleName())) {
            currpos = (currpos + 1) % GameManager.NUMBER_OF_SQUARES;
            numOfSteps++;
        }

        movePlayer(player, numOfSteps, getBonus);
    }

    /**
     * method public void removePlayerFromGame(Player player)
     * this method safely removes a player from game (returns all his assets to treasury ,demolish houses and so on)
     * @param player a non null active player to be removed
     */
    public void removePlayerFromGame(Player player) {
        if (player == null) {
            endTurn();
            return; //Do nothing, player has already been removed, this is just the ghost of a timer.
        }

        ArrayList<Asset> assetList = player.getAssetList();
        String message;
        EventImpl.EventTypes type;
        while (!assetList.isEmpty())//remove ownership from all remaining assets
        {
            assetList.get(0).setOwner(GameManager.assetKeeper);//set owner removes itself from the list
        }
        if (player.getGetOutOfJailFreeCardPlaceHolder() != null) {
            surprise.add(player.getGetOutOfJailFreeCardPlaceHolder());
        }
        gamePlayers.remove(player);

        if (player.getBalance() == Player.BANKRUPT) {
            type = EventTypes.PlayerLost;
            message = player.getName() + " has lost due to the fact he had no money";
        } else {
            type = EventTypes.PlayerResigned;;
            message = player.getName() + " has left the game due to the fact he choose forfeit or did not respond to the game requests in a timely manner";
        }
        Monopoly.addEvent(EventImpl.createNewGroupB(gameName, type, message, player.getName()));

        endTurn();
    }

    /**
     * method public ArrayList<Player> getGamePlayers()
     * @return a list of  all players in the game
     */
    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    /**
     * method public ShaffledDeck getSurprise()
     * @return the surprise Deck
     */
    public ShaffledDeck getSurprise() {
        return surprise;
    }

    /**
     * method public ShaffledDeck getCallUp()
     * @return the CallUp Deck
     */
    public ShaffledDeck getCallUp() {
        return callUp;
    }

    /**
     * public ArrayList<Square> getGameBoard()
     * @return an ArrayList of Squares representing the game board.
     */
    public ArrayList<Square> getGameBoard() {
        return gameBoard;
    }

    /**
     * method private void endTurn()
     * Ends the current turn of the active player.
     */
    private void endTurn() {
        playerIndex++;
        if (playerIndex >= gamePlayers.size()) {
            playerIndex = 0;
            roundNumber++;
        }
        currentActivePlayer = gamePlayers.get(playerIndex);

        if (getActualNumPlayers() != 1) {
            eventDispatch(currentActivePlayer.getID(), "start");
        } else {
            endGameSequence();
            stopGame = true;
        }
    }

    /**
     * method private void thrownDie()
     * This method handles the operations neccesary after throwing the die.
     */
    private void throwDie() {

        //note that when parked player wont get here!
        int[] result = Dice.rollDie();
        throwDie(result[0], result[1]);
    }

    void throwDie(int die1, int die2) {
        String message = "Rolled: " + die1 + "," + die2 + ".";
        String playerName = GameManager.currentGame.getCurrentActivePlayer().getName();
        EventImpl.EventTypes typeCode = EventImpl.EventTypes.DiceRoll;
        Monopoly.addEvent(EventImpl.createNewDiceRollEvent(GameManager.currentGame.getGameName(), typeCode, message, playerName, die1, die2));

        if (currentPlayerSquare instanceof JailSlashFreePassSquare && !currentPlayerSquare.shouldPlayerMove(currentActivePlayer)) {
            boolean hasDouble = die1 == die2;
            ((JailSlashFreePassSquare) currentPlayerSquare).release(currentActivePlayer, hasDouble);
            eventDispatch(currentActivePlayer.getID(), "endTurn");
        } else {
            int dieSum = die1 + die2;
            movePlayer(currentActivePlayer, dieSum, true);
        }
    }

    /**
     * method private void buyHouse()
     * This method activates the buyHouse method in the currentPlayerSquare
     */
    private void buyHouse() {
        ((City) currentPlayerSquare).BuyHouse(getCurrentActivePlayer());
        eventDispatch(currentActivePlayer.getID(), "endTurn");
    }

    /**
     * method private void useGetOutOfJail()
     * This method activates the get out of jail method in a JailSlashFreePassSquare.
     */
    private void useGetOutOfJail() {
        String message = "player " + currentActivePlayer.getName() + " used a card to get out of jail";
        addEvent(EventImpl.createNewGroupB(gameName, EventImpl.EventTypes.PlayerUsedJailCard, message, currentActivePlayer.getName()));
        ((JailSlashFreePassSquare) currentPlayerSquare).playerUsesGetOutOfJailCard(getCurrentActivePlayer());
    }

    /**
     * method private void buyAsset()
     * This method invokes the buyAsset method in an Asset.
     */
    private void buyAsset() {
        ((Asset) currentPlayerSquare).buyAsset(getCurrentActivePlayer());
        eventDispatch(currentActivePlayer.getID(), "endTurn");
    }

    /**
     * method public void signalGameRunning()
     * Signifies that a game has been started - e.g. that Play() has been called.
     */
    public void signalGameRunning() {
        gameRunning = true;
    }

    /**
     * @return true IFF a game is running - e.g. that play() has been called.
     */
    public boolean getGameRunning() {
        return gameRunning;
    }

    /**
     * method public void eventDispatch(String message)
     * @param message a String depicting the event being dispatched by the player.
     * Note that for any illegal message nothing would happen.
     */
    public void eventDispatch(int playerID, String message) {
        currentActivePlayer = gamePlayers.get(playerIndex);
        currentPlayerSquare = gameBoard.get(getCurrentActivePlayer().getCurrentPosition());
        if (playerID != currentActivePlayer.getID()) {
            if (message.equals("forfeit")) {
                Player quitter = null;
                for (Player p : gamePlayers) {
                    if (p.getID() == playerID) {
                        quitter = p;
                    }
                }
                quitters.add(quitter);
            } else {
                //do some error event becuse player cheated or ignore
            }
        } else {
            if (!stopGame && message.equals("start")) {
                state = START;
                doRound();
            } else if (message.equals("forfeit")) {
                TimeOutTasks.stopTimer(); //In the event that a player resigned after a buy prompt was issued, but before he saw it on the GUI, kill the waiting timer.
                quitters.add(currentActivePlayer);
                endTurn();
            } else if (message.equals("endTurn")) {
                state = START;
                endTurn();
            } else if (message.equals("getOutOfJail")) {
                state = ENDTURN;
                useGetOutOfJail();
            } else if (message.equals("buyHouse")) {
                buyHouse();

            } else if (message.equals("buyAsset")) {
                buyAsset();

            } else if (message.equals("throwDie")) {
                throwDie();
                //dont go on till someone else told you so
            }
        }
    }

    /**
     * @return the currentActivePlayer
     */
    public Player getCurrentActivePlayer() {
        return currentActivePlayer;
    }

    /**
     * method private void forfeit()
     * This method removes the current player from the game.
     * As a by-product, ends his turn.
     */
    private void forfeit() {
        TimeOutTasks.stopTimer();
        playerIndex--;
        removePlayerFromGame(getCurrentActivePlayer());
    }

    public void buyWhatYouAreSittingOn() {
        Asset curr = (Asset) gameBoard.get(currentActivePlayer.getCurrentPosition());
        if (curr.getOwner() == GameManager.assetKeeper) {
            eventDispatch(currentActivePlayer.getID(), "buyAsset");
        } else {
            eventDispatch(currentActivePlayer.getID(), "buyHouse");
        }
    }

    public boolean isLegalBuyOP() {
        Square curr = gameBoard.get(currentActivePlayer.getCurrentPosition());
        boolean retVal = false;
        if (curr instanceof Asset && ((Asset) curr).getOwner() == GameManager.assetKeeper) {
            retVal = true;
        } else if (curr instanceof City && ((City) curr).canHouseBeBuilt(currentActivePlayer)) {
            retVal = true;
        }
        return retVal;
    }

    public boolean isAutoDiceRoll() {
        return useAutoDiceRoll;
    }

    boolean isValidPlayerID(int ID) {
        for (Player p : gamePlayers) {
            if (p.isActive() && p.getID() == ID) {
                return true;
            }
        }
        return false;
    }
}
