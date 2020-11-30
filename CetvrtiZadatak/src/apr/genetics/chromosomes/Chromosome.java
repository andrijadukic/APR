package apr.genetics.chromosomes;

public interface Chromosome extends Comparable<Chromosome> {

    double getFitness();

    @Override
    default int compareTo(Chromosome o) {
        return Double.compare(getFitness(), o.getFitness());
    }
}
