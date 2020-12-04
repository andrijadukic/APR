package apr.genetics.chromosomes.floatingpoint;

import apr.genetics.chromosomes.AbstractFieldChromosome;

import java.util.List;

public abstract class FloatingPointChromosome extends AbstractFieldChromosome<Double> {

    protected FloatingPointChromosome(List<Double> representation) {
        super(representation);
    }

    protected FloatingPointChromosome(Double[] representation) {
        super(representation);
    }
}
