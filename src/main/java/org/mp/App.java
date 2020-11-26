package org.mp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Database db = new Database();
        db.Connect();
        Scene scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.setCursor(Cursor.DEFAULT);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.initStyle(StageStyle.DECORATED);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("LFK");
        stage.setResizable(false);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args){
        launch();
    }
}