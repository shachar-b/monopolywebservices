/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GamesPanel.java
 *
 * Created on May 5, 2011, 1:16:30 AM
 */

package src.client.ui.initgame;

import src.client.GameDetails;
import src.client.PlayerDetails;
import src.client.Server;
import java.awt.Component;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import src.client.GameManager;

/**
 *
 * @author blecherl
 */
public class GamesPanel extends javax.swing.JPanel {

    private DefaultListModel gamesListModel;
    private DefaultListModel playersListModel;

    private Timer gamesRefreshTimer;
    private Timer playersRefrshTimer;
    private Timer gameMonRefreshTimer;

    private ListSelectionListener listSelectionListener;
    private boolean clientAlreadyJoined = false;
    
    /** Creates new form GamesPanel */
    public GamesPanel() {
        gamesListModel = new DefaultListModel();
        playersListModel = new DefaultListModel();

        listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String gameName = getSelectedGameName();
                if (gameName != null && !clientAlreadyJoined) {
                    joinGameButton.setEnabled(true);
                    startGamePlayersTask(gameName);
                } else {
                    joinGameButton.setEnabled(false);
                }
            }
        };

        initComponents();
        computerPlayersSlider.setMaximum(MAX_NUMBER_OF_PLAYERS-1);
        waitingGamesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof GameDetails) {
                    GameDetails gameDetails = (GameDetails) value;
                    setText(gameDetails.getGameName() + " (waiting for " + gameDetails.getWaitingForPlayersNumber() +  " players to join)");
                }
                return this;
            }
        });
        waitingGamesList.getSelectionModel().addListSelectionListener(listSelectionListener);
        startRefreshTask();
    }

    private String getSelectedGameName() {
        GameDetails selectedGame = (GameDetails) waitingGamesList.getSelectedValue();
        return (selectedGame != null) ? selectedGame.getGameName() : null;
    }
    private DefaultListModel getListModel() {
        return gamesListModel;
    }

    private DefaultListModel getPlayersListModel() {
        return playersListModel;
    }

    public void setGames (List<GameDetails> games) {
        synchronized (this){
            Object selectedValue = waitingGamesList.getSelectedValue();
            gamesListModel.clear();
            games = games != null ? games : Collections.EMPTY_LIST;

            for (GameDetails game : games) {
                gamesListModel.addElement(game);
            }
            waitingGamesList.setSelectedValue(selectedValue, true);
        }
    }

    private void startRefreshTask() {
        gamesRefreshTimer = Server.getInstance().startPolling("Waiting Games Timer", new RefreshWaitingGamesTask(this), 0, 5);
    }

    private void stopRefreshTask() {
        if (gamesRefreshTimer != null) {
            gamesRefreshTimer.cancel();
        }
        if (playersRefrshTimer != null) {
            playersRefrshTimer.cancel();
        }
        if(gameMonRefreshTimer!=null)
        {
         gameMonRefreshTimer.cancel();
        }
    }

    private void startGamePlayersTask(String gameName) {
        if (playersRefrshTimer != null) {
            playersRefrshTimer.cancel();
        }
        playersRefrshTimer = Server.getInstance().startPolling("Game Players Timer", new RefreshGamePlayersTask(this, gameName), 0, 1);
    }


    private void startGameMonRefreshTask(String gameName, String playerName) {
        if (gameMonRefreshTimer != null) {
            gameMonRefreshTimer.cancel();
        }
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        gameMonRefreshTimer = Server.getInstance().startPolling("Game start monitor Timer", new MonitorGameStartTask(frame, gameName, playerName), 0, 1);
    }

    public void setGamePlayers(List<PlayerDetails> players) {
        waitingGamesList.getSelectionModel().removeListSelectionListener(listSelectionListener);
        int[] selectedIndicies = playersList.getSelectedIndices();
        playersListModel.clear();
        for (PlayerDetails player : players) {
            playersListModel.addElement(player.getName() + "(" + player.getAmount() + ")");
        }
        playersList.setSelectedIndices(selectedIndicies);
        waitingGamesList.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        joinGameButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        waitingGamesList = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        playersList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        startNewGameButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        automaticDiceRollCheckBox = new javax.swing.JCheckBox();
        gameNameTextField = new javax.swing.JTextField();
        HumanPlayersSlider = new javax.swing.JSlider();
        computerPlayersSlider = new javax.swing.JSlider();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(0.3);

        jPanel1.setLayout(new java.awt.BorderLayout());

        joinGameButton.setText("Join");
        joinGameButton.setEnabled(false);
        joinGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinGameButtonActionPerformed(evt);
            }
        });
        jPanel1.add(joinGameButton, java.awt.BorderLayout.PAGE_END);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Waiting Games");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);

        waitingGamesList.setModel(getListModel());
        waitingGamesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(waitingGamesList);

        jSplitPane2.setTopComponent(jScrollPane2);

        playersList.setModel(getPlayersListModel());
        playersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(playersList);

        jSplitPane2.setRightComponent(jScrollPane1);

        jPanel1.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        startNewGameButton.setText("Start New Game");
        startNewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startNewGameButtonActionPerformed(evt);
            }
        });
        jPanel2.add(startNewGameButton, java.awt.BorderLayout.PAGE_END);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("New Game Details");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jLabel3.setText("Name:");

        jLabel4.setText("Human Players:");

        jLabel5.setText("Computerized Players:");

        automaticDiceRollCheckBox.setSelected(true);
        automaticDiceRollCheckBox.setText("Use Automatic Dice Rolls");

        HumanPlayersSlider.setMajorTickSpacing(1);
        HumanPlayersSlider.setMaximum(6);
        HumanPlayersSlider.setMinimum(1);
        HumanPlayersSlider.setMinorTickSpacing(1);
        HumanPlayersSlider.setPaintLabels(true);
        HumanPlayersSlider.setPaintTicks(true);
        HumanPlayersSlider.setSnapToTicks(true);
        HumanPlayersSlider.setToolTipText("Number of Human players");
        HumanPlayersSlider.setValue(1);
        HumanPlayersSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                HumanPlayersSliderStateChanged(evt);
            }
        });

        computerPlayersSlider.setMajorTickSpacing(1);
        computerPlayersSlider.setMaximum(6);
        computerPlayersSlider.setMinorTickSpacing(1);
        computerPlayersSlider.setPaintLabels(true);
        computerPlayersSlider.setPaintTicks(true);
        computerPlayersSlider.setSnapToTicks(true);
        computerPlayersSlider.setToolTipText("Number of Computer players");
        computerPlayersSlider.setValue(1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(automaticDiceRollCheckBox)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                                    .addComponent(gameNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(HumanPlayersSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(computerPlayersSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(gameNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(HumanPlayersSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(computerPlayersSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(automaticDiceRollCheckBox)
                .addContainerGap(234, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void startNewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startNewGameButtonActionPerformed
        Server.getInstance().startNewGame(gameNameTextField.getText(), 
                (Integer)HumanPlayersSlider.getValue(),
                (Integer)computerPlayersSlider.getValue(), automaticDiceRollCheckBox.isSelected());
    }//GEN-LAST:event_startNewGameButtonActionPerformed

    private void joinGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinGameButtonActionPerformed
        stopRefreshTask();
        String gameName = getSelectedGameName();
        String playerName = JOptionPane.showInputDialog("Enter Player Name");
        if (playerName!=null)
        {
            clientAlreadyJoined=true;
            GameManager.clientPlayerID=Server.getInstance().joinPlayer (gameName, playerName);
            GameManager.clientName=playerName;
            GameManager.currentJoinedGame=gameName;
            playersListModel.clear();
        }
        startRefreshTask();
        startGamePlayersTask(gameName);
        startGameMonRefreshTask(gameName,playerName);
    }//GEN-LAST:event_joinGameButtonActionPerformed

    private static final int MAX_NUMBER_OF_PLAYERS = 6;

    private void HumanPlayersSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_HumanPlayersSliderStateChanged
       computerPlayersSlider.setMaximum(MAX_NUMBER_OF_PLAYERS-HumanPlayersSlider.getValue());
    }//GEN-LAST:event_HumanPlayersSliderStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider HumanPlayersSlider;
    private javax.swing.JCheckBox automaticDiceRollCheckBox;
    private javax.swing.JSlider computerPlayersSlider;
    private javax.swing.JTextField gameNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JButton joinGameButton;
    private javax.swing.JList playersList;
    private javax.swing.JButton startNewGameButton;
    private javax.swing.JList waitingGamesList;
    // End of variables declaration//GEN-END:variables
}
