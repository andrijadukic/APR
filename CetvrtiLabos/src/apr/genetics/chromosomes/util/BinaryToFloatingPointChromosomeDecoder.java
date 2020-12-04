package apr.genetics.chromosomes.util;

import apr.util.Interval;

import java.util.BitSet;

public class BinaryToFloatingPointChromosomeDecoder implements ChromosomeDecoder<BitSet, Double> {

    private final Interval interval;
    private final int numberOfBits;
    double precision;

    public BinaryToFloatingPointChromosomeDecoder(Interval interval, int numberOfBits) {
        this.interval = interval;
        this.numberOfBits = numberOfBits;
        this.precision = calculatePrecision(interval, numberOfBits);
    }

    public BinaryToFloatingPointChromosomeDecoder(double lb, double ub, int numberOfBits) {
        this(new Interval(lb, ub), numberOfBits);
    }

    public BinaryToFloatingPointChromosomeDecoder(Interval interval, double precision) {
        this.interval = interval;
        this.precision = precision;
        this.numberOfBits = calculateNumberOfBits(interval, precision);
    }

    public BinaryToFloatingPointChromosomeDecoder(double lb, double ub, double precision) {
        this(new Interval(lb, ub), precision);
    }

    public Interval getInterval() {
        return interval;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    public double getPrecision() {
        return precision;
    }

    @Override
    public Double decode(BitSet binary) {
        double b = 0.;
        for (int i = 0; i < numberOfBits; i++) {
            b += (binary.get(i) ? 1 : 0) * (i << 1);
        }
        return b;
    }

    private static double calculatePrecision(Interval interval, int numberOfBits) {
        double lb = interval.start();
        double ub = interval.end();
        return (ub - lb) / (Math.pow(2, numberOfBits) - 1);
    }

    private static int calculateNumberOfBits(Interval interval, double precision) {
        double lb = interval.start();
        double ub = interval.end();
        return Math.toIntExact(
                Math.round(
                        Math.ceil(
                                Math.log(Math.floor(1 + (ub - lb) * Math.pow(10, precision))) / Math.log(2))
                )
        );
    }
}
