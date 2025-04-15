package com.example.queuemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PeakHourController {

    @FXML private Label mainAppPeakHourLabel;

    public void setMainAppPeakHourLabel(int peakHour) {
        mainAppPeakHourLabel.setText(String.valueOf(peakHour));
    }
}
