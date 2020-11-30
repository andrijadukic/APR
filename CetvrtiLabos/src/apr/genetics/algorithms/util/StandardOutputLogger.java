package apr.genetics.algorithms.util;

public class StandardOutputLogger implements GeneticAlgorithmObserver {

    @Override
    public void update(IntermediateResult intermediateResult) {
        System.out.println("Generation: " + intermediateResult.count() +
                " | Best fitness: " + intermediateResult.fittest().getFitness() +
                " | Chromosome: " + intermediateResult.fittest());
    }
}
