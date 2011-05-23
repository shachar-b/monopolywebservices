/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.client;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import src.players.Player;
import src.squares.Square;
import src.ui.IUI;
import src.ui.UI;
import ui.utils.ImagePanel;

/**
 *
 * @author omer
 */
public class GameManager {

    public static int clientPlayerID = -1;
    public static String clientName = "not joined";
    public static String currentJoinedGame = "not joined";
    public final static int SUBTRACT = -1;
    public final static int ADD = 1;
    public static final int START_SQ_LOCATION = 0;
    public static final Player assetKeeper = null;
    public static IUI CurrentUI;
    public static final String IMAGES_FOLDER = "src/resources/images/";
    public static final String PLAYER_ICONS_PATH = IMAGES_FOLDER+"playerIcons/";
    public static final Font DefaultFont=new Font("Serif", Font.BOLD, 10);
    public static final IUI currentUI = null;
    private ArrayList<Player> gamePlayers;
    private ArrayList<Square> gameBoard;
    public static GameManager staticInstance;

    private GameManager() {
        List<PlayerDetails> playerDetails = Server.getInstance().getPlayersDetails(currentJoinedGame);
        XMLInitializer gameInitializer = new XMLInitializer();
        gameBoard = gameInitializer.initBoard();
        this.gamePlayers=convertPlayerDetailsToPlayers(playerDetails);
        //staticInstance = this;
        CurrentUI= new UI();
    }

    public static GameManager createShit(){
        GameManager g =  new GameManager();
        staticInstance = g;
        return g;
    }
    
    private ArrayList<Player> convertPlayerDetailsToPlayers(List<PlayerDetails> list) {
        ArrayList<Player> result = new ArrayList<Player>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new Player(list.get(i).getName(), new ImagePanel(PLAYER_ICONS_PATH+(i+1)+".png"), list.get(i).getAmount()));
        }

        return result;
    }
    
        public ArrayList<Square> getGameBoard() {
        return gameBoard;
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }
}
