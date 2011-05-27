package src.client;

import comm.Event;
import comm.MonopolyResult;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author blecherl
 */
public class Server {

    private static Server instance;
    private BackendService backendService;
    
    private ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<Event>();

    static {
        instance = new Server();
    }

    private Server() {
        backendService = new BackendService();
    }

    public static Server getInstance() {
        return instance;
    }
    
    public synchronized void addEventsToQueue(Collection<? extends Event> events){
        eventQueue.addAll(events);
    }
    
    public synchronized  Event popEventFromQueue(){
        return eventQueue.remove();
    }
    
    public synchronized Event peekIntoEventQueue(){
        return eventQueue.peek();
    }
    
    public synchronized boolean isEventQueueEmpty(){
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
    
    public List<String> getActiveGame(){
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
    
    public MonopolyResult setDiceRollResults (int playerID, int eventID, int dice1, int dice2){
        return backendService.setDiceRollResults(playerID, eventID, dice1, dice2);
    }
    
    public MonopolyResult resign (int playerID){
        return backendService.resign(playerID);
    }
}