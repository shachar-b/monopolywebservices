package src.client;

import comm.Event;
import comm.MonopolyResult;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.ws.http.HTTPException;

/**
 *  An encapsulation layer for the backend service. Allows for the definition of 
 * a game server, and managing the relevant game functions on it.
 * @author blecherl, modified by Shachar Butnaro and Omer Shenhar
 */
public class Server {

    private static Server instance = null;
    private BackendService backendService;
    private ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<Event>();
    private boolean hasConnetion = false;
    private final int SERVER_INITIAL_AMOUNT=1500;

    static {
        instance = new Server();
    }

    private Server(String url) {
        hasConnetion = setAdderss(url);
    }

    private Server() {
        //Creation of a localhost server is being managed by setAddress in the backend Service.
    }

    public boolean setAdderss(String url) {
        try {
            if (url.equals("")) {
                backendService = new BackendService();
            } else {
                backendService = new BackendService(url);
            }

            backendService.testConnection();
        } catch (HTTPException e) {
            return false;
        }
       catch (ConnectException e) {
            return false;
        } 
        catch (MalformedURLException ex) {
                    return false;
        }
        return true;

    }

    public static Server getInstance() {
        return instance;
    }

    public synchronized void addEventsToQueue(Collection<? extends Event> events) {
        eventQueue.addAll(events);
    }

    public synchronized Event popEventFromQueue() {
        return eventQueue.remove();
    }

    public synchronized Event peekIntoEventQueue() {
        return eventQueue.peek();
    }

    public synchronized boolean isEventQueueEmpty() {
        return eventQueue.isEmpty();
    }

    public Timer startPolling(String timerName, TimerTask task, int delay, int period) {
        Timer timer = new Timer(timerName, true);
        timer.scheduleAtFixedRate(task, delay * 1000, period * 1000);
        return timer;
    }

    public List<String> getWaitingGames() {
        return backendService.getWaitingGames();
    }

    public List<String> getActiveGame() {
        return backendService.getActiveGames();
    }

    public List<Event> getAllEvents(int index) {
        return backendService.getAllEvents(index);
    }

    public String getGameBoardXML() {
        return backendService.getGameBoardXML();
    }

    public boolean startNewGame(String name, int humanPlayers, int computerPlayers, boolean useAutomaticDiceRollCheckBox) {
        return backendService.startGame(name, humanPlayers, computerPlayers, useAutomaticDiceRollCheckBox);
    }

    public List<PlayerDetails> getPlayersDetails(String gameName) {
        return backendService.getPlayersDetails(gameName);
    }

    public int joinPlayer(String gameName, String playerName) {
        return backendService.joinGame(gameName, playerName);
    }

    public GameDetails getGameDetails(String gameName) {
        return backendService.getGameDetails(gameName);
    }

    public MonopolyResult buy(int playerID, int eventID, boolean buyDecision) {
        return backendService.buy(playerID, eventID, buyDecision);
    }

    public MonopolyResult setDiceRollResults(int playerID, int eventID, int dice1, int dice2) {
        return backendService.setDiceRollResults(playerID, eventID, dice1, dice2);
    }

    public MonopolyResult resign(int playerID) {
        return backendService.resign(playerID);
    }

    public boolean hasConnection() {
        return hasConnetion;
    }

    /**
     * As the server continues autonomously and the client catches up, there might be changes
     * in the players' balance, prior to the GUI opening and the game starting on the client side.
     * We have consulted with Amir Kirsh, and he confirmed it is a design flaw, therefore, we assume
     * that the starting balance is 1500 (can be changed by the appropriate const), until a change
     * in the design occurs (wsdl and everything) sometime in the future.
     * @return The player's initial balance.
     */
    public int getInitialBalance() {
        //Doesnt consult Backend.
        return SERVER_INITIAL_AMOUNT;
    }
}