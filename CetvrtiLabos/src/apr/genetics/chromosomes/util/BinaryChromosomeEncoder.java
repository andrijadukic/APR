package apr.genetics.chromosomes.util;

public class BinaryChromosomeEncoder implements ChromosomeEncoder<Integer, Double> {

    private final double lb;
    private final double ub;
    private final int precision;

    public BinaryChromosomeEncoder(double lb, double ub, int precision) {
        this.lb = lb;
        this.ub = ub;
        this.precision = precision;
    }

    @Override
    public Integer encode(Double value) {
        return Math.toIntExact(Math.round((value - lb) / (ub - lb) * (Math.pow(2, precision) - 1)));
    }
}
