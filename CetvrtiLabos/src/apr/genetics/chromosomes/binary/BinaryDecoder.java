package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.util.ChromosomeDecoder;
import apr.util.Interval;

public interface BinaryDecoder extends ChromosomeDecoder<byte[], Double> {

    Interval getInterval();

    int getNumberOfBits();

    double getPrecision();

    static double binaryToFloatingPoint(byte[] b) {
        double v = 0.;
        for (int i = 0, n = b.length; i < n; i++) {
            if (b[i] == (byte) 1) {
                v += (1L << i);
            }
        }
        return v;
    }

    static byte[] binaryToGray(byte[] b) {
        int n = b.length;
        byte[] g = new byte[n];
        g[0] = b[0];
        for (int i = 1; i < n; i++) {
            g[i] = (byte) (b[i - 1] ^ b[i]);
        }
        return g;
    }

    static byte[] grayToBinary(byte[] g) {
        int n = g.length;
        byte[] b = new byte[n];
        byte v = g[0];
        b[0] = v;
        for (int i = 1; i < n; i++) {
            if (g[i] == (byte) 1) {
                v = (byte) (1 - v);
            }
            b[i] = v;
        }
        return b;
    }

    static double calculatePrecision(Interval interval, int numberOfBits) {
        double lb = interval.start();
        double ub = interval.end();
        return (ub - lb) / ((1L << numberOfBits) - 1);
    }

    static int calculateNumberOfBits(Interval interval, double precision) {
        double lb = interval.start();
        double ub = interval.end();
        return Math.toIntExact(
                Math.round(
                        Math.ceil(
                                Math.log(Math.floor(1 + (ub - lb) * Math.pow(10, precision))) / Math.log(2)
                        )
                )
        );
    }
}
