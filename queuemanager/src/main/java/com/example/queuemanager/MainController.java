package com.example.queuemanager;

import businessLogic.Scheduler;
import businessLogic.SimulationManager;
import dataModel.Client;
import dataModel.Queue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController {

    private int numberOfClients;
    private int numberOfQueues;
    private int minimumArrTime;
    private int maximumArrTime;
    private int minimumSerTime;
    private int maximumSerTime;
    private int simulationInterval;

    private SimulationManager simulationManager;

    private ScheduledExecutorService scheduledExecutorService;

    private List<QueueController> queueControllers = new ArrayList<>();

    @FXML private FlowPane mainAppWaitingLineFlowPane;
    @FXML private VBox mainAppQueueVBox;

    @FXML private Label mainAppCurrentTime;
    @FXML private Label mainAppFinalTime;

    private void updateTimeDisplay() {
        mainAppCurrentTime.setText(String.valueOf(simulationManager.getCurrentTime().get()));
        mainAppFinalTime.setText(String.valueOf(simulationInterval));
    }

    private void updateWaitingLine() {
        mainAppWaitingLineFlowPane.getChildren().clear();

        List<Client> waitingClients = simulationManager.getWaitingClients();

        for(Client client : waitingClients) {
            Node clientComponent = createClientComponent(client);
            mainAppWaitingLineFlowPane.getChildren().add(clientComponent);
        }
    }

    @FXML
    private void generate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("input-tab.fxml"));
            Parent root = loader.load();

            InputTabController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Input Tab");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            getInputValues(controller);

            simulationManager = new SimulationManager(numberOfClients, numberOfQueues, simulationInterval, minimumArrTime, maximumArrTime, minimumSerTime, maximumSerTime);
            for(Queue queue : simulationManager.getScheduler().getQueues()) {
                createQueueComponent(queue);
            }
            scheduledExecutorService = Executors.newScheduledThreadPool(numberOfQueues);

            startUpdateThread();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void simulate(ActionEvent event) {
        if(simulationManager != null) {
            Thread simulationThread = new Thread(simulationManager);

            //first starting the overall simulation, then the back end simulation of queues
            simulationThread.start();

            //then I am updating the queue UI
            for(QueueController queueController : queueControllers) {
                scheduledExecutorService.scheduleAtFixedRate(queueController, 0, 250, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void startUpdateThread() {
        Thread updateThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                    updateWaitingLine();
                    updateTimeDisplay();
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        updateThread.start();
    }

    private Node createClientComponent(Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
            Node component = loader.load();

            ClientController controller = loader.getController();
            controller.setLabels(client);

            return component;
        } catch (IOException e) {
           e.printStackTrace();
        }

        return null;
    }

    private void createQueueComponent(Queue queue) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("queue.fxml"));
            Node queueComponent = loader.load();
            mainAppQueueVBox.getChildren().add(queueComponent);

            QueueController controller = loader.getController();
            controller.setQueue(queue);

            queueControllers.add(controller);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void getInputValues(InputTabController controller) {
        simulationInterval = controller.getSimulationInterval();

        numberOfClients = controller.getNumberOfClients();
        numberOfQueues = controller.getNumberOfQueues();

        minimumArrTime = controller.getMinimumArrTime();
        maximumArrTime = controller.getMaximumArrTime();

        minimumSerTime = controller.getMinimumSerTime();
        maximumSerTime = controller.getMaximumSerTime();
    }
}
