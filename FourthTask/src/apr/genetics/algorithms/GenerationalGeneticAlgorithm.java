package apr.genetics.algorithms;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.operators.crossover.CrossoverOperator;
import apr.genetics.operators.mutation.MutationOperator;
import apr.genetics.operators.selection.SelectionOperator;
import apr.util.SourceOfRandomness;

import java.util.Random;

public class GenerationalGeneticAlgorithm extends AbstractGeneticAlgorithm {

    private final SelectionOperator selectionOperator;
    private final CrossoverOperator crossoverOperator;
    private final double crossoverRate;
    private final MutationOperator mutationOperator;
    private final double mutationRate;
    private boolean isElitist;

    private static final boolean DEFAULT_ELITIST = false;

    public GenerationalGeneticAlgorithm(SelectionOperator selectionOperator,
                                        CrossoverOperator crossoverOperator, double crossoverRate,
                                        MutationOperator mutationOperator, double mutationRate,
                                        boolean isElitist) {
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.crossoverRate = crossoverRate;
        this.mutationOperator = mutationOperator;
        this.mutationRate = mutationRate;
        this.isElitist = isElitist;
    }

    public GenerationalGeneticAlgorithm(SelectionOperator selectionOperator,
                                        CrossoverOperator crossoverOperator, double crossoverRate,
                                        MutationOperator mutationOperator, double mutationRate) {
        this(selectionOperator, crossoverOperator, crossoverRate, mutationOperator, mutationRate, DEFAULT_ELITIST);
    }

    public boolean isElitist() {
        return isElitist;
    }

    public void setElitist(boolean elitist) {
        isElitist = elitist;
    }

    @Override
    public void validate(Population initial) {
    }

    @Override
    public Population nextGeneration(Population current) {
        Population next = current.empty();

        if (isElitist) {
            next.addChromosome(current.getFittest());
        }

        Random random = SourceOfRandomness.getSource();
        int remaining = current.size() - next.size();
        while (remaining != 0) {
            Chromosome[] parents = selectionOperator.select(current, 2);

            ChromosomePair pair = random.nextDouble() < crossoverRate
                    ? crossoverOperator.crossover(parents[0], parents[1])
                    : new ChromosomePair(parents[0], parents[1]);

            pair = random.nextDouble() < mutationRate
                    ? new ChromosomePair(mutationOperator.mutate(pair.first()), mutationOperator.mutate(pair.second()))
                    : pair;

            next.addChromosome(pair.first());
            remaining--;

            if (remaining == 0) break;

            next.addChromosome(pair.second());
            remaining--;
        }

        return next;
    }
}
