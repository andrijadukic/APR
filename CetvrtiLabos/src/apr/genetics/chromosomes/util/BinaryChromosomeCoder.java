package apr.genetics.chromosomes.util;

import apr.util.Interval;

public class BinaryChromosomeCoder implements ChromosomeCoder<Integer, Double> {

    private final Interval interval;
    private final int precision;

    public BinaryChromosomeCoder(Interval interval, int precision) {
        this.interval = interval;
        this.precision = precision;
    }

    public BinaryChromosomeCoder(double lb, double ub, int precision) {
        this.interval = new Interval(lb, ub);
        this.precision = precision;
    }

    public Interval getInterval() {
        return interval;
    }

    public int getPrecision() {
        return precision;
    }

    @Override
    public Integer encode(Double value) {
        double lb = interval.start();
        double ub = interval.end();
        return Math.toIntExact(Math.round((value - lb) / (ub - lb) * (Math.pow(2, precision) - 1)));
    }

    @Override
    public Double decode(Integer binary) {
        double lb = interval.start();
        double ub = interval.end();
        return lb + binary / (Math.pow(2, precision) - 1) * (ub - lb);
    }
}
