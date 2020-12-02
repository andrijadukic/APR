package apr.genetics.chromosomes.util;


public class BinaryChromosomeDecoder implements ChromosomeDecoder<Double, Integer> {

    private final double lb;
    private final double ub;
    private final int precision;

    public BinaryChromosomeDecoder(double lb, double ub, int precision) {
        this.lb = lb;
        this.ub = ub;
        this.precision = precision;
    }

    @Override
    public Double decode(Integer binary) {
        return lb + binary / (Math.pow(2, precision) - 1) * (ub - lb);
    }
}
