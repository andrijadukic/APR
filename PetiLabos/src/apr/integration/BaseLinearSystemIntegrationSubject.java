package apr.integration;

import java.util.ArrayList;
import java.util.List;

public class BaseLinearSystemIntegrationSubject implements LinearSystemIntegrationSubject {

    private final List<LinearSystemIntegrationObserver> observers;

    public BaseLinearSystemIntegrationSubject() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(LinearSystemIntegrationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(LinearSystemIntegrationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(StateStatistics statistics) {
        observers.forEach(observer -> observer.update(statistics));
    }
}
