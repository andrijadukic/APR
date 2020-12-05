package apr.genetics.demo;

import apr.functions.IMultivariateFunction;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.binary.BinaryDecoder;

import java.util.BitSet;
import java.util.List;

public class MinimizationProblemBinaryChromosome extends BinaryChromosome {

    private final IMultivariateFunction function;

    public MinimizationProblemBinaryChromosome(BinaryDecoder coder, int length, IMultivariateFunction function) {
        super(length, coder);
        this.function = function;
    }

    public MinimizationProblemBinaryChromosome(List<BitSet> representation, BinaryDecoder coder, IMultivariateFunction function) {
        super(representation, coder);
        this.function = function;
    }

    @Override
    protected double evaluate() {
        return function.valueAt(decoder.decode(representation));
    }

    @Override
    public FieldChromosome<BitSet> newInstance(List<BitSet> representation) {
        return new MinimizationProblemBinaryChromosome(representation, decoder, function);
    }
}
