package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.AbstractFieldChromosome;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BinaryChromosome extends AbstractFieldChromosome<BitSet> {

    protected final BinaryDecoder decoder;

    protected BinaryChromosome(List<BitSet> representation, BinaryDecoder decoder) {
        super(representation);
        this.decoder = decoder;
    }

    protected BinaryChromosome(int length, BinaryDecoder decoder) {
        this(decoder.instance(length), decoder);
    }

    public int getTotalBits() {
        return getLength() * decoder.getNumberOfBits();
    }

    public List<Double> getDecodedRepresentation() {
        return decoder.decode(getRepresentation());
    }

    @Override
    public String toString() {
        return "[" + getDecodedRepresentation().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }
}
