package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.AbstractFieldChromosome;

import java.util.BitSet;
import java.util.List;

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
}
