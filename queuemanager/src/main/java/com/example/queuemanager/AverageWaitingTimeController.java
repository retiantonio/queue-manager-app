package com.example.queuemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;

public class AverageWaitingTimeController {

    @FXML private Label mainAppAverageWaitingTime;

    public void setAverageWaitingTime(double avgWait) {
        mainAppAverageWaitingTime.setText(String.format("%.2f", avgWait));
    }
}
