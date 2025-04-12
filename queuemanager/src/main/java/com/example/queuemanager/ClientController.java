package com.example.queuemanager;

import dataModel.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ClientController {

    @FXML private Label clientServTimeLabel;
    @FXML private Label clientArrTimeLabel;
    @FXML private Label clientIDLabel;

    @FXML private HBox clientHBox;

    public void setLabels(Client client) {
        clientIDLabel.setText(String.valueOf(client.getId()));
        clientArrTimeLabel.setText(String.valueOf(client.getArrivalTime()));
        clientServTimeLabel.setText(String.valueOf(client.getServeTime()));
    }

    public void setProcessingStyle() {
        clientHBox.setStyle("-fx-background-color: #8E7DAA");
    }
}
