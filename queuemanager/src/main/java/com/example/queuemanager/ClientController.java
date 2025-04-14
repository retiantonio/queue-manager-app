package com.example.queuemanager;

import dataModel.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class ClientController {

    @FXML private Label clientServTimeLabel;
    @FXML private SVGPath clientSVGIcon;

    public void setLabel(Client client) {
        clientServTimeLabel.setText(String.valueOf(client.getServeTime()));
    }

    public void setProcessingStyle() {
        processingAnimation();
    }

    private void processingAnimation() {
        clientSVGIcon.setStyle("-fx-fill: #8E7DAA");
        clientServTimeLabel.setStyle("-fx-text-fill: #FFFFFF");
    }
}
