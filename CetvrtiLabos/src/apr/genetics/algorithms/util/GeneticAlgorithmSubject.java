package apr.genetics.algorithms.util;

public interface GeneticAlgorithmSubject {

    void addObserver(GeneticAlgorithmObserver observer);

    void removeObserver(GeneticAlgorithmObserver observer);

    void notifyObservers(IntermediateResult event);
}
