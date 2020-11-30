package apr.genetics.exceptions;

public class InsufficientChromosomeLength extends RuntimeException {

    public InsufficientChromosomeLength(int chromosomeLength, int lowerBound) {
        super("Chromosome not of sufficient length. Chromosome length: " + chromosomeLength + ", needs to be at least: " + lowerBound);
    }
}
