package businessLogic;

import dataModel.Client;
import dataModel.Queue;

import java.util.List;

public interface Strategy {
    void assignClient(List<Queue> queues, Client client);
}
