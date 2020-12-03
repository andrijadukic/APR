package apr.genetics.demo;

import apr.functions.IMultivariateFunction;
import apr.genetics.chromosomes.FloatingPointChromosome;
import apr.genetics.chromosomes.FieldChromosome;

import java.util.List;

public class MinimizationProblemFloatingPointChromosome extends FloatingPointChromosome {

    private final IMultivariateFunction function;

    public MinimizationProblemFloatingPointChromosome(List<Double> representation, IMultivariateFunction function) {
        super(representation);
        this.function = function;
    }

    @Override
    protected double evaluate() {
        return function.valueAt(representation);
    }

    @Override
    public FieldChromosome<Double> newInstance(List<Double> representation) {
        return new MinimizationProblemFloatingPointChromosome(representation, function);
    }
}
