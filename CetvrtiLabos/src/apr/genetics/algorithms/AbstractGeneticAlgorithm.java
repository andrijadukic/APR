package apr.genetics.algorithms;

import apr.genetics.algorithms.conditions.StoppingCondition;
import apr.genetics.algorithms.util.IntermediateResult;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.algorithms.util.GeneticAlgorithmObserver;
import apr.genetics.exceptions.EmptyPopulationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractGeneticAlgorithm implements GeneticAlgorithm {

    private final List<GeneticAlgorithmObserver> observers;

    protected AbstractGeneticAlgorithm() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(GeneticAlgorithmObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    @Override
    public void removeObserver(GeneticAlgorithmObserver observer) {
        observers.remove(Objects.requireNonNull(observer));
    }

    @Override
    public void notifyObservers(IntermediateResult intermediateResult) {
        observers.forEach(observer -> observer.update(intermediateResult));
    }

    @Override
    public Population run(Population initial, StoppingCondition condition) {
        Objects.requireNonNull(initial);
        Objects.requireNonNull(condition);

        if (initial.size() == 0) throw new EmptyPopulationException();

        validate(initial);

        int iteration = 0;
        Population current = initial.copy();
        Chromosome fittest = current.getFittest();
        while (true) {
            IntermediateResult intermediateResult = new IntermediateResult(iteration, fittest, current);
            notifyObservers(intermediateResult);

            if (condition.isMet(intermediateResult)) break;

            current = nextGeneration(current);

            Chromosome candidate = current.getFittest();
            if (candidate.compareTo(fittest) > 0) {
                fittest = candidate;
            }

            iteration++;
        }
        return current;
    }

    public abstract void validate(Population initial);

    public abstract Population nextGeneration(Population current);
}
