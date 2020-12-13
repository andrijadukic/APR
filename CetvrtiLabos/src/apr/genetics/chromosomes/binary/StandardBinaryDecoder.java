package apr.genetics.chromosomes.binary;

import apr.util.Interval;
import apr.util.SourceOfRandomness;

import java.util.Random;

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
    public Double decode(byte[] b) {
        double lb = interval.start();
        double ub = interval.end();
        return lb + BinaryDecoder.binaryToFloatingPoint(b) / ((1L << numberOfBits) - 1) * (ub - lb);
    }

    @Override
    public byte[] instance() {
        byte[] instance = new byte[numberOfBits];
        Random random = SourceOfRandomness.getSource();
        for (int i = 0; i < numberOfBits; i++) {
            instance[i] = random.nextBoolean() ? (byte) 1 : (byte) 0;
        }
        return instance;
    }
}
