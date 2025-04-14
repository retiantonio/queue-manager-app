package com.example.queuemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
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

        System.setProperty("prism.lcdtext", "false");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image icon = new Image(getClass().getResource("/Images/bird-logo-app.png").toExternalForm());
        stage.getIcons().add(icon);

        stage.setTitle("Queuerd");
        stage.setScene(scene);

        Font.loadFont(getClass().getResourceAsStream("/Fonts/Inter_28pt-Medium.ttf"), 64);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/Inter_28pt-Bold.ttf"), 64);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/Inter_28pt-SemiBold.ttf"), 64);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}