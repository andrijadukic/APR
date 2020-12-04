package apr.genetics.chromosomes.binary;

import apr.genetics.chromosomes.AbstractFieldChromosome;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BinaryChromosome extends AbstractFieldChromosome<BitSet> {

    protected final BinaryToFloatingPointChromosomeDecoder coder;

    protected BinaryChromosome(BinaryToFloatingPointChromosomeDecoder coder, int length) {
        super(buildRepresentation(length, coder.getNumberOfBits()));
        this.coder = coder;
    }

    public BinaryChromosome(List<BitSet> representation, BinaryToFloatingPointChromosomeDecoder coder) {
        super(representation);
        this.coder = coder;
    }

    public int getNumberOfBits() {
        return coder.getNumberOfBits();
    }

    private static List<BitSet> buildRepresentation(int length, int numberOfBits) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return Stream.generate(() -> {
            byte[] array = new byte[numberOfBits];
            random.nextBytes(array);
            return BitSet.valueOf(array);
        })
                .limit(length)
                .collect(Collectors.toList());
    }
}
