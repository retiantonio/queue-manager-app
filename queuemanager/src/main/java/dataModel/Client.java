package dataModel;

public class Client {
    private int id;
    private int arrivalTime;
    private int serveTime;

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


}


