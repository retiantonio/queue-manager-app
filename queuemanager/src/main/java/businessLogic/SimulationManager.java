package businessLogic;

import dataModel.Client;
import dataModel.Queue;
import utils.HelpMethods;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {

    private int clientsNo;
    private int queuesNo;
    private int simulationInterval;

    private double averageWaitingTime;

    private AtomicInteger currentTime =  new AtomicInteger(0);

    private boolean threadsStarted = false;
    private boolean isFinished  = false;

    private Scheduler scheduler;
    private Generator generator;

    private List<Client> waitingClients = new ArrayList<>() ;
    private List<Client> initialClientList = new ArrayList<>();

    public SimulationManager(int numberOfClients, int numberOfQueues, int simulationInterval, int minimumArrivalTime, int maximumArrivalTime, int minimumServeTime, int maximumServeTime) {
        clientsNo = numberOfClients;
        queuesNo = numberOfQueues;
        this.simulationInterval = simulationInterval;

        generator = new Generator(minimumArrivalTime, maximumArrivalTime, minimumServeTime, maximumServeTime);
        generateClients();
        initialClientList.addAll(waitingClients);

        scheduler = new Scheduler(numberOfQueues, currentTime);
    }

    private void generateClients() {
        for(int i = 0; i < clientsNo; i++) {
            waitingClients.add(generator.generateClient());
        }
    }

    private void checkAndSendClients() {
        synchronized (waitingClients) {
            Iterator<Client> iterator = waitingClients.iterator();

            while(iterator.hasNext()) {
                Client client = iterator.next();
                if(client.getArrivalTime() == currentTime.get()) {
                    iterator.remove();
                    scheduler.assignClient(client);
                }
            }
        }
    }

    @Override
    public void run() {
        currentTime.set(0);

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter("log-of-events-3.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!Thread.currentThread().isInterrupted() && currentTime.get() < simulationInterval) {

            if(!threadsStarted) {
                scheduler.startQueueThreads();
                threadsStarted = true;
            }

            if(isReadyToFinish()) {
                break;
            }

            changeStrategyApproach(scheduler.getQueues());
            checkAndSendClients();

            if(bufferedWriter != null) {
                writeLogOfEvents(bufferedWriter);
            }

            try {
                Thread.sleep(1000); //sleep for 1 second
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            currentTime.incrementAndGet();
        }

        scheduler.finishThreadScheduling();
        averageWaitingTime = HelpMethods.returnAverageWaitingTime(initialClientList);
        isFinished = true;

        if(bufferedWriter != null) {
            try {
                bufferedWriter.write("Average Waiting Time: " + averageWaitingTime);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeLogOfEvents(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write("Current Time: " + currentTime);
            bufferedWriter.newLine();

            bufferedWriter.write("Waiting Clients: ");
            if(!waitingClients.isEmpty()) {
                for(Client client : waitingClients) {
                    bufferedWriter.write("(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServeTime() + ")");

                    if(!(client == waitingClients.getLast())) {
                        bufferedWriter.write("; ");
                    }
                }
            } else {
                bufferedWriter.write("no clients in waiting line");
            }
            bufferedWriter.newLine();

            for(int i = 0; i < queuesNo; i++) {
                bufferedWriter.write("Queue " + i + ": ");
                Queue queue = scheduler.getQueues().get(i);

                Client processingClient = queue.getProcessingClient();

                if(processingClient != null) {
                    bufferedWriter.write("(" + processingClient.getId() + "," + processingClient.getArrivalTime() + "," + processingClient.getServeTime() + "); ");
                }

                if(!queue.isUnprocessedClientQueueEmpty()) {
                    for(Client client : queue.getClientsInQueue()) {
                        bufferedWriter.write("(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServeTime() + "); ");
                    }
                }

                if(processingClient == null && queue.isUnprocessedClientQueueEmpty()) {
                    bufferedWriter.write("closed");
                }

                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private boolean isReadyToFinish() {
        for(Queue queue : scheduler.getQueues()) {
            if(!queue.isQueueEmpty()) {
                return false;
            }
        }
        return waitingClients.isEmpty();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public List<Client> getWaitingClients() {
        return waitingClients;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public AtomicInteger getCurrentTime() {
        return currentTime;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
}
