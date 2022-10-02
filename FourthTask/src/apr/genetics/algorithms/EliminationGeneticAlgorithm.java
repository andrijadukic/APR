package apr.genetics.algorithms;

import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.InsufficientPopulationSIze;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.operators.crossover.CrossoverOperator;
import apr.genetics.operators.mutation.MutationOperator;
import apr.util.Sampling;
import apr.util.SourceOfRandomness;

import java.util.*;

public final class EliminationGeneticAlgorithm extends AbstractGeneticAlgorithm {

    private final CrossoverOperator crossoverOperator;
    private final double crossoverRate;
    private final MutationOperator mutationOperator;
    private final double mutationRate;
    private final int tournamentSize;

    private static final int DEFAULT_TOURNAMENT_SIZE = 3;

    public EliminationGeneticAlgorithm(CrossoverOperator crossoverOperator, double crossoverRate,
                                       MutationOperator mutationOperator, double mutationRate,
                                       int tournamentSize) {
        this.crossoverOperator = crossoverOperator;
        this.crossoverRate = crossoverRate;
        this.mutationOperator = mutationOperator;
        this.mutationRate = mutationRate;
        this.tournamentSize = tournamentSize;
    }

    public EliminationGeneticAlgorithm(CrossoverOperator crossoverOperator, double crossoverRate,
                                       MutationOperator mutationOperator, double mutationRate) {
        this(crossoverOperator, crossoverRate, mutationOperator, mutationRate, DEFAULT_TOURNAMENT_SIZE);
    }

    @Override
    public void validate(Population initial) {
        int size = initial.size();
        if (size < tournamentSize) throw new InsufficientPopulationSIze(size, tournamentSize);
    }

    @Override
    public Population nextGeneration(Population current) {
        int size = current.size();

        Random random = SourceOfRandomness.getSource();

        int[] indices = new int[tournamentSize];
        Chromosome[] tournament = new Chromosome[tournamentSize];
        Set<Integer> selected = new HashSet<>(tournamentSize, 1.f);
        for (int i = 0; i < tournamentSize; i++) {
            int rind = random.nextInt(size);
            while (selected.contains(rind)) {
                rind = random.nextInt(size);
            }
            indices[i] = rind;
            tournament[i] = current.getChromosome(rind);
            selected.add(rind);
        }

        int min = Sampling.argMin(tournament);

        var temp = tournament[tournamentSize - 1];
        tournament[tournamentSize - 1] = tournament[min];
        tournament[min] = temp;

        ChromosomePair pair = random.nextDouble() <= crossoverRate
                ? crossoverOperator.crossover(tournament[0], tournament[1])
                : new ChromosomePair(tournament[0], tournament[1]);

        Chromosome child = random.nextDouble() <= mutationRate
                ? mutationOperator.mutate(pair.first())
                : pair.first();

        current.setChromosome(indices[min], child);

        return current;
    }
}
