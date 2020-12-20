package apr.integration;

public interface LinearSystemIntegrationSubject {

    void addObserver(LinearSystemIntegrationObserver observer);

    void removeObserver(LinearSystemIntegrationObserver observer);

    void notifyObservers(StateStatistics statistics);
}
