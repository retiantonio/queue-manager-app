package dataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
        //Thread-Safe
    private AtomicInteger waitingPeriod = new AtomicInteger();

    private Client processingClient = null;
    private BlockingQueue<Client> clients = new LinkedBlockingQueue<>();


    @Override
    public void run() {
            //new Design for Blocking Queue Approach
        try {
            if(processingClient == null) {
                processingClient = clients.poll();
            }

            if(processingClient != null) {
                processingClient.decreaseServingTime();
                updateWaitingPeriod();

                if(processingClient.getServeTime() == 0) {
                    processingClient = null;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void assignClient(Client client) {
        clients.add(client);
        waitingPeriod.addAndGet(client.getServeTime());
    }

    private void updateWaitingPeriod() {
        waitingPeriod.set(0);

        waitingPeriod.addAndGet(processingClient.getServeTime());

        for(Client client : clients) {
            waitingPeriod.addAndGet(client.getServeTime());
        }
    }

    public int getSize() {
        return clients.size();
    }

    public boolean isUnprocessedClientQueueEmpty() {
        return clients.isEmpty();
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Client getProcessingClient() {
        return processingClient;
    }

    public BlockingQueue<Client> getClientsInQueue() {
        return clients;
    }
}
