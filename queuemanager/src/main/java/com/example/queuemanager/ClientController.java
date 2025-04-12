package com.example.queuemanager;

import dataModel.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientController {

    @FXML private Label clientServTimeLabel;
    @FXML private Label clientArrTimeLabel;
    @FXML private Label clientIDLabel;

    public void setLabels(Client client) {
        clientIDLabel.setText(String.valueOf(client.getId()));
        clientArrTimeLabel.setText(String.valueOf(client.getArrivalTime()));
        clientServTimeLabel.setText(String.valueOf(client.getServeTime()));
    }
}
