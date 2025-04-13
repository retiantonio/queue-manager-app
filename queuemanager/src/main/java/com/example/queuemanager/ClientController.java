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
        Timeline timeLine = new Timeline(
            new KeyFrame(Duration.ZERO, _ -> {
                clientSVGIcon.setStyle("-fx-fill: #FFFFFF");
                clientServTimeLabel.setStyle("-fx-text-fill: #725D95");
            }),
            new KeyFrame(Duration.millis(62.5), _ -> {
                clientSVGIcon.setStyle("-fx-fill: #9c8db4");
                clientServTimeLabel.setStyle("-fx-text-fill: #C6BED4");
            }),
            new KeyFrame(Duration.millis(125), _ -> {
                clientSVGIcon.setStyle("-fx-fill: #725D95");
                clientServTimeLabel.setStyle("-fx-text-fill: #FFFFFF");
            })
        );

        timeLine.setCycleCount(1);
        timeLine.setAutoReverse(true);
        timeLine.playFromStart();
    }
}
