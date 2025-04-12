package businessLogic;

import dataModel.Client;
import dataModel.Queue;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public void assignClient(List<Queue> queues, Client client) {
        Queue shortestQueue = queues.getFirst();

        for(Queue queue : queues) {
            if(queue.getSize() < shortestQueue.getSize()) {
                shortestQueue = queue;
            }
        }

        shortestQueue.assignClient(client);
    }
}
