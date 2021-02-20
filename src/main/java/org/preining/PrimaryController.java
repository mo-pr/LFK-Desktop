package org.preining;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class PrimaryController {

    public TextArea textOut;
    public Button stopBtn;
    public Button startBtn;
    public Button clearBtn;
    public RadioButton laufend;
    public RadioButton täglich;
    private URL url;
    private API apiObj;
    private File logFile;

    public void apiStart(ActionEvent actionEvent){
        try {
            logFile = new File(PrimaryController.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"lfk_gui_log.txt");
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        radioBtnChanged(actionEvent);
        apiObj = new API(url);
        Console co = new Console("FF SCHWEINBACH",this.textOut, this.logFile);
        apiObj.addObserver(co);
        apiObj.APIStart();
        täglich.setDisable(true);
        laufend.setDisable(true);
        stopBtn.setVisible(true);
        startBtn.setVisible(false);
    }
    public void apiStop(ActionEvent actionEvent) {
        apiObj.APIStop();
        täglich.setDisable(false);
        laufend.setDisable(false);
        startBtn.setVisible(true);
        stopBtn.setVisible(false);
    }
    public void apiClear(ActionEvent actionEvent) {
        textOut.setText("");
    }
    public void radioBtnChanged(ActionEvent actionEvent) {
        if(laufend.isSelected()){
            try {
                url = new URL("https://intranet.ooelfv.at/webext2/rss/json_laufend.txt");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if(täglich.isSelected()){
            try {
                url = new URL("https://intranet.ooelfv.at/webext2/rss/json_taeglich.txt");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
