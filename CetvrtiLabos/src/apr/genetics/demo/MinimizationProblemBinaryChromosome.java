package apr.genetics.demo;

import apr.functions.IMultivariateFunction;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.binary.BinaryToFloatingPointChromosomeDecoder;

import java.util.BitSet;
import java.util.List;

public class MinimizationProblemBinaryChromosome extends BinaryChromosome {

    private final IMultivariateFunction function;

    public MinimizationProblemBinaryChromosome(BinaryToFloatingPointChromosomeDecoder coder, int length, IMultivariateFunction function) {
        super(coder, length);
        this.function = function;
    }

    public MinimizationProblemBinaryChromosome(List<BitSet> representation, BinaryToFloatingPointChromosomeDecoder coder, IMultivariateFunction function) {
        super(representation, coder);
        this.function = function;
    }

    @Override
    protected double evaluate() {
        return function.valueAt(coder.decode(representation));
    }

    @Override
    public FieldChromosome<BitSet> newInstance(List<BitSet> representation) {
        return new MinimizationProblemBinaryChromosome(representation, coder, function);
    }
}
