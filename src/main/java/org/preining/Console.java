package org.preining;

import org.json.*;
import javafx.scene.control.TextArea;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javax.swing.*;
import org.preining.Observer;

public class Console implements Observer{
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
        JSONObject json = new JSONObject(infoOut);
        JSONObject alarms = new JSONObject(json.get("einsaetze").toString());
        int amount = Integer.parseInt(json.get("cnt_einsaetze").toString());
        alarmArr = new Alarm[amount];
        for(int i = 0; i < amount; i++){
            String temp = String.valueOf(i);
            JSONObject tempJSON = new JSONObject(alarms.get(temp).toString());
            JSONObject tempJSON2 = new JSONObject(tempJSON.get("einsatz").toString());
            alarmArr[i] = new Alarm(tempJSON2, this.text,this.ffName, this.log);
            if(alarms.get(""+temp).toString().contains(ffName)){
                infoBox(alarms.get(""+temp).toString());
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
