package org.mp;

import javafx.scene.control.TextArea;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javax.swing.*;

public class Console implements Observer {
    private boolean isQuit = false;
    private String ffName;
    private TextArea text;
    private File log;
    private Alarm[] alarmArr;

    public String getFfName() {
        return ffName;
    }
    public Console(String FF, TextArea textA, File logFile){
        if(FF != null){
            this.ffName = FF;
        }
        if(textA != null){
            this.text = textA;
        }
        if(logFile != null){
            this.log = logFile;
        }
    }
    @Override
    public void notify(Object sender, String infoOut){
        text.setText("");
        String[] alarms = infoOut.split("\"einsatz\":");
        alarmArr = new Alarm[alarms.length];
        if(alarms.length != 0) {
            for (int i = 1; i < alarms.length; i++) {
                Alarm alarm = new Alarm(alarms[i], this.text, this.ffName, this.log);
                alarmArr[i] = alarm;
            }
            for(int i = 1; i < alarmArr.length; i++){
                if(alarmArr[i].geteFDArray().contains(this.ffName)){
                    infoBox(alarmArr[i].print());
                }
            }
        }
    }
    private void infoBox(String infoMessage) {
        final JFXPanel fxPanel = new JFXPanel();
        String bip = "gong_bf.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        JOptionPane optionPane = new JOptionPane(infoMessage,JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("ALARM FEUERWEHR SCHWEINBACH");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        //isQuit = true;
    }
}
