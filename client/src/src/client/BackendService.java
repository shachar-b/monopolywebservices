package src.client;

import comm.Event;
import comm.GameDetailsResult;
import comm.GetActiveGamesResponse;
import comm.GetWaitingGamesResponse;
import comm.IDResult;
import comm.MonopolyGame;
import comm.MonopolyGamePortType;
import comm.MonopolyResult;
import comm.PlayerDetailsResult;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author blecherl
 */
public class BackendService {

    private MonopolyGamePortType monopolyGamePortType;

    public BackendService()throws ConnectException {
        monopolyGamePortType = new MonopolyGame().getMonopolyGameHttpSoap11Endpoint();   
    }
    
    public BackendService(String url) throws MalformedURLException,ConnectException {
            this.monopolyGamePortType = new MonopolyGame(new URL(url)).getMonopolyGameHttpSoap11Endpoint();
    }

    public String getGameBoardXML() {
        return monopolyGamePortType.getGameBoardXML().getReturn().getValue();
    }

    public String getGameBoardSchema() {
        return monopolyGamePortType.getGameBoardSchema().getReturn().getValue();
    }

    public List<Event> getAllEvents(int index) {
        return monopolyGamePortType.getAllEvents(index).getResults();
    }

    public boolean startGame(String name, int humanPlayers, int computerPlayers, boolean useAutomaticDiceRollCheckBox) {
        MonopolyResult result = monopolyGamePortType.startGame(name, humanPlayers, computerPlayers, useAutomaticDiceRollCheckBox);
        return !hasError(result);
    }

    public List<String> getWaitingGames() {
        GetWaitingGamesResponse result = monopolyGamePortType.getWaitingGames();
        return result != null ? result.getReturn() : Collections.EMPTY_LIST;
    }

    public int joinGame(String gameName, String playerName) {
        IDResult result = monopolyGamePortType.joinGame(gameName, playerName);
        if (hasError(result)) {
            return -1;
        } else {
            return result.getResult();
        }
        
    }

    private boolean hasError(MonopolyResult monopolyResult) {
        if (monopolyResult == null || monopolyResult.isError()) {
            JOptionPane.showMessageDialog(null, monopolyResult.getErrorMessage().getValue(), "Server Communication Error", JOptionPane.ERROR_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public List<PlayerDetails> getPlayersDetails(String gameName) {
        List<PlayerDetails> players = new LinkedList<PlayerDetails>();
        PlayerDetailsResult result = monopolyGamePortType.getPlayersDetails(gameName);
        if (hasError(result)) {
            return Collections.EMPTY_LIST;
        } else {
            for (int index = 0; index < result.getNames().size(); index++) {
                players.add(new PlayerDetails(
                        result.getNames().get(index),
                        result.getIsHumans().get(index),
                        result.getIsActive().get(index),
                        result.getMoney().get(index)));
            }
            return players;
        }

    }

    public GameDetails getGameDetails(String gameName) {
        GameDetailsResult result = monopolyGamePortType.getGameDetails(gameName);
        if (hasError(result)) {
            return null;
        } else {
            return new GameDetails(
                    gameName,
                    result.getTotalHumanPlayers(),
                    result.getJoinedHumanPlayers(),
                    result.getTotalComputerPlayers(),
                    result.getStatus().getValue());
        }
    }

    public MonopolyResult buy(int playerID, int eventID, boolean buyDecision) {
        return monopolyGamePortType.buy(playerID, eventID, buyDecision);
    }

    public List<String> getActiveGames() {
        GetActiveGamesResponse result = monopolyGamePortType.getActiveGames();
        return result != null ? result.getReturn() : Collections.EMPTY_LIST;
    }

    public MonopolyResult setDiceRollResults(int playerID, int eventID, int dice1, int dice2) {
        MonopolyResult result = monopolyGamePortType.setDiceRollResults(playerID, eventID, dice1, dice2);
        return result;
    }

    public MonopolyResult resign(int playerID) {
        MonopolyResult result = monopolyGamePortType.resign(playerID);
        return result;
    }

    void testConnection() throws ConnectException {
        monopolyGamePortType.getWaitingGames();
    }
}
