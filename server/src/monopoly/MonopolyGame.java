package monopoly;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import monopoly.gamesmanager.MonopolyGameManager;
import monopoly.results.EventArrayResult;
import monopoly.results.GameDetailsResult;
import monopoly.results.IDResult;
import monopoly.results.MonopolyResult;
import monopoly.results.PlayerDetailsResult;

/**
 *
 * @author blec
 */
public class MonopolyGame {

    private MonopolyGameManager gameManager;
    static MonopolyGameManager gameManagerHandle;
    static int gameID = 0;

    /**
     * a constructor for this game
     */
    public MonopolyGame() {
        gameManager = new MonopolyGameManager();
        gameManagerHandle = gameManager;
    }
/**
     * 
     * @return the game board schema file as string
     */
    public String getGameBoardSchema() {
        return getFileAsString(GameManager.SchemaFile);
    }

    /**
     * 
     * @return the game bored XML as string
     */
    public String getGameBoardXML() {
        return getFileAsString(GameManager.DataFile);
    }

    /**
     * 
     * @param path a path of a valid text file
     * @return the file as string
     */
    public String getFileAsString(String path) {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(path);
            return new Scanner(is).useDelimiter("\\Z").next();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                   System.err.println("Encountered error while closing file: " + path);
                }
            }
        }
    }

    /**
     * if a game can be started a new game whould be started with the given name plus a uniqe number
     * @param gameName- a name for the game
     * @param humanPlayers- the number of human players 
     * @param computerizedPlayers- the number of computer players
     * @param useAutomaticDiceRoll- true to use auto dice roll false to promot
     * @return an error or approval of game start
     */
    public MonopolyResult startGame(String gameName, int humanPlayers, int computerizedPlayers, boolean useAutomaticDiceRoll) {
        gameID++;
        if (gameManager.getNumberOfGames() > 0) {
            if (gameManager.isGameStarted(gameID + "." + gameName)) {
                return MonopolyResult.error("A game '" + gameID + "." + gameName + "' has already been started");
            } else {
                return MonopolyResult.error("Only one game is supported, game " + gameManager.getGamesNames().toString() + " is on.");
            }
        } else {
            gameManager.addGame(gameID + "." + gameName, humanPlayers, computerizedPlayers, useAutomaticDiceRoll);
            if (checkGameLegitimacy(humanPlayers, computerizedPlayers)) {
                for (int i = 0; i < computerizedPlayers; i++) {//Add computer players
                    gameManager.joinPlayer(gameID + "." + gameName, "Computer" + (i + 1), false);
                }
            }
            return new MonopolyResult();
        }
    }
    /**
     * 
     * @param humanPlayers- a number of human players
     * @param computerizedPlayers- the number of computerized Players
     * @return true if the given numbers are valid false otherwise
     */
    private boolean checkGameLegitimacy(int humanPlayers, int computerizedPlayers) {
        int totalPlayers = humanPlayers + computerizedPlayers;
        if (totalPlayers >= GameManager.MIN_NUMBER_OF_PLAYERS && totalPlayers <= GameManager.MAX_NUMBER_OF_PLAYERS) {
            if (humanPlayers >= 0 && computerizedPlayers >= 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param gameName- the game name 
     * @return the game details if it is alive false otherwise
     */
    public GameDetailsResult getGameDetails(String gameName) {
        if (!gameManager.isGameStarted(gameName)) {
            return GameDetailsResult.error("Game '" + gameName + "' has not been started");
        } else {
            return new GameDetailsResult(
                    (gameManager.isGameActive(gameName) ? "ACTIVE" : "WAITING"),
                    gameManager.getHumanPlayers(gameName), gameManager.getComputerizedPlayers(gameName),
                    gameManager.getJoinedHumanPlayers(gameName), gameManager.isAutomaticDiceRoll(gameName));
        }
    }
/**
     * 
     * @return  the names of the waiting games
     */
    public String[] getWaitingGames() {
        List<String> results = new LinkedList<String>();
        for (String gameName : gameManager.getGamesNames()) {
            if (gameManager.isGameStarted(gameName) && !gameManager.isGameActive(gameName)) {
                results.add(gameName);
            }
        }
        return results.toArray(new String[results.size()]);
    }
/**
     * 
     * @return the names of the Active games
     */
    public String[] getActiveGames() {
        List<String> results = new LinkedList<String>();
        for (String gameName : gameManager.getGamesNames()) {
            if (gameManager.isGameStarted(gameName) && gameManager.isGameActive(gameName)) {
                results.add(gameName);
            }
        }
        return results.toArray(new String[results.size()]);
    }
/**
     * 
     * @param gameName the name of the game
     * @param playerName the name of the player
     * @return an id if the player joined error otherwise
     */
    public IDResult joinGame(String gameName, String playerName) {
        if (gameManager.isGameStarted(gameName)
                && !gameManager.isGameActive(gameName)) {
            return new IDResult(gameManager.joinPlayer(gameName, playerName));
        } else {
            return IDResult.error("Game '" + gameName + "' is not open for registration");
        }
    }

    
    /**
     * 
     * @param gameName- the name of the game
     * @return the details of all players
     */
    public PlayerDetailsResult getPlayersDetails(String gameName) {
        if (!gameManager.isGameStarted(gameName)) {
            return PlayerDetailsResult.error("Game '" + gameName + "' has not been started");
        } else {
            return new PlayerDetailsResult(
                    gameManager.getPlayersNames(gameName),
                    gameManager.getPlayersHumanity(gameName),
                    gameManager.getPlayersActive(gameName),
                    gameManager.getPlayersAmounts(gameName));
        }
    }

    /**
     * 
     * @param eventID- the event Id of the first id to get
     * @return a list of all events from ID to the end (or an error)
     */
    public EventArrayResult getAllEvents(int eventID) {
        //validate evendID against last eventID
        if (eventID <= Monopoly.getCurrentEventID() && eventID >= 0) {
            ArrayList<Event> result = Monopoly.getEventsFrom(eventID);
            Event[] retVal = new Event[result.size()];
            result.toArray(retVal);
            return new EventArrayResult(retVal);
        } else //Illegal index
        {
            return new EventArrayResult(true, "Invalid EventID!");
        }
    }

    /**
     * 
     * @param playerID- the player for whome to set the dice
     * @param eventID- the event who we responded
     * @param dice1- the first dice res
     * @param dice2- the second dice res
     * @return an error or sucssess message
     */
    public MonopolyResult setDiceRollResults(int playerID, int eventID, int dice1, int dice2) {
        //validate evendID against last eventID
        //validate playerID against games players and players state
        //validate game mode
        MonopolyResult res = validatePlayerAndEventID(playerID, eventID, EventImpl.EventTypes.PromptPlayerToRollDice);
        if (res.isError())//if the player isnt active he wont be the current one or the event isnt the currect one !
        {
            return res;

        } else//do it
        {
            TimeOutTasks.stopTimer();
            GameManager.currentGame.throwDie(dice1, dice2);
            return new MonopolyResult(false, "Dice1= " + dice1 + ", Dice2= " + dice2);
        }
    }

    /**
     * 
     * @param playerID- the ID of the player who want to resign
     * @return an error on fail or succuss message
     */
    public MonopolyResult resign(int playerID) {
        //validate playerID against games players and players state
        if (GameManager.currentGame == null) {
            return new MonopolyResult(true, "cant resign- game isnt active");
        } else if (GameManager.currentGame.isValidPlayerID(playerID)) {
            GameManager.currentGame.eventDispatch(playerID, "forfeit");
        }
        return new MonopolyResult();
    }

    /**
     * 
     * @param playerID- the sender ID
     * @param eventID- the event id this relates to
     * @param buy - should the asset be boght
     * @return an error on fail or succuss message
     */
    public MonopolyResult buy(int playerID, int eventID, boolean buy) {
        //validate evendID against last eventID
        //validate playerID against games players and players state
        //validate asset from eventID
        MonopolyResult res = validatePlayerAndEventID(playerID, eventID, EventImpl.EventTypes.PromptPlayerToBuyAsset, EventImpl.EventTypes.PromptPlayerToBuyHouse);
        if (res.isError())//if the player isnt active he wont be the current one or the event isnt the currect one !
        {
            return res;

        } else//do it
        {
            TimeOutTasks.stopTimer();
            if (buy) {
                if (GameManager.currentGame.isLegalBuyOP()) {
                    GameManager.currentGame.buyWhatYouAreSittingOn();
                    return new MonopolyResult(false, "asset bought");
                } else {

                    System.err.println("an falty attampt to buy was made-this is a fatal error ");
                    return new MonopolyResult("an falty attampt to buy was made-this is a fatal error ");
                }
            } else {

                GameManager.currentGame.eventDispatch(playerID, "endTurn");
                return new MonopolyResult(false, "asset have not been bought");
            }
        }
    }
    /**
     * 
     * @param playerID-the player ID who was given
     * @param eventID - the event ID which was given
     * @param AlowedEventTypes-the event types which are allowed (1 or more)
     * @return true if the last event is the ID is of the given player the eventID is for last event and the type fits
     */
    private MonopolyResult validatePlayerAndEventID(int playerID, int eventID, EventImpl.EventTypes... AlowedEventTypes) {
        if (GameManager.currentGame == null) {
            return new MonopolyResult(true, "no game is active");
        }
        if (!Monopoly.isLastEventID(eventID)) {
            return new MonopolyResult(true, "the event id given is not valid");
        }
        boolean found = false;
        for (EventImpl.EventTypes type : AlowedEventTypes) {
            if (Monopoly.isLastEventIDType(type)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new MonopolyResult(true, "the event type of the id given is not valid for this action");
        }
        if (GameManager.currentGame.getCurrentActivePlayer().getID() != playerID)//if the player isnt active he wont be the current one !
        {//also makes sure the player is active
            return new MonopolyResult(true, "the player id given dont belong to the current active player");

        } else {
            return new MonopolyResult(false, "");
        }
    }
}
