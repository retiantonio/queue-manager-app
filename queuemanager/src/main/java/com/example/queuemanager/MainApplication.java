package com.example.queuemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Logger.getLogger("javafx.fxml").setLevel(Level.SEVERE);
        Logger.getLogger("javafx.fxml.FXMLLoader").setLevel(Level.SEVERE);
        Logger.getLogger("").setLevel(Level.SEVERE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Queuerd");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}