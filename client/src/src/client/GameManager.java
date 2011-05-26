/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.client;

import comm.Event;
import comm.MonopolyResult;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import src.assets.Asset;
import src.assets.City;
import src.client.ui.utils.EventTypes;
import src.players.Player;
import src.squares.Square;
import src.ui.UI;
import src.ui.guiComponents.dialogs.BuyDialog;
import src.ui.guiComponents.dice.Dice;
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
    public static final String IMAGES_FOLDER = "src/resources/images/";
    public static final String PLAYER_ICONS_PATH = IMAGES_FOLDER+"playerIcons/";
    public static final Font DefaultFont=new Font("Serif", Font.BOLD, 10);
    public static  UI currentUI = null;
    public static final TimerTask feeder=new EventFeeder();
    private ArrayList<Player> gamePlayers;
    private ArrayList<Square> gameBoard;
    public static GameManager staticInstance;
    private Timer feederTimer;
   

    private GameManager() {
        List<PlayerDetails> playerDetails = Server.getInstance().getPlayersDetails(currentJoinedGame);
        XMLInitializer gameInitializer = new XMLInitializer();
        gameBoard = gameInitializer.initBoard();
        this.gamePlayers=convertPlayerDetailsToPlayers(playerDetails);
        //staticInstance = this;
        currentUI= new UI();
        
        startEventFeederTask();
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
    public Player getPlayerByName(String given)
    {
        for(Player p:gamePlayers)
        {
            if(p.getName().equals(given))
            {
                return p;
            }
        }
        throw new RuntimeException("this is wrong- an unknown player name was given by server: "+given);
    }
    
    
    public void handelEvent(Event event) {
        //currentUI.notifyPlayerPaysRent(event.getEventMessage().getValue());//TODO:remove this line
        final int eID = event.getEventID();
        final int eBSID = event.getBoardSquareID();
        Player pl=null;
        if(event.getEventType()>2)
        {
            pl= getPlayerByName(event.getPlayerName().getValue());
            
        }
        int bosid;
        switch (event.getEventType().intValue())
        {
            
            
          /*
        * GameStart(1),GameOver(2),GameWinner(3),PlayerResigned(4),PlayerLost(5),
        PromptPlayerToRollDice (6),DiceRoll(7),Move(8),PassedStartSquare(9),LandedOnStartSquare(10),
        GoToJail(11),PromptPlayerToBuyAsset(12),PromptPlayerToBuyHouse(13),AssetBoughtMessage(14),HouseBoughtMessage(15),
        SurpriseCard(16),WarrantCard(17),GetOutOfJailCard(18),Payment(19),PlayerUsedJailCard(20);
             */
        case 1://game start - ignored -remove
        break;
        case 2://GameOver
            currentUI.notifyGameWinner(pl);
        break;
        case 3://GameWinner
        break;
        case 4://PlayerResigned- do the same as 5 maybe make a diffrent statement
  
        case 5://PlayerLost
            pl= getPlayerByName(event.getPlayerName().getValue());
            pl.remove();
            gamePlayers.remove(pl);
        break;
        case 6://PromptPlayerToRollDice
        break;
        case 7://dice roll
            Dice.getGameDice().makeItRoll(event.getFirstDiceResult(), event.getSecondDiceResult());
            break;
        case 8://Move
            String pname=event.getPlayerName().getValue();
            bosid=event.getBoardSquareID();
            int nbosid=event.getNextBoardSquareID();
            currentUI.movePlayer(pl,bosid,nbosid);
        break;
        case 9://PassedStartSquare
            currentUI.notifyPassStartSquare();
        break;
        case 10://LandedOnStartSquare
        break;
        case 11://GoToJail
        break;
        case 12://PromptPlayerToBuyAsset
        case 13://PromptPlayerToBuyHouse
            //TODO : CONTINUE HERE
            
         if ( event.getPlayerName().getValue().equals(GameManager.clientName)) {
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                   BuyDialog diag= new BuyDialog((JFrame)currentUI.getFrame(),true, (Asset)gameBoard.get(eBSID), true, eID);
                   diag.setVisible(true);
                }
            });
         }
         
        break;
        case 14://AssetBoughtMessage
            bosid=event.getBoardSquareID();
            ((Asset)gameBoard.get(bosid)).setOwner(pl);
        break;
        case 15://HouseBoughtMessage
            bosid=event.getBoardSquareID();
            ((City)gameBoard.get(bosid)).BuyHouse(pl);
        break;
        case 16://SurpriseCard
            currentUI.notifyPlayerGotCard(pl, 1, event.getEventMessage().getValue());
        break;
        case 17://WarrantCard
             currentUI.notifyPlayerGotCard(pl, -1, event.getEventMessage().getValue());
        break;
        case 18://GetOutOfJailCard
            currentUI.notifyPlayerGotCard(pl, 1, event.getEventMessage().getValue());
        break;
        case 19://Payment
                Player reciver = getPlayerByName(event.getPaymentToPlayerName().getValue());
                reciver.ChangeBalance(event.getPaymentAmount());
                if(event.isPaymemtFromUser())
                {
                    currentUI.notifyPlayerPaysRent(event.getEventMessage().getValue());
                }
                else
                {
                    
                }
                
            
        break;
        case 20://PlayerUsedJailCard
            
        break;
        }
    }

    private static class EventFeeder extends TimerTask {

        @Override
        public void run() {
            System.err.println("feeding");
            if (!Server.getInstance().isEventQueueEmpty()) {
                staticInstance.handelEvent(Server.getInstance().popEventFromQueue());
            }
        }
    }
    private void startEventFeederTask() {
        if (feederTimer != null) {
            feederTimer.cancel();
        }
        feederTimer = Server.getInstance().startPolling("EventFeeder Timer", GameManager.feeder, 0, 1);
    }
}
