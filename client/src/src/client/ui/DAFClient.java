/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DAFClient.java
 *
 * Created on May 16, 2011, 4:25:59 PM
 */
package src.client.ui;

import comm.Event;
import comm.MonopolyResult;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.text.DefaultCaret;
import src.client.GameManager;
import src.client.Server;
import src.client.ui.utils.EventTypes;

/**
 *
 * @author omer
 */
public class DAFClient extends javax.swing.JPanel {

    private Timer feederTimer;

    /** Creates new form DAFClient */
    public DAFClient() {
        initComponents();
        jTextArea1.setEditable(false);
        ((DefaultCaret) jTextArea1.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); //Set AutoScroll for textual console.
        startEventFeederTask();
        

    }

    private void startEventFeederTask() {
        if (feederTimer != null) {
            feederTimer.cancel();
        }
        feederTimer = Server.getInstance().startPolling("EventFeeder Timer", GameManager.feeder, 0, 1);
    }

    private void stopEventFeederTask() {
        if (feederTimer != null) {
            feederTimer.cancel();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        jFrame1Layout.setVerticalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("yes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("no");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(243, Short.MAX_VALUE).addComponent(jButton1).addGap(114, 114, 114).addComponent(jButton2).addGap(123, 123, 123).addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(43, 43, 43)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton2).addComponent(jButton1)).addGap(23, 23, 23)));
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private  javax.swing.JTextArea jTextArea1;
    // End of variables declaration

    private void printEventToConsole(Event e) {
        String message = "Received event : ID " + e.getEventID() + " , Type " + e.getEventType() + " , " + e.getEventMessage().getValue();
        jTextArea1.append(message+"\n");
        jTextArea1.validate();
        jTextArea1.repaint();
    }

//    public void handelEvent(Event event) {
//        printEventToConsole(event);
//        if (event.getEventType() == EventTypes.PromptPlayerToBuyAsset.getCode()&& event.getPlayerName().getValue().equals(GameManager.clientName)) {
//            MonopolyResult buyResult = Server.getInstance().buy(GameManager.clientPlayerID, event.getEventID(), false);
////            if (buyResult.isError()) {
////                jTextArea1.append("Buy returned Error!" +event.getEventMessage().getValue());
////            } else {
////                jTextArea1.append("Buy returned successfuly!\n");
////            }
//            
//        } else if (event.getEventType() == EventTypes.PromptPlayerToBuyHouse.getCode()&& event.getPlayerName().getValue().equals(GameManager.clientName)) {
//            MonopolyResult buyResult = Server.getInstance().buy(GameManager.clientPlayerID, event.getEventID(), false);
////            if (buyResult.isError()) {
////                jTextArea1.append("Buy returned Error!\n");
////            } else {
////                jTextArea1.append("Buy returned successfuly!");
////            }
//        }
//        /*  switch (event.getEventID().intValue())
//        {
//        case 1:
//
//        break;
//        case 2:
//        break;
//        case 3:
//        break;
//        case 4:
//        break;
//        case 5:
//        break;
//        case 6:
//        break;
//        case 7:
//        break;
//        case 8:
//        break;
//        case 9:
//        break;
//        case 10:
//        break;
//        case 11:
//        break;
//        case 12:
//        break;
//        case 13:
//        break;
//        case 14:
//        break;
//        case 15:
//        break;
//        case 16:
//        break;
//        case 17:
//        break;
//        case 18:
//        break;
//        case 19:
//        break;
//        case 20:
//        break;
//        }*/
//    }

//    private class EventFeeder extends TimerTask {
//
//        @Override
//        public void run() {
//            if (!Server.getInstance().isEventQueueEmpty()) {
//                handelEvent(Server.getInstance().popEventFromQueue());
//            }
//        }
//    }
}
