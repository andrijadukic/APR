package apr.genetics.demo;

import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.binary.BinaryDecoder;

import java.util.List;

public class MinimizationProblemBinaryChromosome extends BinaryChromosome {

    private final FitnessFunction function;

    public MinimizationProblemBinaryChromosome(BinaryDecoder coder, int length, FitnessFunction function) {
        super(length, coder);
        this.function = function;
    }

    public MinimizationProblemBinaryChromosome(List<byte[]> representation, BinaryDecoder coder, FitnessFunction function) {
        super(representation, coder);
        this.function = function;
    }

    @Override
    protected double evaluate() {
        return function.valueAt(getDecodedRepresentation());
    }

    @Override
    public FieldChromosome<byte[]> newInstance(List<byte[]> representation) {
        return new MinimizationProblemBinaryChromosome(representation, decoder, function);
    }
}
