package businessLogic;

import dataModel.Client;

import java.util.Random;

public class Generator {
    private int minimumArrivalTime;
    private int maximumArrivalTime;

    private int minimumServeTime;
    private int maximumServeTime;

    private int id = 1;

    public Generator(int minimumArrivalTime, int maximumArrivalTime, int minimumServeTime, int maximumServeTime) {
        this.minimumArrivalTime = minimumArrivalTime;
        this.maximumArrivalTime = maximumArrivalTime;

        this.minimumServeTime = minimumServeTime;
        this.maximumServeTime = maximumServeTime;
    }

    public Client generateClient() {
        Random random = new Random();

        int arrivalTimeInt = random.nextInt(maximumArrivalTime - minimumArrivalTime + 1) + minimumArrivalTime;
        int serveTimeInt = random.nextInt(maximumServeTime - minimumServeTime + 1) + minimumServeTime;

        return new Client(id++, arrivalTimeInt, serveTimeInt);
    }
}