package apr.genetics.chromosomes;

import java.util.List;

public abstract class DecimalChromosome extends AbstractFieldChromosome<Double> {

    protected DecimalChromosome(List<Double> representation) {
        super(representation);
    }

    protected DecimalChromosome(Double[] representation) {
        super(representation);
    }
}
