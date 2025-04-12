package businessLogic;

import dataModel.Client;
import dataModel.Queue;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager implements Runnable {

    private int clientsNo;
    private int queuesNo;
    private int simulationInterval;

    private boolean threadsStarted = false;

    private Scheduler scheduler;
    private Generator generator;

    private List<Client> waitingClients = new ArrayList<>() ;

    public SimulationManager(int numberOfClients, int numberOfQueues, int simulationInterval, int minimumArrivalTime, int maximumArrivalTime, int minimumServeTime, int maximumServeTime) {
        clientsNo = numberOfClients;
        queuesNo = numberOfQueues;
        this.simulationInterval = simulationInterval;

        generator = new Generator(minimumArrivalTime, maximumArrivalTime, minimumServeTime, maximumServeTime);
        generateClients();

        scheduler = new Scheduler(numberOfQueues);
    }

    private void generateClients() {
        for(int i = 0; i < clientsNo; i++) {
            waitingClients.add(generator.generateClient());
        }
    }

    private void checkAndSendClients(int currentTime) {
        for(Client client : waitingClients)
            if(client.getArrivalTime() == currentTime) {
                waitingClients.remove(client);
                scheduler.assignClient(client);
            }
    }

    @Override
    public void run() {
        int currentTime = 0;

        while(!Thread.currentThread().isInterrupted() && currentTime < simulationInterval) {

            if(!threadsStarted) {
                scheduler.startQueueThreads();
                threadsStarted = true;
            }

            changeStrategyApproach(scheduler.getQueues());
            checkAndSendClients(currentTime);

            currentTime++;

            try {
                Thread.sleep(1000); //sleep for 1 second
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        scheduler.finishThreadScheduling();
    }

    private void changeStrategyApproach(List<Queue> queues) {
        int shortestQueueTime = checkShortestQueueTime(queues);
        int shortestWaitingTime = checkShortestTime(queues);

        if(shortestQueueTime < shortestWaitingTime) {
            SelectionPolicy.selectionPolicy = SelectionPolicy.SHORTEST_QUEUE_STRATEGY;
        } else {
            SelectionPolicy.selectionPolicy = SelectionPolicy.TIME_STRATEGY;
        }

        scheduler.changeStrategy();
    }

    private int checkShortestTime(List<Queue> queues) {
        int shortestTimePerQueue = queues.getFirst().getWaitingPeriod();

        for(Queue queue : queues) {
            if(queue.getWaitingPeriod() < shortestTimePerQueue) {
                shortestTimePerQueue = queue.getWaitingPeriod();
            }
        }

        return shortestTimePerQueue;
    }

    private int checkShortestQueueTime(List<Queue> queues) {
        int shortestQueueSize = queues.getFirst().getSize();
        int queueIndex = 0;

        for(Queue queue : queues) {
            if(queue.getSize() < shortestQueueSize) {
                shortestQueueSize = queue.getSize();
                queueIndex = queues.indexOf(queue);
            }
        }

        return queues.get(queueIndex).getWaitingPeriod();
    }

    public List<Client> getWaitingClients() {
        return waitingClients;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
