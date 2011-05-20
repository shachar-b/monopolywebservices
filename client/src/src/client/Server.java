package src.client;

import comm.Event;
import comm.MonopolyResult;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author blecherl
 */
public class Server {

    private static Server instance;
    private BackendService backendService;

    static {
        instance = new Server();
    }

    private Server() {
        backendService = new BackendService();
    }

    public static Server getInstance() {
        return instance;
    }

    public Timer startPolling(String timerName, TimerTask task, int delay, int period) {
        Timer timer = new Timer(timerName, true);
        timer.scheduleAtFixedRate(task, delay * 1000, period * 1000);
        return timer;
    }

    public List<String> getWaitingGames() {
        return backendService.getWaitingGames();
    }

    public List<Event> getAllEvents(int index) {
        return backendService.getAllEvents(index);
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
}