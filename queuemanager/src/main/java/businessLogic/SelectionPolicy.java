package businessLogic;

public enum SelectionPolicy {
    TIME_STRATEGY, SHORTEST_QUEUE_STRATEGY;

    public static SelectionPolicy selectionPolicy = TIME_STRATEGY;
}
