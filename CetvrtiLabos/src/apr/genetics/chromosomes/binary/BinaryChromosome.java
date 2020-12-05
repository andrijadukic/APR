package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.AbstractFieldChromosome;

import java.util.BitSet;
import java.util.List;

public abstract class BinaryChromosome extends AbstractFieldChromosome<BitSet> {

    protected final BinaryDecoder coder;

    public BinaryChromosome(List<BitSet> representation, BinaryDecoder coder) {
        super(representation);
        this.coder = coder;
    }

    public BinaryChromosome(int length, BinaryDecoder coder) {
        this(coder.instance(length), coder);
    }

    public int getTotalBits() {
        return getLength() * coder.getNumberOfBits();
    }
}
