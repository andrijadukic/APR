package apr.genetics.chromosomes;

public abstract class AbstractChromosome implements Chromosome {

    protected double fitness = DEFAULT_FITNESS;

    private static final double DEFAULT_FITNESS = Double.NEGATIVE_INFINITY;

    @Override
    public double getFitness() {
        if (fitness == DEFAULT_FITNESS) {
            fitness = evaluate();
        }
        return fitness;
    }

    protected abstract double evaluate();
}
