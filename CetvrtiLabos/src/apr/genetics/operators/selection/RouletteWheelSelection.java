package apr.genetics.operators.selection;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.exceptions.InsufficientPopulationSIze;
import apr.util.MathUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RouletteWheelSelection implements SelectionOperator {

    private final boolean isSorted;

    private static final boolean DEFAULT_IS_SORTED = false;

    public RouletteWheelSelection() {
        this(DEFAULT_IS_SORTED);
    }

    public RouletteWheelSelection(boolean isSorted) {
        this.isSorted = isSorted;
    }

    @Override
    public Chromosome select(Population population) {
        return select(population, 1)[0];
    }

    @Override
    public Chromosome[] select(Population population, int k) {
        int size = population.size();

        if (size < k) throw new InsufficientPopulationSIze(population.size(), k);

        Random random = ThreadLocalRandom.current();

        List<Chromosome> chromosomes = new ArrayList<>(population.getChromosomes());
        double[] probabilities = probabilities(chromosomes);
        Chromosome[] selected = new Chromosome[k];
        int prev = -1;
        for (int i = 0; i < k; i++) {
            int rind = find(probabilities, random.nextDouble());

            if (i % 2 == 0) {
                prev = -1;
            }

            while (prev == rind) {
                rind = find(probabilities, random.nextDouble());
            }

            selected[i] = chromosomes.get(rind);
            prev = rind;
        }
        return selected;
    }

    private double[] probabilities(List<Chromosome> chromosomes) {
        if (!isSorted) {
            Collections.sort(chromosomes);
        }

        double[] fitness = chromosomes.stream().mapToDouble(Chromosome::getFitness).toArray();
        MathUtils.cumulativeSum(fitness, -fitness[0]);
        MathUtils.scale(fitness, fitness[fitness.length - 1]);
        return fitness;
    }

    private static int find(double[] rouletteWheel, double selection) {
        int l = 0;
        int r = rouletteWheel.length;
        while (r > l) {
            int mid = (l + r) / 2;
            if (mid == 0 || (rouletteWheel[mid] >= selection && rouletteWheel[mid - 1] < selection)) {
                return mid;
            } else if (rouletteWheel[mid] <= selection) {
                l = mid + 1;
            } else if (rouletteWheel[mid] > selection) {
                r = mid;
            }
        }

        throw new IllegalStateException();
    }
}
