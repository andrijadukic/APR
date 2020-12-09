package apr.genetics.chromosomes.binary;

import apr.util.Interval;

public final class Decoders {

    private Decoders() {
    }

    public static BinaryDecoder binary(double lb, double ub, int numberOfBits) {
        return new StandardBinaryDecoder(new Interval(lb, ub), numberOfBits);
    }

    public static BinaryDecoder binary(double lb, double ub, double precision) {
        return new StandardBinaryDecoder(new Interval(lb, ub), precision);
    }

    public static BinaryDecoder gray(double lb, double ub, int numberOfBits) {
        return new GrayDecoder(binary(lb, ub, numberOfBits));
    }

    public static BinaryDecoder gray(double lb, double ub, double precision) {
        return new GrayDecoder(binary(lb, ub, precision));
    }
}
