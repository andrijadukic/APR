package apr.genetics.chromosomes.binary;

import apr.util.Interval;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

final class StandardBinaryDecoder implements BinaryDecoder {

    private final Interval interval;
    private final int numberOfBits;
    private final double precision;

    StandardBinaryDecoder(Interval interval, int numberOfBits) {
        this.interval = interval;
        this.numberOfBits = numberOfBits;
        this.precision = BinaryDecoder.calculatePrecision(interval, numberOfBits);
    }

    StandardBinaryDecoder(Interval interval, double precision) {
        this.interval = interval;
        this.numberOfBits = BinaryDecoder.calculateNumberOfBits(interval, precision);
        this.precision = precision;
    }

    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public int getNumberOfBits() {
        return numberOfBits;
    }

    @Override
    public double getPrecision() {
        return precision;
    }

    @Override
    public Double decode(BitSet b) {
        double lb = interval.start();
        double ub = interval.end();
        return lb + BinaryDecoder.binaryToFloatingPoint(b) * (ub - lb) / ((1L << numberOfBits) - 1);
    }

    @Override
    public BitSet instance() {
        BitSet instance = new BitSet(numberOfBits);
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < numberOfBits; i++) {
            instance.set(i, random.nextBoolean());
        }
        return instance;
    }
}
