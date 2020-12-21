package apr.integration.util;

public class StandardOutputLogger implements LinearSystemIntegrationObserver {

    @Override
    public void update(StateStatistics statistics) {
        System.out.println("Iteration: " + statistics.iteration() + " | t: " + statistics.step() + " | x: " + statistics.x());
    }
}
