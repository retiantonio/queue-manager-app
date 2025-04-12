package com.example.queuemanager;

import dataModel.Client;
import dataModel.Queue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class QueueController implements Runnable {

    private Queue queue;

    @FXML private FlowPane queueFlowPane;

    private void updateComponent() {
        queueFlowPane.getChildren().clear();

            //first update the processing client
        queueFlowPane.getChildren().add(createClientComponent(queue.getProcessingClient()));

            //then update the rest of the queue
        for(Client client : queue.getClientsInQueue()) {
            Node clientComponent = createClientComponent(client);
            queueFlowPane.getChildren().add(clientComponent);
        }
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            Platform.runLater(this::updateComponent);
        }
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

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
