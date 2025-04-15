package utils;

import dataModel.Client;

import java.util.List;

public class HelpMethods {

    public static int calculateClientWaitingTime(Client client) {
        return client.getProcessingTime() - client.getArrivalTime();
    }

    public static double returnAverageWaitingTime(List<Client> allClients) {
        double sumWaitingTime = 0;

        for (Client client : allClients) {
            if(client.getProcessingTime() != -1) {
                sumWaitingTime += calculateClientWaitingTime(client);
            }
        }

        return sumWaitingTime / allClients.size();
    }
}
