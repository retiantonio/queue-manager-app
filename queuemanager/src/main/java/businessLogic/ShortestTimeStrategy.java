package businessLogic;

import dataModel.Client;
import dataModel.Queue;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {

    @Override
    public void assignClient(List<Queue> queues, Client client) {
        Queue shortestTimeQueue = queues.getFirst();

        for(Queue queue : queues) {
            if(queue.getWaitingPeriod() < shortestTimeQueue.getWaitingPeriod()) {
                shortestTimeQueue = queue;
            }
        }

        shortestTimeQueue.assignClient(client);
    }
}
