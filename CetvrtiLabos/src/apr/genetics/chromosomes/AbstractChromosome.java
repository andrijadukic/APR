package apr.genetics.chromosomes;

public abstract class AbstractChromosome implements Chromosome {

    private static final double DEFAULT_FITNESS = Double.NEGATIVE_INFINITY;

    protected double fitness = DEFAULT_FITNESS;

    @Override
    public double getFitness() {
        if (fitness == DEFAULT_FITNESS) {
            fitness = evaluate();
        }
        return fitness;
    }

    protected abstract double evaluate();
}
