/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gamesmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import monopoly.EventImpl;
import monopoly.GameManager;
import monopoly.Monopoly;
import players.ComputerPlayer;
import players.HumanPlayer;
import players.Player;

/**
 *
 * @author blecherl
 */
public class MonopolyGameManager {

    GameDetails game = null;

    //Removed constructor - now using empty default.
    public String removeGame(String gameName) {
        //ToDo : Remove this function
        String returnVal = "";
        if (isGameExists(gameName)) {
            game = null;
            returnVal = "Removed game " + gameName;
        } else if (game == null) {
            returnVal = "No game running!";
        } else {
            returnVal = "Game " + gameName + "not found, game " + game.gameName + "is running!";
        }
        return returnVal;

    }

    public boolean addGame(String gameName, int humanPlayers, int computerizedPlayers, boolean useAutomaticDiceRoll) {
        if (getNumberOfGames() > 0) {
            return false;
        } else {
            game = new GameDetails(gameName, humanPlayers, computerizedPlayers, useAutomaticDiceRoll);
            return true;
        }
    }

    public int getNumberOfGames() {
        return game == null ? 0 : 1;
    }

    private boolean isGameExists(String gameName) {
        if (game != null) {
            if (game.gameName.equals(gameName)) {
                return true;
            }
        }

        return false;
    }

    public boolean isGameStarted(String gameName) {
        return isGameExists(gameName) && game.isGameStarted();
    }

    public boolean isGameActive(String gameName) {
        return isGameExists(gameName) && game.isGameActive();
    }

    public int joinPlayer(String gameName, String playerName, boolean isHuman) {
        if (isGameExists(gameName) && !isGameActive(gameName)) {
            return game.joinPlayer(playerName, isHuman);
        } else {
            return -1;
        }

    }

    public int joinPlayer(String gameName, String playerName) {
        return joinPlayer(gameName, playerName, true);
    }

    public Set<String> getGamesNames() {
        Set<String> result = new HashSet<String>();
        if (game != null) {
            result.add(game.gameName);
        }
        return result;
    }

    public int getComputerizedPlayers(String gameName) {
        return isGameExists(gameName) ? game.computerizedPlayers : -1;
    }

    public int getHumanPlayers(String gameName) {
        return isGameExists(gameName) ? game.humanPlayers : -1;
    }

    public int getJoinedHumanPlayers(String gameName) {
        return isGameExists(gameName) ? game.getJoinedHumanPlayers() : -1;
    }

    public boolean isAutomaticDiceRoll(String gameName) {
        return isGameExists(gameName) ? game.useAutomaticDiceRoll : true;
    }

    public String[] getPlayersNames(String gameName) {
        if (!isGameExists(gameName)) {
            return new String[0];
        }

        List<String> names = new LinkedList<String>();
        for (Player player : game.players) {
            names.add(player.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public boolean[] getPlayersHumanity(String gameName) {
        if (!isGameExists(gameName)) {
            return new boolean[0];
        }
        boolean[] results = new boolean[game.players.size()];
        for (int index = 0; index < game.players.size(); index++) {
            results[index] = game.players.get(index).isHuman();
        }
        return results;
    }

    public boolean[] getPlayersActive(String gameName) {
        if (!isGameExists(gameName)) {
            return new boolean[0];
        }
        boolean[] results = new boolean[game.players.size()];
        for (int index = 0; index < game.players.size(); index++) {
            results[index] = game.players.get(index).isActive();
        }
        return results;
    }

    public int[] getPlayersAmounts(String gameName) {
        if (!isGameExists(gameName)) {
            return new int[0];
        }
        int[] results = new int[game.players.size()];
        for (int index = 0; index < game.players.size(); index++) {
            results[index] = game.players.get(index).getBalance();
        }
        return results;
    }

    class GameDetails {

        private String gameName;
        private int humanPlayers;
        private int computerizedPlayers;
        private boolean useAutomaticDiceRoll;
        private ArrayList<Player> players;
        private int currPlayerID;

        public GameDetails(String gameName, int humanPlayers, int computerizedPlayers, boolean useAutomaticDiceRoll) {
            this.gameName = gameName;
            this.humanPlayers = humanPlayers;
            this.computerizedPlayers = computerizedPlayers;
            this.useAutomaticDiceRoll = useAutomaticDiceRoll;
            this.players = new ArrayList<Player>();
            currPlayerID=0;
        }

        public boolean isGameStarted() {
            return this.gameName != null;
        }

        public boolean isGameActive() {
            return isGameStarted() && (this.humanPlayers == this.players.size() - computerizedPlayers);
        }

        public int joinPlayer(String playerName, boolean isHuman) {
            if (isHuman){ //TODO:ask shachar if we should use players.size insted of id
                this.players.add(new HumanPlayer(playerName,currPlayerID));
            }
            else{
                this.players.add(new ComputerPlayer(playerName,currPlayerID));
            }
            currPlayerID++;
            
            if (isGameActive()) {
                initNewGameSequence();
            }

            return currPlayerID-1;
        }

        private void initNewGameSequence() {
            //TODO : Maybe add thread
            Monopoly newGame = new Monopoly(gameName,players);
            GameManager.currentGame = newGame;
            EventImpl genEvent = (EventImpl) EventImpl.createNewGroupA(gameName, EventImpl.EventTypes.GameStart, "Game " + gameName + " is starting.");
            Monopoly.addEvent(genEvent);
            newGame.play();
        }

        public int joinPlayer(String playerName) {
            return joinPlayer(playerName, true);
        }

        public int getJoinedHumanPlayers() {
            int result = 0;
            for (Player player : players) {
                if (player.isHuman()) {
                    result++;
                }
            }
            return result;
        }

        public String[] getPlayersNames() {
            List<String> names = new LinkedList<String>();
            for (Player player : players) {
                names.add(player.getName());
            }
            return names.toArray(new String[names.size()]);
        }

        public boolean[] getPlayersHumanity() {
            boolean[] results = new boolean[players.size()];
            for (int index = 0; index < players.size(); index++) {
                results[index] = players.get(index).isHuman();
            }
            return results;
        }

        public boolean[] getPlayersActive() {
            boolean[] results = new boolean[players.size()];
            for (int index = 0; index < players.size(); index++) {
                results[index] = players.get(index).isActive();
            }
            return results;
        }

        public int[] getPlayersAmounts() {
            int[] results = new int[players.size()];
            for (int index = 0; index < players.size(); index++) {
                results[index] = players.get(index).getBalance();
            }
            return results;
        }
    }
}