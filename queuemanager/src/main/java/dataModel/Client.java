package dataModel;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private int id;
    private int arrivalTime;
    private int serveTime;

    private int processingTime = -1;

    public Client(int id, int arrivalTime, int serveTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTime = serveTime;
    }

    public void decreaseServingTime() {
        serveTime--;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServeTime() {
        return serveTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }
}


