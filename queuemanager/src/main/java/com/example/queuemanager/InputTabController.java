package com.example.queuemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputTabController {

    @FXML private TextField numberOfClientsTF;
    @FXML private TextField numberOfQueuesTF;
    @FXML private TextField minimumArrTimeTF;
    @FXML private TextField maximumArrTimeTF;
    @FXML private TextField minimumSerTimeTF;
    @FXML private TextField maximumSerTimeTF;
    @FXML private TextField simulationIntervalTF;

    private int numberOfClients;
    private int numberOfQueues;

    private int minimumArrTime;
    private int maximumArrTime;

    private int minimumSerTime;
    private int maximumSerTime;

    private int simulationInterval;

    @FXML
    private void confirm(ActionEvent event) {

        try {
            numberOfClients = Integer.parseInt(numberOfClientsTF.getText());
            numberOfQueues = Integer.parseInt(numberOfQueuesTF.getText());

            minimumArrTime = Integer.parseInt(minimumArrTimeTF.getText());
            maximumArrTime = Integer.parseInt(maximumArrTimeTF.getText());

            minimumSerTime = Integer.parseInt(minimumSerTimeTF.getText());
            maximumSerTime = Integer.parseInt(maximumSerTimeTF.getText());

            simulationInterval = Integer.parseInt(simulationIntervalTF.getText());

            checkIntervals(minimumArrTime, maximumArrTime);
            checkIntervals(minimumSerTime, maximumSerTime);

            ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();

        } catch (NumberFormatException numberFormatException) {
            numberOfClientsTF.clear();
            numberOfQueuesTF.clear();

            minimumArrTimeTF.clear();
            maximumArrTimeTF.clear();

            minimumSerTimeTF.clear();
            maximumSerTimeTF.clear();

            simulationIntervalTF.clear();
        }
    }

    private void checkIntervals(int minimumTime, int maximumTime) throws NumberFormatException {
        if(minimumTime > maximumTime) {
            throw new NumberFormatException();
        }
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public int getMinimumArrTime() {
        return minimumArrTime;
    }

    public int getMaximumArrTime() {
        return maximumArrTime;
    }

    public int getMinimumSerTime() {
        return minimumSerTime;
    }

    public int getMaximumSerTime() {
        return maximumSerTime;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }
}
