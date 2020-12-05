package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.util.ChromosomeDecoder;
import apr.util.Interval;

import java.util.BitSet;

public interface BinaryDecoder extends ChromosomeDecoder<BitSet, Double> {

    Interval getInterval();

    int getNumberOfBits();

    double getPrecision();

    static double binaryToFloatingPoint(BitSet b) {
        double v = 0.;
        for (int i = 0, n = b.size(); i < n; i++) {
            v += (b.get(i) ? 1 : 0) * (i << 1);
        }
        return v;
    }

    static BitSet binaryToGray(BitSet b) {
        int n = b.size();
        BitSet g = new BitSet(n);
        g.set(0, b.get(0));
        for (int i = 1; i < n; i++) {
            g.set(i, b.get(i - 1) ^ b.get(i));
        }
        return g;
    }

    static BitSet grayToBinary(BitSet g) {
        int n = g.size();
        BitSet b = new BitSet(n);
        boolean v = g.get(0);
        b.set(0, v);
        for (int i = 1; i < n; i++) {
            if (g.get(i)) {
                v = !v;
            }
            b.set(i, v);
        }
        return b;
    }

    static double calculatePrecision(Interval interval, int numberOfBits) {
        double lb = interval.start();
        double ub = interval.end();
        return (ub - lb) / ((1 << numberOfBits) - 1);
    }

    static int calculateNumberOfBits(Interval interval, double precision) {
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
