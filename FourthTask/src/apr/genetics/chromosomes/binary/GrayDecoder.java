package apr.genetics.chromosomes.binary;

import apr.util.Interval;

final class GrayDecoder implements BinaryDecoder {

    private final BinaryDecoder binaryDecoder;

    GrayDecoder(BinaryDecoder binaryDecoder) {
        this.binaryDecoder = binaryDecoder;
    }

    @Override
    public Interval getInterval() {
        return binaryDecoder.getInterval();
    }

    @Override
    public int getNumberOfBits() {
        return binaryDecoder.getNumberOfBits();
    }

    @Override
    public double getPrecision() {
        return binaryDecoder.getPrecision();
    }

    @Override
    public Double decode(byte[] g) {
        return binaryDecoder.decode(BinaryDecoder.grayToBinary(g));
    }

    @Override
    public byte[] instance() {
        return BinaryDecoder.binaryToGray(binaryDecoder.instance());
    }
}
