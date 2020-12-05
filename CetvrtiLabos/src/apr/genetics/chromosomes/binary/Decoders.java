package apr.genetics.chromosomes.binary;

import apr.util.Interval;

public final class Decoders {

    private Decoders() {
    }

    BinaryDecoder binary(double lb, double ub, int numberOfBits) {
        return new StandardBinaryDecoder(new Interval(lb, ub), numberOfBits);
    }

    BinaryDecoder binary(double lb, double ub, double precision) {
        return new StandardBinaryDecoder(new Interval(lb, ub), precision);
    }

    BinaryDecoder gray(double lb, double ub, int numberOfBits) {
        return new GrayDecoder(binary(lb, ub, numberOfBits));
    }

    BinaryDecoder gray(double lb, double ub, double precision) {
        return new GrayDecoder(binary(lb, ub, precision));
    }
}
