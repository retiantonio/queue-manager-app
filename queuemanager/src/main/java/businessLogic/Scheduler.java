package businessLogic;

import dataModel.Client;
import dataModel.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private int mumberOfQueues;

    private Strategy strategy;
    private ScheduledExecutorService threadScheduler;

    private List<Queue> queues = new ArrayList<>();

    public Scheduler(int queueNo) {
        mumberOfQueues = queueNo;
        strategy = new ShortestTimeStrategy();

        for(int i = 0; i < queueNo; i++) {
            queues.add(new Queue());
        }

        threadScheduler = Executors.newScheduledThreadPool(queueNo);
    }

    public void changeStrategy() {
        if(SelectionPolicy.selectionPolicy == SelectionPolicy.TIME_STRATEGY) {
            strategy = new ShortestTimeStrategy();
        } else {
            strategy = new ShortestQueueStrategy();
        }
    }

    public void assignClient(Client client) {
        strategy.assignClient(queues, client);
    }

    public void startQueueThreads() {
        for(int i = 0; i < mumberOfQueues; i++) {
            threadScheduler.scheduleAtFixedRate(queues.get(i), 0, 1, TimeUnit.SECONDS);
        }
    }

    public List<Queue>  getQueues() {
        return queues;
    }

    public void finishThreadScheduling() {
        threadScheduler.shutdown();
    }
}
